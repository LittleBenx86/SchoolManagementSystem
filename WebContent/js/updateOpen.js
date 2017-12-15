/**
 * @author Unicorn
 * @date 2017-12-13
 * 更新学生信息
 */

/**
 * 自定义vue组件
 */
var uo = new Vue({
	el : '#updateOpen',
	data :{
		stuinfo : {}
	}
});

/**
 * 与addStudent.js的验证基本一致,但是不能直接使用addStudent.js的验证
 */
$(function(){
	$("input[id=stuNo]").blur(function(){
		checkStudentNoValid();
	});
	
	$("input[id=name]").blur(function(){
		checkNameValid();
	});
	
	$("input[id=age]").blur(function(){
		checkAgeValid();
	});
	
	$("input[id=province]").blur(function(){
		checkProvinceValid();
	});
	
	$("input[id=city]").blur(function(){
		checkCityValid();
	});
	
	$("input[id=area]").blur(function(){
		checkAreaValid();
	});
	
	$("input[id=phone]").blur(function(){
		checkPhoneValid();
	});
	
	$("input[id=email]").blur(function(){
		checkEmailValid();
	});
	
	$("input[id=birthday]").blur(function(){
		checkBirthdayValid();
	});
});

var stuNoFlag = false, nameFlag = false, ageFlag = false, pFlag = false, 
	cFlag = false, aFlag = false, phoneFlag = false, eFlag = false, bFlag = false;

