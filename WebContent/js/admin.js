/**
 * @author Unicorn
 * @date 2017-12-13
 * 管理员登录/退出,传入管理员姓名,这里略略没有完善好
 */
var testName = "";
/**
 * vue组件,主要用于获取管理员的姓名/用于退出系统
 */
var Administrator = new Vue({
	el : '#Admin',
	data : {
		name : "admin"
	},
	methods : {
		logout : function(){
			layer.confirm('确认退出?', {icon: 3, title:'提示'}, function(index){
				  //do something
				ajaxRequest("/SchoolManagementSystem/admin/logout", {},
						function(){
							i = layer.load(1);
						},
						function(data, status){
							if("y" === data.logout){
								layer.msg("再见!");
								layer.close(i);
								layer.close(index);
								window.location.replace("/SchoolManagementSystem/admin/login.htm");
							} else {
								layer.msg("系统退出失败!");
							}
						},
						function(data, status){
							layer.msg("服务器内部错误!系统退出失败!");
							layer.close(i);
						})
			});
		}
	}
});

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
