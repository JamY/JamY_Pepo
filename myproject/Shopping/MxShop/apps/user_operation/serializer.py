# -*- coding:utf-8 -*-
from rest_framework import serializers
from rest_framework.validators import UniqueTogetherValidator

from .models import UserFav
from .models import UserLeavingMessage
from .models import UserAddress

from goods.serializer import GoodsSerializer

class UserFavDetailSerializer(serializers.ModelSerializer):
    # 获取详细的外键信息
    goods = GoodsSerializer()
    class Meta:
        model = UserFav
        fields = ("goods", "id")

class UserFavSerializer(serializers.ModelSerializer):
    user = serializers.HiddenField( # 获取当前用户 填充为当前用户 隐藏
        default=serializers.CurrentUserDefault()
    )
    # goods = GoodsSerializer()
    class Meta:
        model = UserFav
        # 防止用户重复收藏
        validators = [
            UniqueTogetherValidator(
                queryset=UserFav.objects.all(),
                fields=('user', 'goods'),
                message="已经收藏" # 设置错误信息
            )
        ]
        # 或者.model.UserFav设置unique_together
        fields = ("user", "goods", "id")

class LeavingMessageSerializer(serializers.ModelSerializer):
    user = serializers.HiddenField(  # 获取当前用户 填充为当前用户 隐藏
        default=serializers.CurrentUserDefault()
    )
    add_time = serializers.DateTimeField(read_only=True, format="%Y-%m-%d %H:%M") # 只返回不提交 指定日期格式
    class Meta:
        model = UserLeavingMessage
        fields = ("user" ,"message_type", "subject", "message", "file", "id", "add_time")

class AddressSerializer(serializers.ModelSerializer):
    user = serializers.HiddenField(
        default=serializers.CurrentUserDefault()
    )
    add_time = serializers.DateTimeField(read_only=True, format="%Y-%m-%d %H:%M")
    # 待完成手机格式验证 是否为空 名称最长最小
    class Meta:
        model = UserAddress
        fields = ("id", "user", "province", "city", "district", "address", "signer_name", "signer_mobile", "add_time")