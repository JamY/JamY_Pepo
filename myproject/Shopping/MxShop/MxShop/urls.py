"""MxShop URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from django.urls import path, include
from django.views.generic import TemplateView
from django.views.static import serve
from rest_framework.authtoken import views
from rest_framework.documentation import include_docs_urls
from rest_framework.routers import DefaultRouter
from rest_framework_jwt.views import obtain_jwt_token

import xadmin
from MxShop.settings import MEDIA_ROOT
# from goods.views import GoodsListView # 使用drf serializers
from goods.views import GoodsListViewSet, CategoryViewset, HotSearchsViewset, BannerViewset, \
    IndexCategoryViewset  # 使用 viewsets
# from goods.views_base import GoodsListView # 使用django serializers
from trade.views import ShoppingCartViewset, OrderViewset, AlipayView
from user_operation.views import UserFavViewset
from user_operation.views import LeavingMessageViewset
from user_operation.views import AddressViewset

from users.views import SmsCodeViewset, UserViewset

# 使用router
router = DefaultRouter()
router.register(r'goods', GoodsListViewSet, base_name="goods") # base_name=''用于创建url的基础名字 过滤时需加
router.register(r'categorys', CategoryViewset, base_name="categorys")
router.register(r'codes', SmsCodeViewset, base_name="codes")
router.register(r'hotsearchs', HotSearchsViewset, base_name="hotsearchs")
router.register(r'users', UserViewset, base_name="users")
#收藏
router.register(r'userfavs', UserFavViewset, base_name="userfavs")
#留言
router.register(r'messages', LeavingMessageViewset, base_name="messages")
#收货地址
router.register(r'address', AddressViewset, base_name="address")
#购物车
router.register(r'shopcarts', ShoppingCartViewset, base_name="shopcarts")
#订单相关
router.register(r'orders', OrderViewset, base_name="orders")
#轮播图url
router.register(r'banners', BannerViewset, base_name="banners")
#首页商品系列数据
router.register(r'indexgoods', IndexCategoryViewset, base_name="indexgoods")
# goods_list = GoodsListViewSet.as_view({
#     'get': 'list',
#     # 'post': 'create'
# })

urlpatterns = [
    path('admin/', admin.site.urls),
    url(r'^xadmin/', xadmin.site.urls),
    url(r'^media/(?P<path>.*)$', serve, {"document_root": MEDIA_ROOT}),
    # 商品列表页
    # url(r'goods/$', GoodsListView.as_view(), name="goods-list"),
    # url(r'goods/$', goods_list, name="goods-list"),
    # 使用 router
    url('^', include(router.urls)),
    # 使用生成自动文档的配置k r'docs/'$不能加 $ 符号
    url(r'docs/', include_docs_urls(title="沐雪生鲜")),
    # drf配置
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    # 获取 测试 token
    # drf自带的token认证模式
    url(r'api-token-auth/', views.obtain_auth_token),
    #jwt的认证接口
    # url(r'^jwt_auth/', obtain_jwt_token),
    url(r'^login/$', obtain_jwt_token), # login/首先进入该函数 由于第三方登录urls中也有 login 所以加上$ 精确链接只能是login/才能进入该函数
    # 支付返回
    url(r'^alipay/return', AlipayView.as_view(), name="alipay"),
    # django代理vue页面
    url(r'^index/', TemplateView.as_view(template_name="index.html"), name="index"),
    # 第三方登录url
    url('', include('social_django.urls', namespace='social')),
]
