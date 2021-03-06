# 微信小程序学习实例-天气实例

作为第一个实例主要搞清楚微信小程序的开发思路及流程。

在这个例子中所用到的功能大致有：

+ 自动定位获取经纬度
+ 根据经纬度获取所在城市及城市id
+ 根据城市名称和城市id获取当地天气情况
+ 模板template的使用（多数据传递）
+ wx.request的使用（用来调用远程服务端的数据）
+ 数据绑定（和AngularJS的双向数据绑定类似）
+ wx.navigateTo（在首页点击当天气温即可跳转到详情页面）

截图如下：

![微信小程序-天气 首页](https://github.com/JamY/JamY_Pepo/tree/master/myproject/MyApp/image-folder/2018-08-24_172257.jpg "微信小程序-天气 首页")

![微信小程序-天气 详情页](https://github.com/JamY/JamY_Pepo/tree/master/myproject/MyApp/image-folder/2018-08-24_172339.jpg "微信小程序-天气 详情页")

![微信小程序-天气 搜索页](https://github.com/JamY/JamY_Pepo/tree/master/myproject/MyApp/image-folder/2018-08-24_172355.jpg "微信小程序-天气 搜索页")