/*
*	全国五级城市乡村联动 js版
*	author:		FuYin
*   download:   http://pan.baidu.com/share/home?uk=370326476&view=share#category/type=0
*	homepage:	http://weibo.com/fugardenia
*	E-mail:		fooying#qq.com
*	version:	1.0.0
*	data:		http://api.dangqian.com/apidiqu2/api.asp?format=json
**/
function provinceCallback(result) {
	    var obj=$("#s_province");
	    for(var i in result['list']){
	    	obj.append("<option value='"+result['list'][i]['daima']+"'>"+result['list'][i]['diming']+"</option>");
	    }
}
function cityCallback(result) {
	    var obj=$("#s_city");
	    for(var i in result['list']){
	    	obj.append("<option value='"+result['list'][i]['daima']+"'>"+result['list'][i]['diming']+"</option>");
	    }
}
function countyCallback(result) {
	    var obj=$("#s_county");
	    for(var i in result['list']){
	    	obj.append("<option value='"+result['list'][i]['daima']+"'>"+result['list'][i]['diming']+"</option>");
	    }
}
function townCallback(result) {
	    var obj=$("#s_town");
	    for(var i in result['list']){
	    	obj.append("<option value='"+result['list'][i]['daima']+"'>"+result['list'][i]['diming']+"</option>");
	    }
}
function villageCallback(result) {
	    var obj=$("#s_village");
	    for(var i in result['list']){
	    	obj.append("<option value='"+result['list'][i]['daima']+"'>"+result['list'][i]['diming']+"</option>");
	    }
}



function getDataById(callBack,id){
		var JSONP=document.createElement("script");
		JSONP.type="text/javascript";
		JSONP.src="http://api.dangqian.com/apidiqu2/api.asp?format=json&callback="+callBack+"&id="+id;
		document.getElementsByTagName("head")[0].appendChild(JSONP);
}
function _init(){
	getDataById("provinceCallback","000000000000");
}

function changeData(callBack,areaId){
	var val = $("#"+areaId).val();
	switch(areaId)
	{
	case "s_province":
	  $("#s_city option[value!=0]").remove();
	  $("#s_county option[value!=0]").remove();
	  $("#s_town option[value!=0]").remove();
	  $("#s_village option[value!=0]").remove();
	  break;
	case "s_city":
	  $("#s_county option[value!=0]").remove();
	  $("#s_town option[value!=0]").remove();
	  $("#s_village option[value!=0]").remove();
	  break;
	case "s_county":
	  $("#s_town option[value!=0]").remove();
	  $("#s_village option[value!=0]").remove();
	  break;
	case "s_town":
	  $("#s_village option[value!=0]").remove();
	  break;    
	default:
	  $("#s_city option[value!=0]").remove();
	  $("#s_county option[value!=0]").remove();
	  $("#s_town option[value!=0]").remove();
	  $("#s_village option[value!=0]").remove();
	}
	getDataById(callBack,val);
}