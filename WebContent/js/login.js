/**
 * @author Unicorn
 * @date 2017-12-11
 */

//管理员登录相关事件响应
$(function(){
	$("button[id=confirm]").click(function(){
		var pwd = checkPasswordValid();
		var name = checkNameValid();
		
		if(nameFlag === 1 && pwdFlag === 1 && codeFlag === 1){
						
			var json = {
				"code" : $("input[name=code]").val()
			}
			//验证码 valid
			ajaxRequest("/SchoolManagementSystem/validCode", //共用admin.js的ajax请求封装函数
					json, 
					function(){},
					function(data, status){
						if("no" === data.isCorrect){
							layer.tips('验证码错误!', $("input[id=code]"), {
								time : 1500,
								tips: [1, '#FF5722']
							})
							//更新验证码
							$("img[id=valid-code-img]").attr("src","/SchoolManagementSystem/securitycode?time="+ new Date().getTime());
						} else if("yes" === data.isCorrect){
							var str = {
									"adminName" : name,
									"password" : pwd
								}
							ajaxRequest("/SchoolManagementSystem/adminLoginRequest", 
									str, 
									function(){},
									function(data, status){
										if("yes" === data.success){
											window.location.href = "/SchoolManagementSystem/admin/addStu.htm";
											testName = name;
										} else {
											layer.msg("账户或密码错误!");
										}
									}, 
									function(data, status){
										layer.msg("服务器内部错误!");
							})
						}
					}, 
					function(data, status){
						layer.msg("服务器内部错误!");
			});
			
		} else {
			if(!checkValidCodeValid()){
				layer.msg("请完整填写登录信息!");
				codeFlag = 0;
			}
		}
	});
	
	$("img[id=valid-code-img]").click(function(){//点击更新验证码
		this.src = "/SchoolManagementSystem/securitycode?time="+ new Date().getTime();
	});
	
	$("input[id=pwd]").blur(function(){//密码校验
		checkPasswordValid();
	});
	
	$("input[id=name]").blur(function(){//用户名校验
		checkNameValid();
	});
	
	$("input[id=code]").blur(function(){//验证码校验
		checkValidCodeValid();
	});
	
});

//校验的标志位
var nameFlag = 0, pwdFlag = 0, codeFlag = 0;

/**
 * 密码校验
 * @returns
 * @author Unicorn
 */
function checkPasswordValid(){
	var pwd = $("input[name=pwd]").val();
	
	if("" === pwd || null === pwd || undefined === pwd){
		layer.tips('密码不能为空!', $("input[id=pwd]"), {
			time : 3000,
			tips: [1, '#FF5722']
		});
		pwdFlag = 0;
		return;
	}
	pwdFlag = 1;
	return pwd;
}

/**
 * 用户名校验
 * @returns
 * @author Unicorn
 */
function checkNameValid(){
	var name = $("input[name=name]").val();
	
	if("" === name || null === name || undefined === name){
		layer.tips('账户名不能为空!', $("input[id=name]"), {
			time : 3000,
			tips : [1, '#FF5722']
		})
		nameFlag = 0;
		return;
	}
	nameFlag = 1;
	return name;
}

/**
 * 验证码校验
 * @returns
 * @author Unicorn
 */
function checkValidCodeValid(){
	var code = $("input[name=code]").val();
	
	if("" === code || null === code || undefined === code){
		layer.tips('验证码不能为空!', $("input[id=code]"), {
			time : 3000,
			tips: [1, '#FF5722']
		})
		codeFlag = 0;
		return false;
	} else {
		codeFlag = 1;
		
		return true;
	}
}

