# -*- coding: utf-8 -*-
from users.models import UserProfile

__author__ = 'bobby'
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

# django信号量
@receiver(post_save, sender=UserProfile, weak=False) # post_save接收信号
def create_user(sender, instance=None, created=True, **kwargs):
    print("ok")
    if created:
        print("ok1")
        password = instance.password
        instance.set_password(password)
        instance.save()
# 最后一步配置apps.UserConfig
# 在settings.py中修改INSTALL_APPS 'users.apps.UsersConfig'
