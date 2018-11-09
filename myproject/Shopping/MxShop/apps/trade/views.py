# -*- coding:utf-8 -*-
from datetime import datetime

from django.shortcuts import redirect
from rest_framework import viewsets, mixins
from rest_framework.authentication import SessionAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework_jwt.authentication import JSONWebTokenAuthentication

from trade.models import ShoppingCart, OrderInfo, OrderGoods
from trade.serializer import ShopCartSerializer, ShopCartDetailSerializer, OrderSerializer, OrderDetailSerializer
from utils.permissions import IsOwnerOrReadOnly


class ShoppingCartViewset(viewsets.ModelViewSet):
    """
    购物车功能
    list:
        获取购物车详情
    create:
        加入购物车
    delete:
        删除购物记录
    """
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = ShopCartSerializer
    lookup_field = "goods_id" # 是用于RetrieveModelMixin查询某一条记录，而不是ShoppingCart的id

    # 处理商品库存 新增商品到购物车
    def perform_create(self, serializer):
        shop_cart = serializer.save()
        goods = shop_cart.goods
        goods.goods_num -= shop_cart.nums
        goods.save()

    # 删除购物车数量
    def perform_destroy(self, instance):
        goods = instance.goods
        goods.goods_num += instance.nums
        goods.save()
        instance.delete()

    # 修改购物车数量
    def perform_update(self, serializer):
        existed_record = ShoppingCart.objects.get(id=serializer.instance.id)
        existed_nums = existed_record.nums
        saved_record = serializer.save()
        nums = saved_record.nums - existed_nums
        goods = saved_record.goods
        goods.goods_num -= nums
        goods.save()

    # 动态设置serializer
    def get_serializer_class(self):
        if self.action == "list":
            return ShopCartDetailSerializer # 同时显示Goods的详细信息
        else:
            return ShopCartSerializer

    # queryset = ShoppingCart.objects.all() # 必要
    def get_queryset(self):
        return ShoppingCart.objects.filter(user=self.request.user) # 只返回当前用户的购物车

class OrderViewset(mixins.ListModelMixin, mixins.RetrieveModelMixin, mixins.CreateModelMixin, mixins.DestroyModelMixin, viewsets.GenericViewSet):
    """
    订单管理
    list:
        获取个人订单
    delete:
        删除个人订单
    create:
        新增订单
    """
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = OrderSerializer

    def get_queryset(self):
        return OrderInfo.objects.filter(user=self.request.user)

    def get_serializer_class(self):
        if self.action == "retrieve":
            return OrderDetailSerializer
        return OrderSerializer

    # 重写CreateModelMixin里的的perform_create() 保存之前
    def perform_create(self, serializer):
        order = serializer.save() # 保存OrderInfo(订单)并返回
        # 1.从购物车里的数据生成订单
        shop_carts = ShoppingCart.objects.filter(user=self.request.user)
        for shop_cart in shop_carts:
            order_goods = OrderGoods()
            order_goods.goods = shop_cart.goods
            order_goods.goods_num = shop_cart.nums
            order_goods.order = order # 删除的时候是级联删除
            order_goods.save()
            # 2.清空购物车
            shop_cart.delete()
        return order

from rest_framework.views import APIView
from utils.alipay import AliPay
from MxShop.settings import ali_pub_key_path, private_key_path
from rest_framework.response import Response
class AlipayView(APIView): # 最底层的View
    def get(self, request):
        """
        处理支付宝的return_url返回
        :param request:
        :return:
        """
        processed_dict = {}
        for key, value in request.GET.items():
            processed_dict[key] = value

        sign = processed_dict.pop("sign", None)

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

        verify_re = alipay.verify(processed_dict, sign) # 验证是否为有效支付

        if verify_re is True:
            order_sn = processed_dict.get('out_trade_no', None) # 商户订单号 自己生成
            trade_no = processed_dict.get('trade_no', None) # 支付宝交易号 回返生成
            # trade_status = processed_dict.get('trade_status', None) # 交易状态 新版return_url 没有trade_status
            trade_status = "TRADE_SUCCESS"

            existed_orders = OrderInfo.objects.filter(order_sn=order_sn)
            for existed_order in existed_orders:
                existed_order.pay_status = trade_status
                existed_order.trade_no = trade_no
                existed_order.pay_time = datetime.now()
                existed_order.save()

            response = redirect("http://127.0.0.1:8080") # 返回首页
            response.set_cookie("nextPath", "pay", max_age=3) # cookie与vue页面对应 由前端控制跳转页面
            return response
        else:
            response = redirect("http://127.0.0.1:8080")
            return response

    def post(self, request):
        """
        处理支付宝的notify_url
        :param request:
        :return:
        """
        processed_dict = {}
        for key, value in request.POST.items():
            processed_dict[key] = value

        sign = processed_dict.pop("sign", None)

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

        verify_re = alipay.verify(processed_dict, sign)

        if verify_re is True:
            order_sn = processed_dict.get('out_trade_no', None)
            trade_no = processed_dict.get('trade_no', None)
            # trade_status = processed_dict.get('trade_status', None)
            trade_status = "TRADE_SUCCESS"
            # 销量修改
            existed_orders = OrderInfo.objects.filter(order_sn=order_sn)
            for existed_order in existed_orders:
                order_goods = existed_order.goods.all() # OrderInfo取OrderGoods反向取的方法
                for order_good in order_goods:
                    goods = order_good.goods
                    goods.sold_num += order_good.goods_num
                    goods.save()

                existed_order.pay_status = trade_status
                existed_order.trade_no = trade_no
                existed_order.pay_time = datetime.now()
                existed_order.save()

            return Response("success")