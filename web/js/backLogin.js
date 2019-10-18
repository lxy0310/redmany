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
        var loading= window.parent.layer.load(1, {
            shade: [0.1,'black'] //0.1透明度的
        });
        var UserName=$(".username").val();
        var password=$(".password").val();
        var CompanyId = $(".CompanyId").val();
        var message = $(".massage").val();
        $.ajax({
            url:"login",
            data:{"Company_Id":CompanyId,"userName":UserName,"password":password,"message":message,"pcPort":"1"},
            type:"POST",
            success: function (result) {
                window.parent.layer.close(loading);
                if (result.indexOf("success")!=-1){
                    layer.msg("登陆成功！",{icon:1});
                    //window.close();
                    location.href="http://"+window.location.host+"/redmany/backMar?method=menuList";

                } else{
                    layer.msg(result,{icon:6});
                    window.location.href="backLogin.jsp";
                }

            },error:function(){
                window.parent.layer.close(loading);
                layer.msg("服务异常,请及时联系工作人员！",{icon:5});
            }
        });

    });



});
/*$("#login").click(function () {
    alert("asdf");
    var UserName=$(".username").val();
    var password=$(".password").val();
    var CompanyId = $(".CompanyId").val();
    var message = $(".massage").val();
    $.ajax({
        url:"login",
        data:{"Company_Id":CompanyId,"userName":UserName,"password":password,"message":message,"pcPort":"1"},
        type:"POST",
        success: function (result) {
            alert(result);
            if (result=="success"){
                layer.msg("登陆成功！",{icon:5});
                alert("登陆成功！");
                var s = "<%= request.getContextPath()%>/backMar?method=menuList";
                alert(s);
                window.location.href="<%= request.getContextPath()%>/backMar?method=menuList";
            } else{
                layer.msg(result,{icon:6});
                window.location.href="backLogin.jsp";
            }
        },error:function(){
            layer.msg("服务异常,请及时联系工作人员！",{icon:5});
        }
    });

});*/

/* if (result==6){
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
                 window.location.href="backMar?method=menuList";
           }*/

function changeCode(){

    var date = new Date().getTime();
    var path =  $('#imagecode').attr('src');
    var src = path+"?" + date;

    $('.verifyCode').attr("src",src);                  //jQuery写法

}
