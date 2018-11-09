from datetime import datetime

from django.contrib.auth.models import AbstractUser
from django.db import models

# Create your models here.
class UserProfile(AbstractUser):
    # 用户
    name = models.CharField(max_length=30, null=True, blank=True, verbose_name="姓名")
    birthday = models.DateField(null=True, blank=True, verbose_name="出生年月")
    gender = models.CharField(max_length=6, choices=(("male", u"男"), ("female", "女")), default="female",
                              verbose_name="性别")
    mobile = models.CharField(null=False, blank=True, max_length=11, verbose_name="电话") #用户注册的时候mobile不可以为null
    email = models.EmailField(max_length=100, null=True, blank=True, verbose_name="邮箱")

    class Meta:
        verbose_name = '用户'
        verbose_name_plural = verbose_name
    def __str__(self):
        return self.username

class VerifyCode(models.Model):
    # 短信验证码
    code = models.CharField(max_length=10, verbose_name="验证码") # verbose_name设置详细名称
    mobile = models.CharField(max_length=11, verbose_name="电话")
    add_time = models.DateTimeField(default=datetime.now, verbose_name="添加时间")
    # datetime.now()保存系统编译时间 datetime.now保存添加数据时间
    class Meta:
        verbose_name = '短信验证码'
        verbose_name_plural = verbose_name
    def __str__(self):
        return self.code

