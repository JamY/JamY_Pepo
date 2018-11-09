# -*- coding:utf-8 -*-
import re
from datetime import datetime, timedelta

from django.contrib.auth import get_user_model
from rest_framework import serializers
from rest_framework.validators import UniqueValidator

from MxShop.settings import REGEX_MOBILE
from .models import VerifyCode

User = get_user_model()
class SmsSerializer(serializers.Serializer):
    mobile = serializers.CharField(max_length=11)
    # 发送验证码并校验
    def validate_mobile(self, mobile): # validate_验证参数
        """
        验证手机号码
        :param data:
        :return:
        """
        # 手机是否注册
        if User.objects.filter(mobile=mobile).count():
            raise serializers.ValidationError("用户已经存在")
        # 验证手机号码是否合法
        if not re.match(REGEX_MOBILE, mobile):
            raise serializers.ValidationError("手机号码非法")
        # 验证码发送频率
        one_mintes_ago = datetime.now() - timedelta(hours=0, minutes=1, seconds=0)
        if VerifyCode.objects.filter(add_time__gt=one_mintes_ago, mobile=mobile).count():# gt大于
            raise serializers.ValidationError("距离上一次发送未超过60s")
        return mobile
class UserDetailSerializer(serializers.ModelSerializer):
    """
    用户详情序列化类
    """
    class Meta:
        model = User
        fields = ("name", "gender", "birthday", "email", "mobile")

class UserRegSerializer(serializers.ModelSerializer):
    # 自定义字段 必填字段 对字段进行约束
    code = serializers.CharField(required=True, write_only=True, max_length=4, min_length=4, label="验证码",
                                 error_messages={ # 设置验证错误信息
                                     "blank": "请输入验证码",
                                     "required": "请输入验证码",
                                     "max_length": "验证码格式错误",
                                     "min_length": "验证码格式错误"
                                 },# write_only 只写不显示
                                 help_text="验证码") # 验证完之后del
    username = serializers.CharField(label="用户名", help_text="请输入手机号当用户名", required=True, allow_blank=False,
                                     validators=[UniqueValidator(queryset=User.objects.all(), message="用户已经存在")])# 只写不显示
    password = serializers.CharField(
        style={'input_type': 'password'}, help_text="密码", label="密码", write_only=True,
    )# 页面显示效果成密文 保存到数据库是明文
    # 保存到数据库成密文
    # def create(self, validated_data):
    #     user = super(UserRegSerializer, self).create(validated_data=validated_data)
    #     user.set_password(validated_data["password"])
    #     user.save()
    #     return user
    # 或者使用django信号量

    # 验证用户输入的验证码
    def validate_code(self, code):
        # 验证手机验证码是否已存在-通过手号码查询验证码记录-排序(验证最后一条记录)
        # get()和filter()区别
        # get()返回超过1条/返回不存在 都可能抛异常
        # filter()返回数组
        verify_records = VerifyCode.objects.filter(mobile=self.initial_data["username"]).order_by("-add_time")
        # initial_data['mobile']通过http://127.0.0.1:8000/users/传过来的值 手机号 用户名共用 保存用户名为手机号
        if verify_records:
            last_record = verify_records[0]

            five_mintes_ago = datetime.now() - timedelta(hours=0, minutes=5, seconds=0)
            if five_mintes_ago > last_record.add_time:
                raise serializers.ValidationError("验证码过期")

            if last_record.code != code:
                raise serializers.ValidationError("验证码错误")

        else:
            raise serializers.ValidationError("验证码错误")
    def validate(self, attrs):# 作用于所有字段 attrs:serializer.data每个字段经过validate_code等函数验证之后的返回总的所有字段的dict
        attrs["mobile"] = attrs["username"] # 字段mobile赋值
        del attrs["code"]
        return attrs
    class Meta:
        model = User
        fields = ("username", "code", "mobile", "password") # 定义serializer.data
        # 可视化在http://127.0.0.1:8000/users/
