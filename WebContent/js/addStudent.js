/**
 * @author Unicorn 
 * @date 2017-12-11
 * @brief
 * 添加学生信息
 */
//管理员添加学生信息操作事件
/* 监听网页是否加载完毕 */
/* 获取浏览器页面的可见高度和宽度 */
var _pageHeight = document.documentElement.clientHeight,
	_pageWidth = document.documentElement.clientWidth;
/* 计算loading框距离顶部和左部的距离  */
var _loadingTop = _pageHeight > 61 ? (_pageHeight - 61) / 2 : 0,
	_loadingLeft = _pageWidth > 215 ? (_pageWidth - 215) / 2 : 0; 	
/* 在页面还在完成之前的显示效果 */
var _loadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + 
			_pageHeight + 'px;top:0;background:#f3f8ff;opacity:1;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: ' + 
			_loadingLeft + 'px; top:' + _loadingTop + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px; background: #fff url(../img/loading.gif) no-repeat scroll 5px 10px; border: 2px solid #95B8E7; color: #696969; font-family:\'Microsoft YaHei\';">页面加载中，请等待...</div></div>';
document.write(_loadingHtml);
/* 监听加载状态改变 */
document.onreadystatechange = completeLoading;
	
function completeLoading(){
	if("complete" == document.readyState){
		 var loadingMask = document.getElementById('loadingDiv');
	     loadingMask.parentNode.removeChild(loadingMask);
	     layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>loading completed!');
	}
}

//全局变量
var index;//用于layer弹出层的关闭

$(function(){
	$("input[id=stuNo]").blur(function(){//学号填入验证
		checkStudentNoValid();
	});
	
	$("input[id=name]").blur(function(){//姓名填入验证
		checkNameValid();
	});
	
	$("input[id=age]").blur(function(){//年龄填入验证
		checkAgeValid();
	});
	
	$("input[id=province]").blur(function(){//省份填入验证
		checkProvinceValid();
	});
	
	$("input[id=city]").blur(function(){//城市填入验证
		checkCityValid();
	});
	
	$("input[id=area]").blur(function(){//区/县填入验证
		checkAreaValid();
	});
	
	$("input[id=phone]").blur(function(){//手机号码填入验证
		checkPhoneValid();
	});
	
	$("input[id=email]").blur(function(){//邮箱填入验证
		checkEmailValid();
	});
	
	$("input[id=birthday]").blur(function(){//出生日期填入验证
		checkBirthdayValid();
	});
	
	$("button[id=confirm]").click(function(){//确认添加按钮触发提交事件
		if(stuNoFlag && nameFlag && ageFlag && pFlag && 
				cFlag && aFlag && phoneFlag && eFlag && bFlag){
			var json = {//数据json化
				"name" : $("input[name=name]").val(),
				"age" : $("input[name=age]").val(),
				"sex" : $("input[name=sex]:checked").val(),
				"province" : $("input[name=province]").val(),
				"city" : $("input[name=city]").val(),
				"area" : $("input[name=area]").val(),
				"studentNo" : $("input[name=stuNo]").val(),
				"phone" : $("input[name=phone]").val(),
				"email" : $("input[name=email]").val(),
				"birthday" : $("input[name=birthday]").val()
			}
			ajaxRequest("/SchoolManagementSystem/admin/add", json,
				function(){//before ajax request
					index = layer.load(1);
				},
				function(data, status){//after ajax request success
					if("yes" === data.isAdd){//添加成功
						layer.msg("学生" + $("input[name=name]").val() + "的信息录入成功!");
						stuNoFlag = nameFlag = ageFlag = pFlag = 
						cFlag = aFlag = phoneFlag = eFlag = bFlag = false;
						//添加成功,清空输入框
						$("input[id!=sex]").val("");
						$("input[id=sex][value='0']").attr("checked", "checked");
						layer.close(index);
					} else if("no" === data.isAdd){//添加失败
						layer.msg("学生" + $("input[name=name]").val() + "的信息录入失败!");
					} else {
						layer.msg("学生" + $("input[name=name]").val() + "的信息录已存在!不需要再录入!");
					}
				},
				function(data, status){//after ajax request failed
					layer.msg("服务器内部错误!学生" + $("input[name=name]").val() + "的信息录入失败!");
				})
		} else {//信息填写不完整,弹出信息
			layer.msg("信息填写未完整!");
		}
	});
	
	$("button[id=quit]").click(function(){//取消按钮触发事件
		stuNoFlag = nameFlag = ageFlag = pFlag = 
		cFlag = aFlag = phoneFlag = eFlag = bFlag = false;
		//清空输入
		$("input[id!=sex]").val("");
		layer.msg("已清除所有输入");
	});
	
});

//输入信息的标志位
var stuNoFlag = false, nameFlag = false, ageFlag = false, pFlag = false, 
	cFlag = false, aFlag = false, phoneFlag = false, eFlag = false, bFlag = false;

/**
 * 学号验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 姓名验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 年龄验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 省份验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 城市验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 区/县验证
 * @returns
 * @author Unicorn
 */
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

/**
 * 手机号验证
 * @returns
 * @author Unicorn
 */
function checkPhoneValid(){
	var phone = $("input[name=phone]").val();
	//手机号正则表达式
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

/**
 * 邮箱验证
 * @returns
 * @author Unicorn
 */
function checkEmailValid(){
	var email = $("input[name=email]").val();
	//邮箱正则验证
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

/**
 * 出生日期验证
 * @returns
 * @author Unicorn
 */
function checkBirthdayValid(){
	var bd = $("input[name=birthday]").val();
	//匹配从1900-01-01(1900-1-1)到2099-12-31出生日期正则表达式
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

/**
 * 闰年判断
 * @param year
 * @returns
 * @author Unicorn
 */
function isLeapYear(year){
	if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
		return true;
	}
	return false;
}

/**
 * 获取月份的天数
 * @param leapYear
 * @param month
 * @returns
 * @author Unicorn
 */
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
 * @author Unicorn
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


