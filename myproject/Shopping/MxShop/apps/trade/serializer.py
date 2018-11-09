# -*- coding:utf-8 -*-
import time

from rest_framework import serializers

from MxShop.settings import private_key_path, ali_pub_key_path
from goods.models import Goods
from goods.serializer import GoodsSerializer
from trade.models import ShoppingCart, OrderInfo, OrderGoods
from utils.alipay import AliPay


class ShopCartDetailSerializer(serializers.ModelSerializer):
    goods = GoodsSerializer(many=False,read_only=True) # 一个ShopCart记录对应只有一个Goods
    class Meta:
        model = ShoppingCart
        fields = ("goods", "nums")

class ShopCartSerializer(serializers.Serializer):
    # 如果使用 serializers.ModelSerializer
    # 当action="create" 因为设置了unique_together,当重复时
    # 进入不了def create(self, validated_data)方法
    user = serializers.HiddenField(
        default=serializers.CurrentUserDefault()
    )
    nums = serializers.IntegerField(required=True, min_value=1, label="数量",
                                    error_messages={
                                        "min_value": "商品数量不能小于1",
                                        "required": "请选择购买数量"
                                    })
    goods = serializers.PrimaryKeyRelatedField(required=True, queryset=Goods.objects.all())
    def create(self, validated_data):
        # 判断记录存不存在
        # 获取数据
        user = self.context["request"].user
        nums = validated_data["nums"]
        goods = validated_data["goods"]

        existed = ShoppingCart.objects.filter(user=user, goods=goods)

        if existed:
            existed = existed[0]
            existed.nums +=nums
            existed.save()
        else:
            existed = ShoppingCart.objects.create(**validated_data) # 创建
        return existed # 数据返回前端

    # 继承serializers.Serializer，如果必要，需要自己重写update delete不需要
    def update(self, instance, validated_data):
        # 修改商品数量
        instance.nums = validated_data["nums"]
        instance.save()
        return instance
    # class Meta:
    # 继承serializers.Serializer无法使用class Meta

class OrderGoodsSerialzier(serializers.ModelSerializer):
    # 显示外键的详细信息
    goods = GoodsSerializer(many=False) # 暂时发现many=False与""没有区别
    class Meta:
        model = OrderGoods
        fields = "__all__"


class OrderDetailSerializer(serializers.ModelSerializer):
    goods = OrderGoodsSerialzier(many=True)
    # alipay_url = serializers.SerializerMethodField(read_only=True)

    # def get_alipay_url(self, obj):
    #     alipay = AliPay(
    #         appid="",
    #         app_notify_url="http://127.0.0.1:8000/alipay/return/",
    #         app_private_key_path=private_key_path,
    #         alipay_public_key_path=ali_pub_key_path,  # 支付宝的公钥，验证支付宝回传消息使用，不是你自己的公钥,
    #         debug=True,  # 默认False,
    #         return_url="http://127.0.0.1:8000/alipay/return/"
    #     )
    #
    #     url = alipay.direct_pay(
    #         subject=obj.order_sn,
    #         out_trade_no=obj.order_sn,
    #         total_amount=obj.order_mount,
    #     )
    #     re_url = "https://openapi.alipaydev.com/gateway.do?{data}".format(data=url)
    #
    #     return re_url

    # 订单详情也需要用 所以与下面一样
    alipay_url = serializers.SerializerMethodField(read_only=True)
    def get_alipay_url(self, obj):# 函数对应 alipay_url
        alipay = AliPay(
            appid="2016092000558805",
            # app_notify_url="http://132.232.148.220:8000/alipay/return/",
            app_notify_url="http://127.0.0.1:8000/alipay/return/",
            app_private_key_path=private_key_path,
            alipay_public_key_path=ali_pub_key_path,  # 支付宝的公钥，验证支付宝回传消息使用，不是你自己的公钥,
            debug=True,  # 默认False,
            # return_url="http://132.232.148.220:8000/alipay/return/"
            return_url="http://127.0.0.1:8000/alipay/return/"
        )

        url = alipay.direct_pay(
            subject=obj.order_sn,
            out_trade_no=obj.order_sn,
            total_amount=obj.order_mount,
        )
        re_url = "https://openapi.alipaydev.com/gateway.do?{data}".format(data=url)

        return re_url

    class Meta:
        model = OrderInfo
        fields = "__all__"

class OrderSerializer(serializers.ModelSerializer):
    user = serializers.HiddenField(
        default=serializers.CurrentUserDefault()
    )
    pay_status = serializers.CharField(read_only=True)
    trade_no = serializers.CharField(read_only=True)
    order_sn = serializers.CharField(read_only=True)
    pay_time = serializers.DateTimeField(read_only=True)
    alipay_url = serializers.SerializerMethodField(read_only=True) # 用于生成支付链接

    def get_alipay_url(self, obj):# 函数对应 alipay_url
        alipay = AliPay(
            appid="2016092000558805",
            # app_notify_url="http://132.232.148.220:8000/alipay/return/",
            app_notify_url="http://127.0.0.1:8000/alipay/return/",
            app_private_key_path=private_key_path,
            alipay_public_key_path=ali_pub_key_path,  # 支付宝的公钥，验证支付宝回传消息使用，不是你自己的公钥,
            debug=True,  # 默认False,
            # return_url="http://132.232.148.220:8000/alipay/return/"
            return_url="http://127.0.0.1:8000/alipay/return/"
        )

        url = alipay.direct_pay(
            subject=obj.order_sn,
            out_trade_no=obj.order_sn,
            total_amount=obj.order_mount,
        )
        re_url = "https://openapi.alipaydev.com/gateway.do?{data}".format(data=url)

        return re_url

    # 获取当前时间并且格式化成字符串
    # 生成区间数
    def generate_order_sn(self):
        # 当前时间+userid+随机数
        from random import Random
        random_ins = Random()
        order_sn = "{time_str}{userid}{ranstr}".format(time_str = time.strftime("%Y%m%d%H%M%S"),
                                                       userid = self.context["request"].user.id,
                                                       ranstr = random_ins.randint(10, 99))
        # 在Serializer取user self.context["request"].user
        # 在Viewset取user self.request.user
        return  order_sn

    def validate(self, attrs):
        # 填充订单号 # 成订单号
        attrs["order_sn"] = self.generate_order_sn()
        return  attrs

    class Meta:
        model = OrderInfo
        fields = "__all__"



