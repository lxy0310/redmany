layui.use('form', function(){
    var form = layui.form;

    form.verify({
        required:function (val,demo) {
            var reg = /[\S]+/;
            if (!reg.test(val)){
                var name = demo.name;
                switch (name) {
                    case 'CompanyId':return '企业ID不能为空！';break;
                    case 'username':return '用户名不能为空！';break;
                    case 'password':return '密码不能为空！';break;
                    case 'massage':return '验证码不能为空！';break;
                    default:return '必填项不能为空';
                }
            }
        }
    });


    //监听提交
    form.on('submit(formDemo)', function(data){
       /* layer.msg(JSON.stringify(data.field));
        alert(JSON.stringify(data.field));*/

        $.ajax({
            url:"backLogin",
            data:{"method":"login","setSubList":JSON.stringify(data.field)},
            type:"POST",
            success: function (result) {
              /*alert(result);*/
              if (result==6){
                  layer.msg("验证码错误！",{icon:6});
                  window.location.href="backLogin.jsp";
              }
              else if (result==5){
                  layer.msg("企业帐号不存在，请重新输入，谢谢！",{icon:6});
                  window.location.href="backLogin.jsp";
              }else if (result==1){
                  layer.msg("企业帐号已经过期，请联系客服中心处理，谢谢！",{icon:6});
                  window.location.href="backLogin.jsp";
              }else if (result==2){
                  layer.msg("手机客户端数量超过授权，请联系客服中心处理，谢谢！",{icon:6});
                  window.location.href="backLogin.jsp";
              }else if (result==3){
                  layer.msg("手机客户端数量超过授权，请联系客服中心处理，谢谢！",{icon:6});
                  window.location.href="backLogin.jsp";
              } else {
                  window.location.href="zTree.jsp";
              }
            },error:function(){
                layer.msg("服务异常,请及时联系工作人员！",{icon:5});
            }
        });
        return false;
    });
});
function reloadCode() {
    var time=new Date().getTime();
    document.getElementById("imageCode").src="<%= request.getContextPath()%>/servlet/ImageServlet?d="+time;

}