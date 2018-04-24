var box1Div,box2Div,msgDic,img1;
window.onload = function(){
	box1Div = document.getElementById("box1");
	box2Div = document.getElementById("box2");
	msgDiv = document.getElementById("msg");
	img1 = document.getElementById("img1");

	box1Div.ondragover = function(e){
		e.preventDefault();
	}
	box2Div.ondragover = function(e){
		e.preventDefault();
	}
	img1.ondragstart = function(e){
		e.dataTransfer.setData("imgId", "img1");
	}
	box1Div.ondrop = dropImghandler;
	box2Div.ondrop = dropImghandler;
}

function dropImghandler(e){
	showObj(e.dataTransfer);
	e.preventDefault();
	var img = document.getElementById(e.dataTransfer.getData("imgId"));
	e.target.appendChild(img);
}

function showObj(obj){
	var s = "";
	for(var k in obj){
		s += ":" + obj[k] + "<br/>";
	}
	msgDiv.innerHTML = s;
}
/*	
	ondragstart 规定当元素被拖动
	ondragover 事件规定在何处放置被拖动的数据。
	ondrop 当放置被拖数据时，会发生 drop 事件。
	preventDefault() 默认地，无法将数据/元素放置到其他元素中。如果需要设置允许放置，我们必须阻止对元素的默认处理方式。
*/