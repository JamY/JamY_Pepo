# -*- coding: utf-8 -*-

# pip install pycryptodome
__author__ = 'bobby'

from datetime import datetime
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5
from Crypto.Hash import SHA256
from base64 import b64encode, b64decode
from urllib.parse import quote_plus
from urllib.parse import urlparse, parse_qs
from urllib.request import urlopen
from base64 import decodebytes, encodebytes

import json


class AliPay(object):
    """
    支付宝支付接口
    """
    def __init__(self, appid, app_notify_url, app_private_key_path,
                 alipay_public_key_path, return_url, debug=False):
        self.appid = appid
        self.app_notify_url = app_notify_url
        self.app_private_key_path = app_private_key_path
        self.app_private_key = None
        self.return_url = return_url
        with open(self.app_private_key_path) as fp:
            self.app_private_key = RSA.importKey(fp.read()) # 读取私钥

        self.alipay_public_key_path = alipay_public_key_path
        with open(self.alipay_public_key_path) as fp:
            self.alipay_public_key = RSA.import_key(fp.read()) # 读取支付宝公钥


        if debug is True:
            self.__gateway = "https://openapi.alipaydev.com/gateway.do" # 沙箱环境的url
        else:
            self.__gateway = "https://openapi.alipay.com/gateway.do" # 正式环境的url

    def direct_pay(self, subject, out_trade_no, total_amount, return_url=None, **kwargs):
        biz_content = { # 请求参数的集合
            "subject": subject, # 商户订单号,
            "out_trade_no": out_trade_no, # 订单标题
            "total_amount": total_amount, # 订单总金额
            "product_code": "FAST_INSTANT_TRADE_PAY", # 销售产品码
            # "qr_pay_mode":4
        }

        biz_content.update(kwargs) # **kwargs 是可变参数 同时也会加到 biz_content
        data = self.build_body("alipay.trade.page.pay", biz_content, self.return_url) # 生成消息格式
        return self.sign_data(data) # 签名

    def build_body(self, method, biz_content, return_url=None): # 公共请求参数
        data = {
            "app_id": self.appid,  # 沙箱环境的appid
            "method": method, # 接口名称
            "charset": "utf-8",
            "sign_type": "RSA2", # 签名算法类型
            "timestamp": datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            "version": "1.0", # 接口版本
            "biz_content": biz_content # 请求参数的集合
        }

        if return_url is not None:
            data["notify_url"] = self.app_notify_url # 通知商户服务器里指定的页面http/https路径
            data["return_url"] = self.return_url # HTTP/HTTPS开头字符串

        return data

    def sign_data(self, data):
        data.pop("sign", None) # data 里面不能有sign字段
        # 排序后的字符串
        unsigned_items = self.ordered_data(data) # 支付宝规定要排序
        unsigned_string = "&".join("{0}={1}".format(k, v) for k, v in unsigned_items)
        sign = self.sign(unsigned_string.encode("utf-8"))
        ordered_items = self.ordered_data(data)
        quoted_string = "&".join("{0}={1}".format(k, quote_plus(v)) for k, v in ordered_items) # quote_plus对url 的预处理

        # 获得最终的订单信息字符串
        signed_string = quoted_string + "&sign=" + quote_plus(sign)
        return signed_string


    def ordered_data(self, data):
        complex_keys = []
        for key, value in data.items(): # 列表返回可遍历的(键, 值) 元组数组
            if isinstance(value, dict): # 判断一个对象是否是一个已知的类型 int float bool complex str list dict set tuple
                complex_keys.append(key) # complex_keys = [{biz_content}]

        # 将字典类型的数据dump出来
        for key in complex_keys:
            data[key] = json.dumps(data[key], separators=(',', ':'))# 将Python对象编码成JSON字符串

        return sorted([(k, v) for k, v in data.items()])

    def sign(self, unsigned_string):
        # 开始计算签名
        key = self.app_private_key
        signer = PKCS1_v1_5.new(key)
        signature = signer.sign(SHA256.new(unsigned_string))
        # base64 编码，转换为unicode表示并移除回车
        sign = encodebytes(signature).decode("utf8").replace("\n", "") # byte类型转字符串 decode转成utf-8的字符串
        return sign

    def _verify(self, raw_content, signature):
        # 开始计算签名
        key = self.alipay_public_key # 使用支付宝的公钥验证签名
        signer = PKCS1_v1_5.new(key)
        digest = SHA256.new()
        digest.update(raw_content.encode("utf8"))
        if signer.verify(digest, decodebytes(signature.encode("utf8"))):
            return True
        return False

    def verify(self, data, signature):
        if "sign_type" in data:
            sign_type = data.pop("sign_type")
        # 排序后的字符串
        unsigned_items = self.ordered_data(data)
        message = "&".join(u"{}={}".format(k, v) for k, v in unsigned_items)
        return self._verify(message, signature)


if __name__ == "__main__":
    return_url = 'http://132.232.148.220:8000/?charset=utf-8&out_trade_no=201702021225&method=alipay.trade.page.pay.return&total_amount=100.00&sign=wV6iaqY%2FVV%2FFspEGvJvPFiCqkvQqh5YA%2BpK2SV815%2BScHUFzD2YttAf4QU0BsvQEaRLYBWb0yFsNKxr0xUq%2FXpcP07Zvqyx7fzA5ZF1AVKhbHqaSa%2BZCypIEkA4ZkpruHbDSjDAY3uRmS4ej02MtKMd%2F1PncPmA3oCu5fELodeHyo0bHO0WgztDBvwLrlmA4r89FDBfARhVZHtPdpwrMqhMkLOiXmvdFf4TTa45QndJOLTGgZcI3kQFD8E1Enxcd0OxtWC91fVr%2B3w2DdE%2FnzIVRW0G4fNQUMXKVXqeacbi9SqUcEreGsBlDyYUyg3RE0tETP%2FndUxMKnO0SNrvfrQ%3D%3D&trade_no=2018100321001004820200728795&auth_app_id=2016092000558805&version=1.0&app_id=2016092000558805&sign_type=RSA2&seller_id=2088102176546851&timestamp=2018-10-03+22%3A37%3A24'

    alipay = AliPay(
        appid="2016092000558805", # 沙箱环境的appid
        app_notify_url="http://132.232.148.220:8000/alipay/return", # 订单支付后会发送一个异步请求 告知订单已支付
        # app_private_key_path=u"H:/VueShop/RSA/private_2048.txt",
        # alipay_public_key_path="H:/VueShop/RSA/ali_pub.txt",  # 支付宝的公钥，验证支付宝回传消息使用，不是你自己的公钥,
        app_private_key_path = "../trade/keys/private_2048.txt",
        alipay_public_key_path = "../trade/keys/alipay_key_2048.txt",
        debug=True,  # 默认False,
        return_url="http://132.232.148.220:8000/alipay/return" # 支付完成后跳转的页面
    )

    # 验证返回的地址是否有效 防止伪造
    o = urlparse(return_url)
    query = parse_qs(o.query)
    processed_query = {}
    ali_sign = query.pop("sign")[0] # 验证sign的第一个 只有这个
    for key, value in query.items():
        processed_query[key] = value[0]
    print (alipay.verify(processed_query, ali_sign))

    url = alipay.direct_pay(
        subject="测试订单",
        out_trade_no="201702021226", # 交易号 区分不同支付页面
        total_amount=100,
        return_url="http://132.232.148.220:8000/"
    )
    re_url = "https://openapi.alipaydev.com/gateway.do?{data}".format(data=url)
    print(re_url)
