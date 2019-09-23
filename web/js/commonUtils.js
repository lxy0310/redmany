var SumbitUrl = "http://oa.redmany.com:50011/submitData.aspx?";
var userLogin="http://oa.redmany.com:50011/userRegister.aspx?";

var layer,$,form,upload;
//一般直接写在一个js文件中

layui.use(['layer','element','form','upload'],function(){
    layer=layui.layer,
        element = layui.element,
        //$=layui.jquery;
        upload = layui.upload,
        // laypage = layui.laypage;
        form=layui.form;



    $(".saveBtn").click(function() {
        // layer.msg('hello');
        // var addForms=$("#addForm").serialize();
        //addForms = decodeURIComponent(add,true);
        //alert(addForms);
        var dataList=$(".saveData").val();
        var dataJson=eval('('+dataList +')');

        var d = {};
        //循环获取input的值
        var t=$('form').serializeArray();
        $.each(t, function() {
            d[this.name] = this.value;
        });
        alert(JSON.stringify(d));
        //获取参数
        var paramId = $(".paramId").val();
        //alert(paramId);
        var FormName = $(".formName").val();
        alert(FormName);
        $.ajax({
            url:"common",
            data:{"method":"addForm","addForm":JSON.stringify(d),"FormName":FormName,"paramId":paramId},
            //async:false,
            success:function(data){
                if (data>0){
                    layer.msg("操作成功！",{icon:6});
                    window.parent.location.reload();
                }else {
                    layer.msg("操作失败！",{icon:5});
                    location.reload();
                }
            },
            error:function(data){
                layer.msg('服务器异常！',{icon:5});
            }
        });
    });

    $('.site-demo-active').on('click', function(){
        var othis = $(this), type = othis.data('type');
        active[type] ? active[type].call(this, othis) : '';
    });



    //触发事件 选项卡切换
    var active = {
        tabAdd: function(){
            //新增一个Tab项
            element.tabAdd('test1', {
                title: '新选项'+ (Math.random()*1000|0) //用于演示
                ,content: '内容'+ (Math.random()*1000|0)
                ,id: 1 //实际使用一般是规定好的id，这里以时间戳模拟下
            })
        }
        // ,tabDelete: function(othis){
        //     //删除指定Tab项
        //     element.tabDelete('demo', '44'); //删除：“商品管理”
        //     othis.addClass('layui-btn-disabled');
        // }
        ,tabChange: function(){
            //切换到指定Tab项
            element.tabChange('test1', '2'); //切换到：用户管理
        }
    };

    //Hash地址的定位
    var layid = location.hash.replace(/^#test1=/, '');
    element.tabChange('test1', layid);
    element.on('tab(test1)', function(elem){
        location.hash = 'test1='+ $(this).attr('lay-id');
    });

    //全选
    // form.on('checkbox(allChoose)', function(data){
    //     var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
    //     child.each(function(index, item){
    //         item.checked = data.elem.checked;
    //     });
    //     form.render('checkbox');

    //layer.msg('Hello World');
});

//全选
function my(){
    var is=document.getElementById('box');//获取全选的复选框
    var em=is.checked;//判断复选框的选中状态
    var iss=document.getElementsByName('box1');//下面所有的复选框
    var ass=document.getElementById('as');
    if(em){//如果全选复选框是false 则执行下面代码
        for(var i=0;i<iss.length;i++){ //循环下面所有复选框,将所有复选框都选中
            iss[i].checked=true;
        }
        ass.innerHTML='取消';//选中后将SPAN中的内容改为取消全选
    }else{
        for(var i=0;i<iss.length;i++){
            iss[i].checked=false;
        }
        ass.innerHTML='全选';
    }
}

//批量删除
function del(FormName) {
    alert(FormName)
    var s='';
    $('input[name="box1"]:checked').each(function(){
        s+=$(this).val()+','; //遍历得到所有checkbox的value
    });
    if (s.length > 0) {
        //删除多出来的“，”
        s = s.substring(0,s.length - 1);
    }
    var flag = window.confirm("确认删除吗？");
    if (!flag){
        return;
    }
    $.ajax({
        url:"common",
        data:{"method":"delBatch","gCompany_Id":gCompany_Id,"hidFormName":FormName,"id":s},
        type:"POST",
        success:function(data){
            if (data>0){
                layer.msg("删除成功！",{icon:6});
                window.parent.location.reload();
            } else{
                layer.msg("删除失败！",{icon:5});
            }
        },error:function(){
            layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
        }
    });
    //生成链接
    // s="deletel?id="+s+"";
    //把链接添加到删除的超链接中中
   //s $("#deletel").attr('href',s);
   /* layer.confirm('您确定要删除吗？', {
        btn: ['忍心删除','在想想'] //按钮
    }, function(){
        alert(s);
        s="common?method='delBatch'&hidFormName="+hidFormName+"&id="+s+"";
        alert(s)
        $("#elDelete").attr('href',s);

        $.ajax({
            url:"common",
            data:{"method":"delBatch","gCompany_Id":gCompany_Id,"hidFormName":hidFormName,"id":s},
            type:"POST",
            success:function(data){
                if (data>0){
                    layer.msg("删除成功！",{icon:6});
                    window.parent.location.reload();
                } else{
                    layer.msg("删除失败！",{icon:6});
                }
            },error:function(){
                layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
            }
        });
    });*/
}

//批量操作
function batchList(FormName) {
    //1 获取勾选的Id
    var s='';
    $('input[name="box1"]:checked').each(function(){
        s+=$(this).val()+','; //遍历得到所有checkbox的value
    });
    if (s.length > 0) {
        //删除多出来的“，”
        s = s.substring(0,s.length - 1);
    }
    alert(FormName.toString());
    // 2
    $.ajax({
        url:"common",
        data:{"method":"batchList","gCompany_Id":gCompany_Id,"hidFormName":FormName,"id":s},
        type:"POST",
        success:function(data){
            if (data>0){
                layer.msg("删除成功！",{icon:6});
                window.parent.location.reload();
            } else{
                layer.msg("删除失败！",{icon:6});
            }
        },error:function(){
            layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
        }
    });

}

/**
 *  状态修改  formstateid,OperationNameid
 */
function updateListBtn(listId,FormName,AfterProcessState){
    /*alert(listId);
    alert(FormName);
    alert(AfterProcessState);*/
    $.ajax({
        url:"common",
        data:{"method":"updateListBtn","listId":listId,"FormName":FormName,"AfterProcessState":AfterProcessState},
        type:"POST",
        success:function(data){
            if (data>0){
                layer.msg("操作成功！",{icon:6});
                window.parent.location.reload();
            }else {
                layer.msg("操作失败！",{icon:5});
            }
        },error:function(){
            layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
        }
    });
}

//删除
function delListForm(id,hidFormName) {
    layer.confirm('您确定要删除吗？', {
        btn: ['忍心删除','在想想'] //按钮
    }, function(){
        $.ajax({
            url:"common",
            data:{"method":"delListForm","gCompany_Id":gCompany_Id,"hidFormName":hidFormName,"id":id},
            type:"POST",
            success:function(data){
                if (data>0){
                    layer.msg("删除成功！",{icon:6});
                    window.parent.location.reload();
                } else{
                    layer.msg("删除失败！",{icon:6});
                }
            },error:function(){
                layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
            }
        });
    });
}

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

