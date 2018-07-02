var MyJs = require("./MyJs.js");
function parseWeather(data){
  var array = new Array();
  array[0] = data[0]; //省份 城市
  array[1] = data[1]; //城市
  array[2] = data[2]; //城市id
  array[3] = data[3]; //最后更新时间 格式 yyyy-MM-dd HH：mm:ss

  var arr_CurWd = splitCurWd(data[4]);
  array[4] = arr_CurWd[0]; //温度
  //arr_CurWd[1], arr_CurWd[2], arr_CurWd[3] //风向 风力 湿度

  var arr_CurWd2 = splitCurWd2(data[5]);
  array[5] = [{
    key: arr_CurWd[1],
    value: arr_CurWd[2]
  }, {
    key: "相对湿度",
    value: arr_CurWd[3]
  }, {
    key: "紫外线强度",
    value: arr_CurWd2[0]
  }]
  array[6] = arr_CurWd2[1]; //空气质量

  var arr_CurWd3 = data[6].split("。",6); //分割后的数组最后一个元素为空""
  var arr_CurWd3s = arr_CurWd3.join("：").split("："); 
  //pop() 方法用于删除并返回数组的最后一个元素。
  //join() 方法用于把数组中的所有元素放入一个字符串。
  array[7] = [];
  for (var i = 0; i < arr_CurWd3s.length/2; i++){
    array[7].push({ 
      key: arr_CurWd3s[2 * i],
      value: arr_CurWd3s[2 * i + 1]});
  }
  var split1 = data[7].split(" ");
  var split2 = data[12].split(" ");
  var split3 = data[17].split(" ");
  array[8] = [{
    date: split1[0],
    type: split1[1],
    temperature: data[8],
    wend: data[9],
    img1: data[10],
    img2: data[11]
  },{
      date: split2[0],
      type: split2[1],
      temperature: data[13],
      wend: data[14],
      img1: data[15],
      img2: data[16]
  },{
      date: split3[0],
      type: split3[1],
      temperature: data[18],
      wend: data[19],
      img1: data[20],
      img2: data[21]
  }]
  return array;
}
function splitCurWd(curWd){
  var temperature = curWd.substring(find(curWd, "：", 2) + 1 ,curWd.indexOf("；"));//温度
  var wind = curWd.substring(find(curWd, "：", 3) + 1 ,find(curWd, "；" ,2)).split(" ");//风力 风向
  var windPower = wind[0];
  var windDirection = wind[1];
  var hum = curWd.substring(find(curWd, "：" , 4) + 1 ,curWd.length);//湿度
  var array = new Array();
  array[0] = temperature;
  array[1] = windPower;
  array[2] = windDirection;
  array[3] = hum;
  return array;
}

function splitCurWd2(curWd){
  var array = new Array()
  var ult =  curWd.substring(find(curWd, "：", 1) + 1, find(curWd, "。", 1)); //紫外线强度
  var air = curWd.substring(find(curWd, "：", 2) + 1, find(curWd, "。", 2)); // 空气质量
  array[0] = ult;
  array[1] = air;
  return array;
}

//检索字符串 某字符串在该字符串第num次出现的位置
/**
 * str: 字符串
 * cha: 检索的字符串
 * num: 第几次出现
 * return: 索引位置
 */
function find(str, cha, num) {
  var x = str.indexOf(cha);
  for (var i = 0; i < num - 1; i++) {
    x = str.indexOf(cha, x + 1);
  }
  return x;
}
module.exports.splitCurWd = splitCurWd;
module.exports.parseWeather = parseWeather;