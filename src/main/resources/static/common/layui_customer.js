//日期格式转换
//使用：,{field:'startTime', title: '本周起始日期', sort: true, templet: '<div>{{ layui.laytpl.toDateString(d.createTime,"yyyy-MM-dd") }}</div>'}
//或者,{field:'startTime', title: '本周起始日期', sort: true, templet: '<div>{{ layui.laytpl.toDateString(d.createTime) }}</div>'}
layui.use(['form','table', 'layedit', 'laydate','element','jquery','upload'], function() {
	var form = layui.form;
	//时间戳的处理
	layui.laytpl.toDateString = function(d, format){
		if(d ==null){
			return "";
		}
	  var date = new Date(d || new Date())
	  ,ymd = [
	    this.digit(date.getFullYear(), 4)
	    ,this.digit(date.getMonth() + 1)
	    ,this.digit(date.getDate())
	  ]
	  ,hms = [
	    this.digit(date.getHours())
	    ,this.digit(date.getMinutes())
	    ,this.digit(date.getSeconds())
	  ];

	  format = format || 'yyyy-MM-dd HH:mm:ss';

	  return format.replace(/yyyy/g, ymd[0])
	  .replace(/MM/g, ymd[1])
	  .replace(/dd/g, ymd[2])
	  .replace(/HH/g, hms[0])
	  .replace(/mm/g, hms[1])
	  .replace(/ss/g, hms[2]);
	};
	 
	//数字前置补零
	layui.laytpl.digit = function(num, length, end){
	  var str = '';
	  num = String(num);
	  length = length || 2;
	  for(var i = num.length; i < length; i++){
	    str += '0';
	  }
	  return num < Math.pow(10, length) ? str + (num|0) : num;
	};
	
	
	String.prototype.endWith = function(s) {  
	    if (s == null || s == "" || this.length == 0 || s.length > this.length)  
	        return false;  
	    if (this.substring(this.length - s.length) == s)  
	        return true;  
	    else  
	        return false;  
	    //return true;  
	}
	layui.laytpl.FloatToString = function(d){
		if(d.endWith(".0")){
			return d.substring(0,d.indexOf(".0"));
		}else{
			return d
		}
	}
	layui.laytpl.myweek = function(){
		// 请求
	    $.ajax({
	        url: "/params/week",
	        data:{"yearMonth":$("#date_1").val()},
	        success:function(data){
	        	console.info(data.data)
	            $('#week').empty();
	            var t;
	            if(data==null){
	                t='<option value="---" selected="selected">未获取到数据</option>'
	            }else{
	            	$.each(data.data,function(item,value){
	            		t+='<option value="'+value.week+'">第'+value.week+'周</option>'
	            	})
	            }
	            $('#week').append(t);
	            form.render('select');
	        }
	    })
	}
});