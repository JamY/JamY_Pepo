# -*- coding:utf-8 -*-
import json

from django.http import HttpResponse
from django.views.generic.base import View

from goods.models import Goods


class GoodsListView(View):
    def get(self, request):
        """
        通过django
        :param request:
        :return:
        """
        json_list=[]
        goods=Goods.objects.all()[:10]
        # for good in goods:
        #     json_dict = {}
        #     json_dict['name'] = good.name
        #     json_dict['category'] = good.category.name
        #     json_dict['market_price'] = good.market_price
        #     json_list.append(json_dict)

        # for good in goods:
        #     from django.forms import model_to_dict
        #     json_dict = model_to_dict(good) # 将model转dict 但是存在'ImageFieldFile' is not JSON serializable问题
        #     json_list.append(json_dict)
        # return HttpResponse(json.dumps(json_list), content_type="application/json")

        # json.dumps()转换为JSON对象 json.loads()JSON对象转为Python对象
        # 对字段进行序列化
        from django.core import serializers
        json_data = serializers.serialize("json", goods) # 返回json对象
        # return HttpResponse(json_data, content_type="application/json")
        # 或者
        from django.http import JsonResponse
        json_data = json.loads(json_data)
        return JsonResponse(json_data, safe=False)
