# -*- coding:utf-8 -*-
def get_auth_url():
    weibo_auth_url = "https://api.weibo.com/oauth2/authorize"
    redirect_url = "http://132.232.148.220:8000/complete/webo"
    auth_url = weibo_auth_url+"?client_id={client_id}&redirect_uri={re_url}".format(client_id=4160810630, re_url=redirect_url)

    print(auth_url)
def get_access_token(code=""):
    access_token_url = "https://api.weibo.com/oauth2/access_token"
    import requests # 发送post请求
    re_dict = requests.post(access_token_url, data={
        "client_id": 4160810630,
        "client_secret": "954558f9ebd7671e28a7a280ad11dba9",
        "grant_type": "authorization_code",
        "code":code,  # 调用authorize获得的code值 登录后url上的code值
        "redirect_uri": "http://132.232.148.220:8000/complete/webo"
    })
    pass
# 返回值字段
# '{"access_token":"2.00VLzvjCsu1aXE3be7e096feu5_w4B","remind_in":"157679999","expires_in":157679999,"uid":"2511020677","isRealName":"true"}'
# access_token 用户授权的票据
# remind_in 过期时间
# expires_in 过期时间
# uid 授权用户的UID

def get_user_into(access_token="", uid=""):
    user_url = "https://api.weibo.com/2/users/show.json?access_token={token}&uid={uid}".\
        format(token=access_token, uid=uid)
    import requests
    red_url = requests.get(user_url)
    # 'https://api.weibo.com/2/users/show.json?access_token=2.00VLzvjCsu1aXE3be7e096feu5_w4B&uid=2511020677'
if __name__ == "__main__":
    get_auth_url() # 可在浏览器测试url 登录验证
    get_access_token(code="f41af5bdf294ef95f6aad560fe7c711d")
    get_user_into(access_token="2.00VLzvjCsu1aXE3be7e096feu5_w4B", uid="2511020677")