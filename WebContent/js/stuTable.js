/**
 * @author Unicorn
 * @date 2017-12-12
 */
//学生信息列表Vue.js和layer.js渲染
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

//分页bar自定义组件模版html
var tm = '<div class="page-bar">'+
        '<ul>'+
        '<li v-if="cur!=1"><a @click="btnClick(cur-1)">上一页</a></li>'+
        '<li v-for="index in indexs" v-bind:class="{ active: cur == index}">'+
          '<a @click="btnClick(index)">{{ index }}</a>'+
          '</li>'+
          '<li v-if="cur!=total"><a @click="btnClick(cur+1)">下一页</a></li>'+
          '<li><a>共<i>{{total}}</i>页</a></li>'+
        '</ul>'+
      '</div>'

/**
 * vue分页组件自定义
 */
var navBar = Vue.extend({
    template: tm,
    props: {
      cur: {
        type: [String, Number],
        required: true
      },
      total: {
        type: [String, Number],
        required: true
      },
      url : {
    	  type: [String, String],
    	  required: true
      },
      tar : {
    	  type: [String, String],
    	  required: true
      },
      callback: {
        default() {
          return function callback() {
            // todo
          }
        }
      }
    },
    computed: {//分页下标计算,vue内的实时计算,每次显示的页数只有10页,除非总页数达不到10页
      indexs() {
        var left = 1
        var right = this.total
        var ar = [] 
        if (this.total >= 11) {
          if (this.cur > 5 && this.cur < this.total - 4) {
            left = this.cur - 5
            right = this.cur + 4
          } else {
            if (this.cur <= 5) {
              left = 1
              right = 10
            } else {
              right = this.total
              left = this.total -9
            }
          }
        }
        while (left <= right) {
          ar.push(left)
          left ++
        }   
        return ar
      }
    },
    methods: {//页码点击触发回调函数
      btnClick(page) {
        if (page != this.cur) {
          this.callback(page)
        }
      }
    }
})

var subfix = "";//请求后缀
var tmpfix = "";//暂存后缀
var i;

/**
 * 学生信息表格,vue渲染
 */
