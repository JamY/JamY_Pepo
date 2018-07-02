var Parser = require("../../lib/dom-parser.js");
var MyJs = require("../../lib/MyJs.js");
var Weather = require("../../lib/weather.js");
Page({

  data: {
    weather: {},
    list:[{
        id:"temperature",
        open: false
    },{
        id:"forecast_0",
        open: false
    },{
        id:"forecast_1",
        open: false
    },{
        id:"forecast_2",
        open: false
    }]
  },

  onLoad: function (options) {
    // 生命周期函数--监听页面加载
  },
  onReady: function () {
    // 生命周期函数--监听页面初次渲染完成

  },
  onShow: function () {
    let that =this;
    // 生命周期函数--监听页面显示
    var curCity = getApp().globalData.city;
    console.log(curCity);
    if (curCity != null) {
      if(that.isErrorCity(curCity)){
        this.loadWeather(curCity.replace("市", ""));
      }
    } else {
      this.loadLocation();
    }
  },
  onHide: function () {
    // 生命周期函数--监听页面隐藏

  },
  onUnload: function () {
    // 生命周期函数--监听页面卸载

  },
  onPullDownRefresh: function () {
    // 页面相关事件处理函数--监听用户下拉动作

  },
  onReachBottom: function () {
    // 页面上拉触底事件的处理函数

  },
  onShareAppMessage: function () {
    // 用户点击右上角分享
    return {
      title: '天气-小程序', // 分享标题
      desc: '今天天气怎么样？', // 分享描述
      path: 'path' // 分享路径
    }
  },
  //获取当前的位置信息，即经纬度
  loadLocation: function () {
    var page = this;
    wx.getLocation({
      type: 'gcj02', // 默认为 wgs84 返回 gps 坐标，gcj02 返回可用于 wx.openLocation 的坐标
      success: function (res) {
        // success
        var latitude = res.latitude;
        var longitude = res.longitude;

        //获取城市
        page.loadCity(latitude, longitude);
      }
    })
  },

  //通过经纬度获取城市
  loadCity: function (latitude, longitude) {
    var page = this;
    var url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," + longitude + "&key=XSWBZ-EVQ3V-UMLPA-U4TP6-6MQFZ-UUFSL&get_poi=1";
    wx.request({
      url: url,
      data: {},
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      // header: {}, // 设置请求的 header
      success: function (res) {
        var city = res.data.result.address_component.city;
        city = city.replace("市", "");
        page.setData({ city: city });
        page.loadWeather(city);
      }
    })
  },

  loadWeather: function (city) {
    var page = this;
    // var res = "<ArrayOfString xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns='http://WebXml.com.cn/'><string>广东 潮</string><string>潮州</string><string>2361</string><string>2018/03/06 18:14:27</string><string>今日天气实况：气温：16℃；风向/风力：东南风 2级；湿度：72%</string><string>紫外线强度：弱。空气质量：中。</string><string>紫外线指数：弱，辐射较弱，涂擦SPF12-15、PA+护肤品。 健臻·血糖指数：不易波动，天气条件好，血糖不易波动，可适时进行户外锻炼。 感冒指数：较易发，温差较大，较易感冒，注意防护。 穿衣指数：较舒适，建议穿薄外套或牛仔裤等服装。 洗车指数：较适宜，无雨且风力较小，易保持清洁度。 空气污染指数：中，易感人群应适当减少室外活动。</string><string>3月6日 多云</string><string>13℃/23℃</string><string>无持续风向小于3级</string><string>1.gif</string><string>1.gif</string><string>3月7日 多云转小雨</string><string>14℃/23℃</string><string>无持续风向小于3级</string><string>1.gif</string><string>7.gif</string><string>3月8日 小雨转多云</string><string>9℃/15℃</string><string>无持续风向小于3级</string><string>7.gif</string><string>1.gif</string><string>3月9日 阴</string><string>8℃/21℃</string><string>无持续风向小于3级</string><string>2.gif</string><string>2.gif</string><string>3月10日 阴</string><string>8℃/22℃</string><string>无持续风向小于3级</string><string>2.gif</string><string>2.gif</string></ArrayOfString>"
    //method中设置你想调用的方法名
    var method = 'getWeather';
    //path中设置需要访问的webservice的url地址
    var path = 'http://ws.webxml.com.cn/WebServices/WeatherWS.asmx';
    //datacopy中拼字符串，即http传输中的soap信息
    var datacopy = '<?xml version="1.0" encoding="utf-8"?>';
    datacopy += '<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">';
    datacopy += '<soap12:Body>';
    datacopy += '<getWeather xmlns="http://WebXml.com.cn/">';
    //接着拼你要访问的方法名、参数名和你传递的实参值，比如我要访问的方法是getReader(String arg0,int arg1)
    //而我的实际调用是getWeather('#city')，所以拼字符串如下
    datacopy += '<theCityCode>';
    datacopy += city;
    datacopy += '</theCityCode>';
    datacopy += '<theUserID>';
    datacopy += '</theUserID>';
    datacopy += '</getWeather>';
    datacopy += '</soap12:Body>';
    datacopy += '</soap12:Envelope>';
    wx.request({
      url: path,
      data: datacopy,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {
        'Content-Type': 'application/soap+xml; charset=utf-8',
      },
      // 设置请求的 header
      success: function (res) {
        console.log(res);
        // var array = Weather.parseWeather(res.data);
        var arrData = MyJs.parseXML(res.data);
        if (arrData.length != 1){
          var array = Weather.parseWeather(arrData);
          page.setData({
            weather: {
              city: array[1], //查询的天气预报地区名称
              cityId: array[2], //查询的天气预报地区ID"
              date: array[3], //最后更新时间
              // others: array[5], //空气质量、紫外线强度
              temperature: array[4],//当天温度
              others: array[5],
              ult: "空气 " + array[6], //空气质量
              allIndex: array[7], //各项指数
              forecast: array[8] //3天气情况
            }
          });
        }else{
          page.re_Input();
        }
      },
    })
  },

  //判断城市是否合法
  isErrorCity: function (res) {
    // !isNaN(city) 判断是否为数字
    let that = this;
    if (res == "" || !isNaN(res)) {
      that.re_Input();
      return false;
    }
    return true;
  },
  re_Input :function (){
    wx.switchTab({
      url: '../searchbar/searchbar'
    })
    wx.showModal({
      title: '提示',
      content: '无效城市',
      showCancel: false
    })
  },
  
  showThis: function(res){
    let that = this;
    console.log(res);
    var arr = that.data.list;
    for (let i in arr){
      if(res.currentTarget.id == arr[i].id){
        arr[i].open = !arr[i].open;
        setTimeout(function () {
          arr[i].open = !arr[i].open;
        }, 1000);
      } 
    }
    that.setData({ list: arr});
    // setTimeout(function () {
    //   that.setData({
    //     open: false
    //   })
    // }, 1000);
  },

  gotoDetail: function (event) {
    // // console.log(this.data.areaid+"==在这里跳转=="+this.data.city);
    // wx.navigateTo({
    //   url: '../detail/detail?city=' + this.data.city + "&cityid=" + this.data.areaid
    // })
  }
})