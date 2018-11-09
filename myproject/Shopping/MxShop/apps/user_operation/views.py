from django.shortcuts import render

# Create your views here.
from rest_framework import mixins, viewsets
from rest_framework.authentication import SessionAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework_jwt.authentication import JSONWebTokenAuthentication

from utils.permissions import IsOwnerOrReadOnly
from .models import UserFav, UserLeavingMessage, UserAddress
from .serializer import UserFavSerializer, UserFavDetailSerializer, LeavingMessageSerializer, AddressSerializer


class UserFavViewset(mixins.CreateModelMixin,mixins.ListModelMixin, mixins.DestroyModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    """
    list:
        获取用户收藏列表
    retrieve:
        判断某个商品是否已经收藏
    create:
        收藏商品
    """
    # queryset = UserFav.objects.all()
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    # IsAuthenticated 访问该接口需要登录认证
    # IsOwnerOrReadOnly 删除的时候必须是当前用户的收藏记录
    # serializer_class = UserFavSerializer
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication) # 设置jwt-token认证模式 Session认证模式
    # 请求时 Header 自带Authorization:JWT tokenkey
    lookup_field = "goods_id" # http://127.0.0.1:8000/userfavs/2/根据goods_id搜索数据 基于get_queryset()返回的数据
    def get_queryset(self):
        return UserFav.objects.filter(user=self.request.user) # 用户只能查看自己收藏的记录

    # 动态设置serializer
    def get_serializer_class(self):
        if self.action == "list":
            return UserFavDetailSerializer
        elif self.action == "create":
            return UserFavSerializer
        return UserFavSerializer # 其他情况

    # 处理收藏数 或者使用信号量
    # def perform_create(self, serializer):
    #     instance = serializer.save()
    #     goods = instance.goods
    #     goods.fav_num += 1
    #     goods.save()

class LeavingMessageViewset(mixins.ListModelMixin, mixins.DestroyModelMixin, mixins.CreateModelMixin,
                            viewsets.GenericViewSet):
    """
    list:
        获取用户留言
    create:
        添加留言
    delete:
        删除留言功能
    """
    serializer_class = LeavingMessageSerializer
    # 用户必须是登录状态
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    def get_queryset(self):
        return UserLeavingMessage.objects.filter(user=self.request.user) # 用户只能获取自己的留言

class AddressViewset(viewsets.ModelViewSet):
    # ModelViewSet包含 CreateModelMixin,RetrieveModelMixin,UpdateModelMixin,DestroyModelMixin,ListModelMixin,GenericViewSet
    # 包含增删改查
    """
    收货地址管理
    list:
        获取收货地址
    create:
        添加收货地址
    update:
        更新收货地址
    delete:
        删除收货地址
    """
    # 用户必须是登录状态
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = AddressSerializer
    def get_queryset(self):
        return UserAddress.objects.filter(user=self.request.user)  # 用户只能获取自己的收货地址

