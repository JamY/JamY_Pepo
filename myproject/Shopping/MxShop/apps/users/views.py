from random import choice

from django.contrib.auth import get_user_model
from django.contrib.auth.backends import ModelBackend
from django.db.models import Q
from django.shortcuts import render

# Create your views here.
# User = get_user_model()
from rest_framework import viewsets, status, mixins, permissions
from rest_framework.authentication import SessionAuthentication
from rest_framework.mixins import CreateModelMixin
from rest_framework.response import Response
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework_jwt.serializers import jwt_payload_handler, jwt_encode_handler

from MxShop.settings import APIKEY
from utils.yunpian import YunPian
from .serializers import SmsSerializer, UserRegSerializer, UserDetailSerializer
from .models import UserProfile, VerifyCode


class CustomBackend(ModelBackend):
    """
    自定义用户验证
    """
    def authenticate(self, username=None, password=None, **kwargs):
        try:
            # 让用户可以用手机登录
            user = UserProfile.objects.get(Q(username=username)|Q(mobile=username)) # 通过name和mobile找到user
            if user.check_password(password): # 验证密码
                return user
        except Exception as e:
            return None

class SmsCodeViewset(CreateModelMixin, viewsets.GenericViewSet):
    """
    发送短信验证码
    """
    serializer_class = SmsSerializer
    def generate_code(self):
        """
        生成四位数字的验证码
        :return:
        """
        seeds = "1234567890"
        random_str = []
        for i in range(4):
            random_str.append(choice(seeds))
        return "".join(random_str)
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)#is_valid调用失败的话直接抛异常 is_valid()调用SmsSerializer的验证方法
        mobile = serializer.validated_data["mobile"]
        yun_pian = YunPian(APIKEY)
        code = self.generate_code()
        sms_status = yun_pian.send_sms(code=code, mobile=mobile)
        # sms_status 通过云片网可以查看返回的参数类型
        if sms_status["code"] != 0: # 发送失败
            return Response({
                "mobile":sms_status["msg"]
            }, status=status.HTTP_400_BAD_REQUEST)
        else: # 成功
            code_record = VerifyCode(code=code, mobile=mobile)
            code_record.save() #保存数据库
            return Response({
                "mobile":mobile
            }, status=status.HTTP_201_CREATED)# 创建成功的状态码

class UserViewset(CreateModelMixin, mixins.UpdateModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    # UpdateModelMixin 接受 put 更新 patch 部分更新
    serializer_class = UserRegSerializer
    # 当post http://127.0.0.1:8000/users/提交数据时序列化serializer.data创建user
    queryset = UserProfile.objects.all()

    # 动态设置Serializer
    def get_serializer_class(self):
        if self.action == "retrieve":
            return UserDetailSerializer
        elif self.action == "create":
            return UserRegSerializer
        # 其他情况
        return UserDetailSerializer

    # 用户登录的情况下才能做 问题：用户注册的时候不用验证 解决：动态设置permission
    # permission_classes = (permissions.IsAuthenticated, )
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)  # 设置jwt-token认证模式 Session认证模式
    def get_permissions(self):
        if self.action == "retrieve":
            return [permissions.IsAuthenticated()]
        elif self.action == "create":
            return []
        # 其他情况
        return []

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = self.perform_create(serializer)

        # 数据定制 token name token是存放在cookie中的
        re_dict = serializer.data
        payload = jwt_payload_handler(user)
        re_dict["token"] = jwt_encode_handler(payload)
        re_dict["name"] = user.name if user.name else user.username

        headers = self.get_success_headers(serializer.data)
        return Response(re_dict, status=status.HTTP_201_CREATED, headers=headers)

    def get_object(self): # 影响RetrieveModelMixin DestroyModelMixin
        return self.request.user # http://127.0.0.1:8000/users/21421412/ 无论是什么只返回当前用户

    def perform_create(self, serializer):
        return serializer.save()