function checkStudentNoValid(){
	var studentNo = $("input[name=stuNo]").val();
	var regx = /^([1-2])[0-9]{11}$/;
	if("" !== studentNo && null !== studentNo && undefined !== studentNo){
		if(!regx.test(studentNo)){
			layer.tips("学号格式错误!", $("input[id=stuNo]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			stuNoFlag = false;
			return;
		}
		stuNoFlag = true;
	} else {
		layer.tips("学号不能为空!", $("input[id=stuNo]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		stuNoFlag = false;
	}
}

function checkNameValid(){
	var name = $("input[name=name]").val();
	var regx = /(^[\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\.\·\。\•]{0,16}[\u4e00-\u9fa5]{1}$)|(^[a-zA-Z]{1}[a-zA-Z\s]{0,20}[a-zA-Z]{1}$)/;
	if("" !== name && null !== name && undefined !== name){
		if(!regx.test(name)){
			layer.tips("姓名输入非法!支持中文名或英文名!", $("input[id=name]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			nameFlag = false;
			return;
		}
		nameFlag = true;
	} else {
		layer.tips("姓名不能为空!", $("input[id=name]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
	}
}

function checkAgeValid(){
	var age = $("input[name=age]").val();
	var regx = /^[1-9][0-9]$/;
	if("" !== age && null !== age && undefined !== age){
		if(!regx.test(age)){
			layer.tips("只能输入数字!", $("input[id=age]"), {
				time: 1000,
				tips : [1, '#FF5722']
			})
			ageFlag = false;
			return;
		}
		ageFlag = true;
	} else {
		layer.tips("年龄不能为空!", $("input[id=age]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		ageFlag = false;
	}
}

function checkProvinceValid(){
	var province = $("input[name=province]").val();
	var regx = /^[\u4e00-\u9fa5]{2,3}$/;
	if("" !== province && null !== province && undefined !== province){
		if(!regx.test(province)){
			layer.tips("只能是中文且字数最大为3!", $("input[id=province]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			pFlag = false;
			return;
		}
		pFlag = true;
	} else {
		layer.tips("省份不能为空!", $("input[id=province]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		pFlag = false;
	}
}

function checkCityValid(){
	var city = $("input[name=city]").val();
	var regx = /^[\u4e00-\u9fa5]{1,6}$/;
	if("" !== city && null !== city && undefined !== city){
		if(!regx.test(city)){
			layer.tips("只能是中文且字数最大为6!", $("input[id=city]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			cFlag = false;
			return;
		}
		cFlag = true;
	} else {
		layer.tips("城市不能为空!", $("input[id=city]"), {
			time : 1000,
			tips: [1, '#FF5722']
		})
		cFlag = false;
	}
}

function checkAreaValid(){
	var a = $("input[name=area]").val();
	var regx = /^[\u4e00-\u9fa5]{1,6}$/;
	if("" !== a && null !== a && undefined !== a){
		if(!regx.test(a)){
			layer.tips("只能是中文且字数最大为6!", $("input[id=area]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			aFlag = false;
			return;
		}
		aFlag = true;
	} else {
		layer.tips("区/县不能为空!", $("input[id=area]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		aFlag = false;
	}
}

function checkPhoneValid(){
	var phone = $("input[name=phone]").val();
	var regx = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))[0-9]{8}$/;
	if("" !== phone && null !== phone && undefined !== phone){
		if(!regx.test(phone)){
			layer.tips("请输入11位的手机号码!支持移动,联通和电信的号码!", $("input[id=phone]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			phoneFlag = false;
			return;
		}
		phoneFlag = true;
	} else {
		layer.tips("手机号不能为空!", $("input[id=phone]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		phoneFlag = false;
	}
}

function checkEmailValid(){
	var email = $("input[name=email]").val();
	var regx = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/;
	if("" !== email && null !== email && undefined !== email){
		if(!regx.test(email)){
			layer.tips("邮箱格式错误!xxx@xxx.xxx", $("input[id=email]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			eFlag = false;
			return;
		}
		eFlag = true;
	} else {
		layer.tips("邮箱不能为空!", $("input[id=email]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		eFlag = false;
	}
}

function checkBirthdayValid(){
	var bd = $("input[name=birthday]").val();
	//匹配从1900-01-01(1900-1-1)到2099-12-31出生日期
	var regx = /^(19|20)[0-9]{2}[-]{1}(0[1-9]?|(1[0-2]))[-]{1}(0[1-9]?|(1|2)[0-9]?|3[0-1])$/;
	
	if("" !== bd && null !== bd && undefined !== bd){
		if(!regx.test(bd)){
			layer.tips("出生日期格式错误!1900-01-01(1900-1-1)", $("input[id=birthday]"), {
				time : 1000,
				tips : [1, '#FF5722']
			})
			bFlag = false;
			return;
		} else {
			var bSplit = bd.split('-');
			var year = parseInt(bSplit[0]);
			var month = parseInt(bSplit[1]);
			var day = parseInt(bSplit[2]);
			var tDay = monthDays(isLeapYear(year), month);
			
			if(parseInt(tDay) < day || day == 0){
				layer.tips("天数输入错误!", $("input[id=birthday]"), {
					time : 1000,
					tips : [1, '#FF5722']
				})
				bFlag = false;
				return;
			}
			bFlag = true;
		}
	} else {
		layer.tips("出生年月日不能为空!", $("input[id=birthday]"), {
			time : 1000,
			tips : [1, '#FF5722']
		})
		bFlag = false;
	}
}

function isLeapYear(year){
	if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
		return true;
	}
	return false;
}

function monthDays(leapYear, month){
	var days = new Array(31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	if(leapYear && 2 === month)
		return 29;
	else if(!leapYear && 2 === month)
		return 28;
	else
		return days[month - 1];
	
}

/**
 * ajax请求封装
 * @author unicorn
 * @param urlpath
 * @param dataVaild
 * @param beforeSend
 * @param successFunc
 * @param errorFunc
 * @returns
 */
function ajaxRequest(urlpath, dataVaild, beforeSend, successFunc, errorFunc){
	$.ajax({
		dataType : "JSON",
		contentType : "application/x-www-form-urlencoded;charset=utf-8;",
		type : "POST",
		url : urlpath,
		async : true,// 异步请求
		data: dataVaild,
		beforeSend : function(){
			beforeSend();
		},
		success : function(data, status) {
			successFunc(data, status);
		},
		error : function(data, status) {
			errorFunc(data, status);
		}
	});
}