var stuTablesVue = new Vue({
	el : "#app",
	data : {
		//查询下拉选择框相关vue事件驱动
		opts : [
			{text : "========请选择=======", value : null},
			{text : "按年龄±5查询", value : 11},
			{text : "按姓氏/名字模糊查询", value : 12},
			{text : "按学号模糊/精确查询", value : 13},
			{text : "按出生年份查询", value : 14},
			{text : "按性别查询", value : 15},
			{text : "按省份/城市/区(县)查询", value : 16},
			{text : "按手机号模糊/精确查询", value : 17},
			{text : "按邮箱地址模糊/精确查询", value : 18}
		],
		selected : null,
		//排序下拉选择框相关vue事件驱动
		sorts : [
			{text : "=======请选择=======", value : null},
			{text : "按年龄升序排序", value : 101},
			{text : "按年龄降序排序", value : 102},
			{text : "按学号升序排序", value : 103},
			{text : "按学号降序排序", value : 104},
			{text : "按手机号升序排序", value : 105},
			{text : "按手机号降序排序", value : 106}
		],
		selectedSort : null,
		selectedSex : 1,
		sexs : [
			{text : "男", value : 0},
			{text : "女", value : 1}
		],
		//输入框隐藏和显示相关,v-if
		query : false,
		pca : false,
		seeSex : false,
		//输入框数据绑定,
		condition : null,
		province : null,
		city : null,
		area : null,
		//学生信息渲染所需的数据
		stuDatas: [],
		//分页所需
		cur : 0,
		total : 0
	},
	components : {
		'vue-nav' : navBar
	},
	methods : {
		updateInfo : function(item){//更改学生信息
			var back = $.extend({}, item);//阻止同步修改
			uo.$data.stuinfo = back;
			layer.open({
				type : 1,
				title : "修改学生信息",
				area : ["450px", "750px"],
				content : $("#updateOpen"),
				btn : ["保存修改"],
				yes : function(index){
					ajaxRequest("/SchoolManagementSystem/admin/update", uo.$data.stuinfo,
							function(){
								i = layer.load(1);
							},
							function(data, status){
								if("y" === data.isUpdate){
									layer.close(i);
									layer.msg("学生" + $("input[name=name]").val() + "的信息修改成功!");
									
									stuTablesVue.$data.stuDatas.splice(uo.$data.stuinfo.id - 1, 1, uo.$data.stuinfo);
									layer.close(index);
								} else if("n" === data.isUpdate){
									layer.msg("学生" + $("input[name=name]").val() + "的信息修改失败!");
								}
							},
							function(data, status){
								layer.msg("服务器内部错误!学生" + $("input[name=name]").val() + "的信息修改失败!");
							})
				},
				cancel : function(){
					
				}
			});
		},
		deleteInfo : function(_id, tIndex){//删除学生信息
			let self = this;
			layer.confirm('确认删除该学生的信息?', {icon: 3, title:'提示'}, function(index){
				  //do something
				ajaxRequest("/SchoolManagementSystem/admin/delete?id=" + _id, {},
						function(){
							i = layer.load(1);
						},
						function(data, status){
							if("y" === data.isDelete){
								layer.msg("该学生的信息删除成功!");
								self.stuDatas.splice(tIndex - 1, 1); 
								layer.close(i);
								layer.close(index);
							} else if("n" === data.isDelete){
								layer.msg("该学生的信息删除失败!");
							}
						},
						function(data, status){
							layer.msg("服务器内部错误!学生" + $("input[name=name]").val() + "的信息删除失败!");
							layer.close(i);
						})
			});
		},
		showConditionInput: function(val){//onchange事件改变,导出条件查询的输入框/选择框
			switch(val){
			case 15:{
				this.seeSex = true;
				this.query = this.pca = false;
			}break;
			case 16:{
				this.pca = true;
				this.query = this.seeSex = false;
			}break;
			case 11: case 12: case 13:
			case 14: case 17: case 18:{
				this.query = true;
				this.seeSex = this.pca = false;
			}break;
			default:{
				this.query = this.seeSex = this.pca = false;
			}break;
			}
		},
		conditionQuery : function(){//条件查询
			let self = this;
			var url = "";
			switch(self.selected){
				case 11:{//age
					url = messUrlHandle(self.selectedSort, self.condition, 11, 'age', 0);
				}break;
				case 12:{//name
					url = messUrlHandle(self.selectedSort, self.condition, 12, 'name', 0);
				}break;
				case 13:{//stuNo
					url = messUrlHandle(self.selectedSort, self.condition, 13, 'studentNo', 0);
				}break;
				case 14:{//year
					url = messUrlHandle(self.selectedSort, self.condition, 14, 'birthday', 0);
				}break;
				case 15:{//sex
					url = messUrlHandle(self.selectedSort, self.selectedSex, 15, 'sex', 0);
				}break;
				case 16:{//pca
					url = pcaUrlHandle(self.selectedSort, self.province, self.city, self.area, 16, 'province', 'city', 'area', 0);
				}break;
				case 17:{//phone
					url = messUrlHandle(self.selectedSort, self.condition, 17, 'phone', 0);
				}break;
				case 18:{//email
					url = messUrlHandle(self.selectedSort, self.condition, 18, 'email', 0);
				}break;
				default:{
					layer.msg('您还没有选择查询条件!此次查询操作无效!');
					return;
				}
			}
			
			ajaxRequest(url, {}, 
					function(){
						i = layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>requesting datas!');
					},
					function(data){
						subfix = tmpfix;
						tmpfix = "";
						self.cur = 1;
						self.stuDatas = data.sDatas;
						self.total = data.totalPages;
						layer.close(i);
					},
					function(data){
						layer.msg("发生错误!数据请求失败!");
					}
				);
		},
		commonQuery : function(){//查询所有的学生信息
			let self = this;
			var url = commonUrlHandle(self.selectedSort, 1);
			ajaxRequest(url, {}, 
					function(){
						i = layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>requesting datas!');
					},
					function(data){
						subfix = tmpfix;
						tmpfix = "";
						self.cur = 1;
						self.stuDatas = data.sDatas;
						self.total = data.totalPages;
						layer.close(i);
					},
					function(data){
						layer.msg("发生错误!数据请求失败!");
					}
				);
		},
		convertToExcel : function(){//导出为excel
			let self = this;
			var url = "";
			switch(self.selected){
				case 11:{//age
					url = messUrlHandle(self.selectedSort, self.condition, 11, 'age', 2);
				}break;
				case 12:{//name
					url = messUrlHandle(self.selectedSort, self.condition, 12, 'name', 2);
				}break;
				case 13:{//stuNo
					url = messUrlHandle(self.selectedSort, self.condition, 13, 'studentNo', 2);
				}break;
				case 14:{//year
					url = messUrlHandle(self.selectedSort, self.condition, 14, 'birthday', 2);
				}break;
				case 15:{//sex
					url = messUrlHandle(self.selectedSort, self.selectedSex, 15, 'sex', 2);
				}break;
				case 16:{//pca
					url = pcaUrlHandle(self.selectedSort, self.province, self.city, self.area, 16, 'province', 'city', 'area', 2);
				}break;
				case 17:{//phone
					url = messUrlHandle(self.selectedSort, self.condition, 17, 'phone', 2);
				}break;
				case 18:{//email
					url = messUrlHandle(self.selectedSort, self.condition, 18, 'email', 2);
				}break;
				default:{//common
					url = commonUrlHandle(self.selectedSort, 2);
					break;
				}
			}
			
			ajaxRequest(url, {}, 
					function(){
						i = layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>converting excel!');
					},
					function(data){
						subfix = tmpfix;
						tmpfix = "";
						self.cur = 1;
						self.stuDatas = data.sDatas;
						self.total = data.totalPages;
						layer.close(i);
						layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe616;</i><br/>输出完成!')
					},
					function(data){
						layer.msg("发生错误!输出excel文件失败!");
					}
				);
		},
		callback : function(data){//页码的回调,vue自定义组件在主体里的绑定
			this.cur = data
			let self = this;
			var tmp = subfix;
			ajaxRequest('/SchoolManagementSystem/admin/reqStuDatas' + subfixCat(tmp, data), {}, 
					function(){
						i = layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>requesting datas!');
					},
					function(data){
						subfix = subfixCat(tmp, self.cur);
						self.stuDatas = data.sDatas;
						self.total = data.totalPages;
						layer.close(i);
					},
					function(data){
						layer.msg("数据请求失败!");
					}
				);
		}
	}
});

