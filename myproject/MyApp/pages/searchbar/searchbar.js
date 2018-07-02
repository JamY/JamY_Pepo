Page({
    data: {
        inputShowed: false,
        inputVal: ""
    },
    showInput: function () {
        this.setData({
            inputShowed: true
        });
    },
    search: function () {
        this.setData({
            // inputVal: "",
            // inputShowed: false
        });
        getApp().globalData.city = this.data.inputVal;
        wx.switchTab({
          url: '../index/index',
        })
    },
    clearInput: function () {
        this.setData({
            inputVal: ""
        });
    },
    inputTyping: function (e) {
        this.setData({
            inputVal: e.detail.value
        });
        console.log(this.data.inputVal)
    }
});