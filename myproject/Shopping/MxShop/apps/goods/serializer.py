# -*- coding:utf-8 -*-
from django.db.models import Q
from rest_framework import serializers

from .models import Goods, GoodsCategory, GoodsImage, HotSearchWords, Banner, GoodsCategoryBrand, IndexAd


# class GoodsSerializer(serializers.Serializer):
#     # 映射相应的字段
#     name = serializers.CharField(required=True, max_length=100)
#     click_num = serializers.IntegerField(default=0)
#     goods_front_image = serializers.ImageField() #映射完整图片路径
#     # 从数据库取得数据后 在前部自动加上"/media"
#     def create(self, validated_data):
#         return Goods.objects.create(**validated_data) # 通过用户传递的json数据保存到数据库
# 三级类目
class CategorySerializer3(serializers.ModelSerializer):
    class Meta:
        model = GoodsCategory
        fields = "__all__"
# # 二级类目
class CategorySerializer2(serializers.ModelSerializer):
    sub_cat = CategorySerializer3(many=True)
    class Meta:
        model = GoodsCategory
        fields = "__all__"
# 一级类目
class CategorySerializer(serializers.ModelSerializer):
    sub_cat = CategorySerializer2(many=True) # 多个二级类目
    class Meta:
        model = GoodsCategory # Good的主键
        fields = "__all__"

class GoodsImageSerializer(serializers.ModelSerializer):
    class Meta:
        model = GoodsImage # Good的外键
        fields = ("image", )

class GoodsSerializer(serializers.ModelSerializer):
    # category是外键 序列化完整信息
    category =CategorySerializer()
    # 序列化以Goods为主键的GoodImage信息
    images = GoodsImageSerializer(many=True) # images从related_name得到
    class Meta:
        model = Goods
        # fields = ('name', 'click_num', 'market_price','add_time')
        fields = "__all__"

class HotWordsSerializer(serializers.ModelSerializer): # 热搜
    class Meta:
        model = HotSearchWords
        fields = "__all__"
class BannerSerializer(serializers.ModelSerializer): # 轮播图
    class Meta:
        model = Banner
        fields = "__all__"

class BrandSerializer(serializers.ModelSerializer): # 品牌
    class Meta:
        model = GoodsCategoryBrand
        fields = "__all__"

class IndexCategorySerializer(serializers.ModelSerializer): # 首页里面的商品类别
    brands = BrandSerializer(many=True) # 品牌 brands从GoodsCategoryBrand的related_name获得
    # goods = GoodsSerializer(many=True) # 取不到数据 因为类目分三级 good 取到属于第一类目的good
    goods = serializers.SerializerMethodField()
    sub_cat = CategorySerializer2(many=True) # 二类目商品分类
    ad_goods = serializers.SerializerMethodField() # 商品广告位

    def get_goods(self, obj): # obj 为 GoodsCategory
        # 查找三个类目的任一个
        all_goods = Goods.objects.filter(Q(category_id=obj.id)|Q(category__parent_category_id=obj.id)|Q(category__parent_category__parent_category_id=obj.id))
        goods_serializer = GoodsSerializer(all_goods, many=True, context={'request': self.context['request']})
        return goods_serializer.data
    def get_ad_goods(self, obj):
        goods_json = {}
        ad_goods = IndexAd.objects.filter(category_id=obj.id, )
        if ad_goods:
            good_ins = ad_goods[0].goods # ad_goods数组只有一值 拿到IndexAd里面的goods
            # 序列化
            goods_json = GoodsSerializer(good_ins, many=False, context={'request': self.context['request']}).data
            # serializer调serializer(嵌套) goods获取的数据含链接 不会传域名request  例media/goods/images/1_P_1449024889287.jpg viewssets自动传
            # 解决 加context={'request': self.context['request']}
        return goods_json

    class Meta:
        model = GoodsCategory
        fields = "__all__"

