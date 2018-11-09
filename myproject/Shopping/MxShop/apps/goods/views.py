from django.shortcuts import render

# Create your views here.
from django_filters.rest_framework import DjangoFilterBackend, OrderingFilter
from rest_framework import status, mixins, generics, viewsets, filters
from rest_framework.authentication import TokenAuthentication
from rest_framework.pagination import PageNumberPagination
from rest_framework.response import Response
from rest_framework.throttling import UserRateThrottle, AnonRateThrottle
from rest_framework.views import APIView
from rest_framework_extensions.cache.mixins import CacheResponseMixin

from .filters import GoodsFilter
from .models import Goods, GoodsCategory, HotSearchWords, Banner
from .serializer import GoodsSerializer, CategorySerializer, HotWordsSerializer, BannerSerializer, \
    IndexCategorySerializer


# class GoodsListView(APIView):
#     """
#     list all goods
#     """
#     def get(selfm, request, format=None):
#         goods = Goods.objects.all()[:10]
#         goods_serializer = GoodsSerializer(goods, many=True) #  many=True序列化成数组对象
#         return Response(goods_serializer.data)
#
#     # 添加功能由xadmin完成，所以不需要post函数
#     # def post(self, request, format=None):
#     #     serializer = GoodsSerializer(data=request.data)
#     #     if serializer.is_valid():# 验证合法性 根据GoodsSerializer
#     #         serializer.save()# 调用GoodsSerializer的get()方法
#     #         return Response(serializer.data, status=status.HTTP_201_CREATED)# 保存成功201# 指明返回的状态码
#     #     return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# class GoodsListView(mixins.ListModelMixin, generics.GenericAPIView):
#     """
#     商品列表页
#     """
#     queryset = Goods.objects.all()[:10]
#     serializer_class = GoodsSerializer
#     def get(self, request, *args, **kwargs):
#         return self.list(request, *args, **kwargs)

class GoodsPagination(PageNumberPagination):
    page_size = 12
    page_size_query_param = 'page_size' #链接访问加上&page_size动态设置分页
    page_query_param = 'page'
    max_page_size = 100

# 等同于class GoodsListView(mixins.ListModelMixin, generics.GenericAPIView)
# class GoodsListView(generics.ListAPIView):
#     """
#     商品列表页
#     """
#     queryset = Goods.objects.all()
#     serializer_class = GoodsSerializer
#     # 因为ListAPIView继承mixins.ListModelMixin,GenericAPIView并重写get
#     # CreateAPIView重写post()
#     # DestroyAPIView 重写delete()
#     pagination_class = GoodsPagination

class GoodsListViewSet(CacheResponseMixin, mixins.ListModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    """
    商品列表页，分页，搜索，过滤，排序
    """
    # pip install drf-extensions
    # CacheResponseMixin drf 的缓存设置 放在第一个 适用于 retrieve list
    # mixins.RetrieveModelMixin 完成商品详情页 http://127.0.0.1:8000/goods/1/
    throttle_classes = (UserRateThrottle, AnonRateThrottle) # drf 的throttle访问限速
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = GoodsPagination
    # GenericViewSet(viewset) -drf
    #     GenericAPIView      -drf
    #         APIView         -drf
    #             View        -django
    # mixin
    #     CreateModeMixin
    #     ListModeMixin
    #     UpdateModelMixin
    #     RetriveveModelMixin
    #     DestoryModelMixin

    # def get_queryset(self):
    #     # return Goods.objects.filter(shop_price__gt=100) # 过滤shop_price大于100
    #     queryset = Goods.objects.all()
    #     price_min = self.request.query_params.get("price_min", 0) # 设置请求参数 默认0
    #     if price_min:
    #         queryset = queryset.filter(shop_price__gt=int(price_min))
    #     return queryset

    # 类中必须有设置queryset或get_queryset() 否则 访问http://127.0.0.1:8000/goods/错误
    # 没有设置queryset属性，那么就urls.py register()方法必须设置base_name

    # filter_backends = (DjangoFilterBackend,) # 使用过滤器
    filter_backends = (DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter) # 使用搜索 排序
    # filter_fields = ('name', 'shop_price') # 使用过滤
    filter_class = GoodsFilter # 使用FilterSet过滤
    # authentication_classes = (TokenAuthentication, ) # 局部token
    search_fields = ('name', 'goods_brief', 'goods_desc')
    # 搜索的限制
    # ^name开头匹配
    # =name完全匹配
    # name$表示以什么结尾
    # 排序的字段
    ordering_fields = ('sold_num', 'shop_price')

    # 处理点击数
    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.click_num += 1
        instance.save()
        serializer = self.get_serializer(instance)
        return Response(serializer.data)

class CategoryViewset(mixins.ListModelMixin, viewsets.GenericViewSet, mixins.RetrieveModelMixin):
    """
    list:
        商品分类列表数据
    """
    # 跟models.py的related_name保持一致
    queryset = GoodsCategory.objects.filter(category_type=1)
    serializer_class = CategorySerializer
    # 获取某个商品详情 继承 mixins.RetrieveModelMixin
    # http://127.0.0.1:8000/categorys/1/
class HotSearchsViewset(mixins.ListModelMixin, viewsets.GenericViewSet):
    """
    获取热搜词列表
    """
    queryset = HotSearchWords.objects.all().order_by("-index")
    serializer_class = HotWordsSerializer
class BannerViewset(mixins.ListModelMixin, viewsets.GenericViewSet):
    """
    获取轮播图列表
    """
    queryset = Banner.objects.all().order_by("index")
    serializer_class = BannerSerializer

class IndexCategoryViewset(mixins.ListModelMixin, viewsets.GenericViewSet):
    """
    首页商品分类数据
    """
    queryset = GoodsCategory.objects.filter(is_tab=True, name__in=["生鲜食品", "酒水饮料"]) # is_tab 选出几个 只取这两个
    serializer_class = IndexCategorySerializer


