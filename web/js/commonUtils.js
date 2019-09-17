var SumbitUrl = "http://oa.redmany.com:50011/submitData.aspx?";
var userLogin="http://oa.redmany.com:50011/userRegister.aspx?";


//一般直接写在一个js文件中
layui.use(['layer', 'form'], function(){
    var layer = layui.layer
        ,form = layui.form;
    layer.msg('Hello World');
});

//text文本框的验证
function validate(id,validateStr,messageStr) {
    var thisValue = $("#"+id).val();
    if(validateStr.test(thisValue)){
        $("#"+id).next().remove();
        $("#"+id).after("<span style='color:red'>"+messageStr+"</span>");
        // alert("提示："+messageStr);
        // $("#"+id).focus();//失焦重复弹出问题
    }else{
        $("#"+id).next().remove();
    }
}

//text文本框是否唯一
function textOnlyOne(id,sql,mesg) {
    var thisValue = $("#"+id).val();
    if (thisValue != null || thisValue != '') {
        $.ajax({
            type: 'GET',
            url: 'register?viewType=1&sql='+ sql+'&value='+thisValue,
            success: function(data) {
                if("ok"==data){
                }else{
                    alert("提示：该["+mesg+"]已存在！")
                    $("#"+id).val('');
                    $("#"+id).focus();
                }
            } ,
            error : function(){}
        });
    }
}

