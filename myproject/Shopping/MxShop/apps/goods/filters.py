# # -*- coding:utf-8 -*-
# # import django_filters
import django_filters
from django.db.models import Q

from .models import Goods
#
#
class GoodsFilter(django_filters.rest_framework.FilterSet):
#     """
#     商品的过滤类
#     """
#     # 搜索shop_price某个区间的数据
    pricemin = django_filters.NumberFilter(field_name='shop_price', lookup_expr='gte', help_text='最低价格') # 大于等于
    pricemax = django_filters.NumberFilter(field_name='shop_price', lookup_expr='lte', help_text='最高价格') # 小于等于
    name = django_filters.CharFilter(field_name='name', lookup_expr='icontains') # 模糊查询 i 忽略大小写
    # 找到某类商品所有数据(一类目或二类目或三类目的商品数据)
    top_category = django_filters.NumberFilter(method='top_category_filter')
    def top_category_filter(self, quertset, name, value):
        return quertset.filter(Q(category_id=value)|Q(category__parent_category_id=value)|
                                   Q(category__parent_category__parent_category_id=value))
    class Meta:
        model = Goods
        fields = ['pricemin', 'pricemax', 'is_hot', 'id', "is_new"] # id 为自己添加的过滤属性