/**
 * 数据监控,数据发生改变时的触发操作
 * 没有实现好
 */
//stuTablesVue.$watch(stuTablesVue.stuDatas, function(){
//	stuTablesVue.$nextTick(function(){
//		layer.msg('<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe6c6;</i><br/>datas loading completed!');
//	})
//})

/**
 * 查询所有数据的请求链接修饰处理
 * @param selectedSort
 * @param btnType
 * @returns
 */
function commonUrlHandle(selectedSort, btnType){
	if(selectedSort !== null && !isNaN(selectedSort)){
		tmpfix = "?pageNo=1&qc=19&btnType="+ btnType +"&st=" + selectedSort;
		return  "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=19&btnType="+ btnType +"&st=" + selectedSort;
	} else if(selectedSort === null){
		tmpfix = "?pageNo=1&qc=19&btnType="+ btnType;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=19&btnType="+ btnType +"";
	}
}

/**
 * 条件查询的请求链接修饰处理
 * @param selectedSort 排序方式
 * @param condition 查询输入值
 * @param qc 查询方式
 * @param ckey json数据对应的输入的key
 * @param btnType 按钮类型
 * @returns
 */
function messUrlHandle(selectedSort, condition, qc, ckey, btnType){
	if(selectedSort !== null && !isNaN(selectedSort) && 
			null !== condition && '' !== condition && undefined !== condition){//排序和输入条件都为空的时候
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort + "&" + ckey + "=" + condition;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort + "&" + ckey + "=" + condition;
	} else if(selectedSort !== null){//没有输入条件
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort;
	} else if(null !== condition && '' !== condition && undefined !== condition){//没有排序
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&" + ckey + "=" + condition;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&" + ckey + "=" + condition;
	} else {//两者都为空的时候
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType;
	}
}

/**
 * 省/市/区(县)查询请求修饰处理
 * @param selectedSort 排序方式
 * @param pval 省的查询输入值
 * @param cval 市的查询输入值
 * @param aval 区/县查询的输入值
 * @param qc	查询方式选择
 * @param pkey json数据省的key
 * @param ckey json数据市的key
 * @param akey json数据区/县的key
 * @param btnType 按钮类型
 * @returns
 */
function pcaUrlHandle(selectedSort, pval, cval, aval, qc, pkey, ckey, akey, btnType){
	var tfix = "";
	if(null !== pval && '' !== pval && undefined !== pval)
		tfix = tfix + "&" + pkey + "=" + pval;
	if(null !== cval && '' !== cval && undefined !== cval)
		tfix = tfix + "&" + ckey + "=" + cval;
	if(null !== aval && '' !== aval && undefined !== aval)
		tfix = tfix + "&" + akey + "=" + aval;
	
	if(selectedSort !== null && !isNaN(selectedSort) && 
			null !== tfix && '' !== tfix && undefined !== tfix){//排序和输入条件都为空的时候
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort + tfix;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort + tfix;
	} else if(selectedSort !== null){//没有输入条件
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"&st=" + selectedSort;
	} else if(null !== tfix && '' !== tfix && undefined !== tfix){//没有排序
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"" + tfix;
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"" + tfix;
	} else {//两者都为空的时候
		tmpfix = "?pageNo=1&qc=" + qc + "&btnType="+ btnType +"";
		return "/SchoolManagementSystem/admin/reqStuDatas?pageNo=1&qc=" + qc + "&btnType="+ btnType +"";
	}
}

/**
 * 后缀拼接函数
 * @param page 页码
 * @param sub 后缀请求参数
 * @returns
 */
function subfixCat(tmpUrl, page){
	var pre = tmpUrl.substring(0, tmpUrl.indexOf("=") + 1);
	var sub = tmpUrl.substring(tmpUrl.indexOf("&"), tmpUrl.length);
	tmpUrl = pre + page + sub;
	return tmpUrl;
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
			beforeSend()
		},
		success : function(data, status) {
			successFunc(data, status);
		},
		error : function(data, status) {
			errorFunc(data, status);
		}
	});
}