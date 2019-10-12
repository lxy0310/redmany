var SumbitUrl = "http://oa.redmany.com:50011/submitData.aspx?";
var userLogin="http://oa.redmany.com:50011/userRegister.aspx?";


var layer,$,form,upload,laydate;
//一般直接写在一个js文件中

layui.use(['layer','element','form','upload','laydate'],function(){
    layer=layui.layer,
        element = layui.element
        upload = layui.upload,
        form=layui.form;
    laydate = layui.laydate;

    $("#reset").on("click",function(){ window.location.reload();}); //重置

    $(".saveBtn").click(function() {
        // var dataList=$(".saveData").val();
        // var dataJson=eval('('+dataList +')');

        var d = {};
        //循环获取input的值
        var t=$('form').serializeArray();
        console.log(t);
        $.each(t, function() {
            d[this.name] = this.value;
        });
        console.log("json:"+JSON.stringify(d));
        //获取参数
        var paramId = $(".paramId").val();
        //alert(paramId);
        var FormName = $(".formName").val();
        console.log(FormName);
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

    // $('.site-demo-active').on('click', function(){
    //     var othis = $(this), type = othis.data('type');
    //     active[type] ? active[type].call(this, othis) : '';
    // });

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


});

function useLayDateMultiple(cls) {
    layui.use('laydate', function() {
        var laydate = layui.laydate;
        lay('#' + cls).each(function() {
            laydate.render({
                elem : this,
                trigger : 'click',
                type: 'datetime',
            });
        });
    });
}

//搜索
function search(formName,showType) {
    layer.open({
        skin: 'layui-layer-molv', //样式类名
        title:'搜索',
        type: 2,
        area: ['700px', '450px'],
        content: 'queryStudentServlet?copformName='+formName+'&showType=SearchForm',
        btn:['查询','取消'],
        yes: function(index, layero){
            var form = $("iframe").contents().find("#searchForm").serialize();
            console.log(form);
        },success: function(layero,index){ //成功回调

        },cancel: function(index, layero){
            //按钮【按钮二】的回调

            //return false 开启该代码可禁止点击该按钮关闭
        }

    });
}

//点击td跳转到修改页面
function tableUpdate(formname,Id) {
    location.href = "queryStudentServlet?copformName="+formname+"&showType=newForm&optype=2&ParamId="+Id;
}

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
            url: 'view?viewType=1&sql='+ sql+'&value='+thisValue,
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

//联动下拉框
function linkageSelectChange(fid,cid){
    var first = document.getElementById(fid);
    var second = document.getElementById(cid);
    second.options.length = 0; // 清除second下拉框的所有内容
    second.options.add(new Option("==请选择==",""));
    if($('#'+cid).next().val()){
        var secondCid = cid+"_hidden";
        var third = $("#"+secondCid).val()+"0";
        document.getElementById(third).options.length = 0;
        document.getElementById(third).options.add(new Option("==请选择==",""));
        if($('#'+third).next().val()){
            var thirdCid = third+"_hidden";
            var four = $("#"+thirdCid).val()+"0";
            document.getElementById(four).options.length = 0;
            document.getElementById(four).options.add(new Option("==请选择==",""));
        }
    }
    if(first.value != null){
        cid = cid.substr(0,cid.length-1);
        var result;
        $.ajax({
            type:"POST",
            dataType:"json",
            url: 'view',
            data:{viewType:2, value:first.value, cid:cid},
            success: function(data){
                result = eval(data);
                for(var x in result ){
                    var name = result[x].name;
                    var value = result[x].value;
                    second.options.add(new Option(name, value));
                }
            },error:function(xhr){alert(xhr.responseText)}
        });
    }
}

//多图的回显
function uploadMultiImg(e,id,isOne){
    var files = e.files;
    var length = files.length;
    var div = document.getElementById(id+"_div");
    if(isOne){//单张图片时清空
        $('#'+id+"_div").empty();
    }
    $.each(files,function(key,value){
        //回显：每次都只会遍历一个图片数据
        var img = document.createElement("img");
        // var hxImg = document.createElement("img");
        var a = document.createElement("a");
        div.style="display:inline-block; position:relative;";
        var fr = new FileReader();
        fr.onload = function(){
            img.width = 50;
            img.height = 50;
            img.src=this.result;
            div.appendChild(img);
            a.href="javascript:void(0);";
            a.id=id+"a";
            div.appendChild(a);
            var hxImg = '<Img src="/redmany/images/delete.jpg" style="position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;" onclick="delFile(\''+id+'\',\'image\')"></Img>';
            $("#"+id+"a").append(hxImg);
        }
        fr.readAsDataURL(value);
    })
}

//单张、多张图片的回显（新的）
function uploadImg(e,id,isOne){
    var files = e.files;
    var length = files.length;
    var div = document.getElementById(id+"_div");
    if(isOne){//单张图片时清空
        $('#'+id+"_div").empty();
    }
    $.each(files,function(key,value){
        //回显：每次都只会遍历一个图片数据
        var img = document.createElement("img");
        // var hxImg = document.createElement("img");
        var a = document.createElement("a");
        div.style="display:inline-block; position:relative;";
        var fr = new FileReader();
        fr.onload = function(){
            img.width = 50;
            img.height = 50;
            img.src=this.result;
            div.appendChild(img);
            a.href="javascript:void(0);";
            a.id=id+"a";
            div.appendChild(a);
            var hxImg = '<Img src="/redmany/images/delete.jpg" style="position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;" onclick="delFile(\''+id+'\',\'image\')"></Img>';
            $("#"+id+"a").append(hxImg);
        }
        fr.readAsDataURL(value);
    })
}

//单张、多张图片的回显（旧的）
function uploadImgOld(e,id,isOne){
    var files = e.files;
    var length = files.length;
    var div = document.getElementById(id+"_div");
    if(isOne){//单张图片时清空
        $('#'+id+"_div").empty();
    }
    $.each(files,function(key,value){
        //回显：每次都只会遍历一个图片数据
        var img = document.createElement("img");
        var hxImg = document.createElement("img");
        hxImg.id=id+"_del";
        var a = document.createElement("a");
        div.style="display:inline-block; position:relative;";
        var fr = new FileReader();
        fr.onload = function(){
            img.width = 50;
            img.height = 50;
            img.src=this.result;
            div.appendChild(img);
            a.href="javascript:void(0);";
            div.appendChild(a);
            hxImg.src="/redmany/images/delete.jpg";
            hxImg.style="position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;";
            // hxImg.onclick="delFile('image')";
            hxImg.addEventListener("click",delFile,false);
            if(isOne) {//单张图片时清空
                // $("#"+id+"_del").onclick="delFile('image')";
                // hxImg.click(function(){
                    // delFile('image');
                    // var flag = window.confirm("您确定要删除该文件吗?");
                    // if (flag) {
                    //     $("#"+id+"_div").empty();
                    // }
                // });
            }
            a.appendChild(hxImg);
        }
        fr.readAsDataURL(value);
    })
}

//图片、多图、视频的文件删除
function delFile(fileId,type){
    var flag = window.confirm("您确定要删除该文件吗?");
    if (flag) {
        if(type=="image"){
            $("#"+fileId+"_div").empty();
            var image = document.getElementById(fileId+"0");
            image.value="";//清空已选中的文件流
        }
        if(type=="multiImage"){
            $("#"+fileId+"_div").empty();
        }
    }
}

function del(imgId,delImgId,type){
    var flag = window.confirm("您确定要删除该文件吗?");
    if (flag) {
        if(type=="picture" || type=="video"){
            var img =document.getElementById(imgId);
            img.setAttribute('src',''); // 修改img标签src属性值
            document.getElementById(imgId).style.display="none";
            document.getElementById(delImgId).style.display="none";
            var obj = document.getElementById(type);
            obj.outerHTML=obj.outerHTML;
        }
        if(type=="adImg" || type=="describe"){
            var i = delImgId;
            var file_name="";
            if(type=="describe"){
                file_name = $("#describePicture")[0].files[i].name;
            }else{
                file_name = $("#adImg")[0].files[i].name;
            }
            var del_names=document.getElementById("del_"+type).value;
            if(del_names==""){
                document.getElementById("del_"+type).value=file_name;
            }else{
                document.getElementById("del_"+type).value=del_names+","+file_name;
            }
            $('#'+imgId).remove();
            $(".imgDiv").find(".delete").hide();
        }
    }
}
