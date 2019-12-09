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



   /* $(".saveBtn").click(function() {
        // var dataList=$(".saveData").val();
        // var dataJson=eval('('+dataList +')');

        var d = {};
        //循环获取input的值
        var t=$('form').serializeArray();
        alert(t);
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
    });*/

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

    //同时绑定多个
    lay('.test-item').each(function(){
        laydate.render({
            elem: this
            ,trigger: 'click'
        });
    });

});

$("#reset").on("click",function(){ window.location.reload();}); //重置

//时间
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

function searchCondition(url) {
    var d = {};
    //循环获取input的值
    var t=$('form').serializeArray();
    $.each(t, function() {
        d[this.name] = this.value;
    });
   /* var  searchUrl = window.location.href +"&condition="+ d;
    alert(searchUrl);
    location.href = searchUrl;*/

    var json = JSON.stringify(d);
    var json2map=JSON.parse(json);
    var condition = '';
    var ce1 = '';
    for(var key in json2map)
    {
        if (json2map[key]!=null && !json2map[key]==''){
           condition += key+":"+json2map[key]+",";
        }
    }
    condition = condition.substring(0,condition.length - 1);//去掉最后一个逗号
    /*var getUrl = url;
    var getUrl = "queryStudentServlet?copformName=user1&showType=listForm"; *///获取url
    getUrl = url + "&condition='"+ condition+"'";
    location.href = getUrl;

}

//返回上一页
function goUrl(){
    var url = document.referrer;
    window.location.href=url;

}

//点击td跳转到修改页面
function tableUpdate(formname,Id) {
    location.href = "queryStudentServlet?copformName="+formname+"&showType=newForm&optype=2&ParamId="+Id;
}
//点击td跳转到查看页面
function tableShow(formname,Id) {
    location.href = "queryStudentServlet?copformName="+formname+"&showType=newForm&optype=1&ParamId="+Id;
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
function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
}
//批量删除
function delBatch(FormName) {
    var s='';
    $('input[name="box1"]:checked').each(function(){
        s+=$(this).val()+','; //遍历得到所有checkbox的value
       /* $("table tr>td:first-child").hide();*/
       // var trList = $(this).parent().parent().find("td:eq(0)").text();//获取点击行的某一列
      //  console.log(trList);
       // alert(trList);
    });

    if (s.length > 0) {
        //删除多出来的“，”
        s = s.substring(0,s.length - 1);
    }
    var str = s.split(',');
    var selId = str.length;
    var pageSize = $("#PageSize").val();
    console.log(pageSize);
    console.log(str.length);

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
     /*           if (selId<pageSize){
                    window.parent.location.reload();
                }else {
                    var getUrl = window.location.href; //获取url
                    var index = getUrl.substring(getUrl.lastIndexOf('pageIndex=') + 10,getUrl.length);  //获取当前页
                    var nowIndex = page-1;//当前页减1 ，删除后的当前页数
                    var nowUrl = getUrl.replace(index,nowIndex);
                    window.location.href = nowUrl;
                }*/
                window.parent.location.reload();
            } else{
                layer.msg("删除失败！",{icon:5});
            }
        },error:function(){
            layer.msg("服务异常暂时无法删除,请及时联系工作人员！",{icon:5});
        }
    });

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
    alert(s);
    // 2
    $.ajax({
        url:"common",
        data:{"method":"batchList","gCompany_Id":gCompany_Id,"hidFormName":FormName,"id":s},
        type:"POST",
        success:function(data){
            if (data>0){
                layer.msg("操作成功！",{icon:6});
                window.parent.location.reload();
            } else{
                layer.msg("操作失败！",{icon:6});
            }
        },error:function(){
            layer.msg("服务异常暂时无法进行操作,请及时联系工作人员！",{icon:5});
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
        data:{"method":"batchList","listId":listId,"FormName":FormName,"AfterProcessState":AfterProcessState},
        type:"POST",
        success:function(data){
            if (data>0){
                layer.msg("操作成功！",{icon:6});
                window.parent.location.reload();
            }else {
                layer.msg("操作失败！",{icon:5});
            }
        },error:function(){
            layer.msg("服务异常暂时无法操作,请及时联系工作人员！",{icon:5});
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
            data:{"method":"delBatch","gCompany_Id":gCompany_Id,"hidFormName":hidFormName,"id":id},
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
function uploadMultiImg(e,id){
    var files = e.files;
    var length = files.length;
    // var div = document.getElementById(id);
    var oldValue = document.getElementById(id+"_old").value;
    var oldValueNum = 0;//旧图片的个数
    if(oldValue!='' && oldValue.length>0){
        var sf = oldValue.split(",");
        oldValueNum = sf.length;
    }
    var delValue = document.getElementById(id+"_del").value;
    var delValueNum = 0;//旧图片的删除个数
    if(delValue!='' && delValue.length>0){
        var delObj = delValue.split(",");
        delValueNum = delObj.length;
    }
    var divNum = document.getElementById(id).getElementsByTagName("div").length;//回显div的个数
    if(length>0){
        var startLen = oldValueNum-delValueNum;
        // alert(startLen);
        for(var i=oldValueNum-delValueNum;i<=divNum;i++){
            // $("#"+id+i+"_div").remove();
            if($("#"+id).children("div").length>i){
                $("#"+id).children("div")[i].remove();
            }
        }
    }
    $.each(files,function(key,value){
        //回显：每次都只会遍历一个图片数据
        var size = key;
        if(oldValueNum>0){
            // size = key+oldValueNum-delValueNum;
            size = key+oldValueNum;
        }
        var idStr = id+size;
        var div2 = '<div id=\''+idStr+'_div\' style="display: inline-block; position: relative;"></div>';
        $("#"+id).append(div2);
        var fr = new FileReader();
        fr.onload = function(){
            var img = '<img id=\''+idStr+'_img\' src=\''+this.result+'\' alt="查看图片" width="50" height="30">';
            $("#"+idStr+"_div").append(img);
            var a = '<a id=\''+idStr+'_a\' href="javascript:void(0);"></a>';
            $("#"+idStr+"_div").append(a);
            var hxImg = '<Img src="/redmany/images/delete.jpg" style="position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;" onclick="delMultiImg(\''+id+'\',\''+size+'\')"></Img>';
            $("#"+idStr+"_a").append(hxImg);
        }
        fr.readAsDataURL(value);
    })
}

//多图的删除
function delMultiImg(fileId,index){
    var flag = window.confirm("您确定要删除该图片吗?");
    if (flag) {
        // var file_name = $("#"+fileId+"0")[0].files[0].name;
        var divNum = document.getElementById(fileId).getElementsByTagName("div").length;//回显div的个数
        var fileNum = document.getElementById(fileId+"0").files.length;//文件的个数
        var oldValue = document.getElementById(fileId+"_old").value;
        var oldValueNum = 0;//旧图片的个数
        if(oldValue!='' && oldValue.length>0){
            var oldObj = oldValue.split(",");
            oldValueNum = oldObj.length;
        }
        var num =fileNum+oldValueNum;
        if(index<=oldValueNum-1){
            var delValue = document.getElementById(fileId+"_del").value;
            if(delValue!='' && oldValue.length>0){
                delValue=delValue+","+oldValue.split(",")[index];
            }else{
                delValue=delValue+oldValue.split(",")[index];
            }
            document.getElementById(fileId+"_del").value=delValue;
        }
        $("#"+fileId+index+"_div").remove();
    }
}

//单张图片的回显
function uploadImg(e,id){
    var files = e.files;
    var length = files.length;
    var div = document.getElementById(id+"_div");
    $('#'+id+"_div").empty();
    $.each(files,function(key,value){
        //回显：每次都只会遍历一个图片数据
        var img = document.createElement("img");
        // var hxImg = document.createElement("img");
        var a = document.createElement("a");
        div.style="display:inline-block; position:relative;";
        var fr = new FileReader();
        fr.onload = function(){
            img.width = 50;
            img.height = 30;
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

//单张图片、视频的文件删除
function delFile(fileId,type){
    var flag = window.confirm("您确定要删除该文件吗?");
    if (flag) {
        if(type=="image" || type=="video"){
            $("#"+fileId+"_div").empty();
            var image = document.getElementById(fileId+"0");
            image.value="";//清空已选中的文件流
        }
        // if(type=="multiImage"){
        //     $("#"+fileId+"_div").remove();
        //     var file_name = $("#lunbo0")[0].files[0].name;
        //     var sdf = document.getElementById("lunbo0").files.length;
        //     // alert(sdf);
        // }
    }
}

//待用
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

//点击链接触发上传事件
function upload_a(fileId) {
    document.getElementById(fileId).click();
}

//图片的放大与切换
function openImg(id) {
    layer.photos({
        photos: { "data": [{"src": 'http://oa.redmany.com:50016/document/071dede250d44fe4a519e7e857ad9e8f.jpg'},
                {"src": 'http://oa.redmany.com:50016/document/0321b01646e8420db016f093d39942d0.jpg'}] }
        ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
    });
}

//视频的上传回显
function uploadVideo(e,id){
    var files = e.files;
    var div = document.getElementById(id+"_div");
    $('#'+id+"_div").empty();
    $.each(files,function(key,value){
        var file_obj = files[0];
        var fd = new FormData();
        fd.append('video',file_obj);
        xhr = new XMLHttpRequest();
        xhr.open('POST', '/redmany/uploadFile', true)
        xhr.send(fd);
        xhr.onreadystatechange = function () {
            //后端接受完毕
            if(xhr.readyState == 4){
                var obj = JSON.parse(xhr.responseText);
                var url = obj.url;
                console.log(obj);
                var a = document.createElement("a");
                div.style="display:inline-block; position:relative;";
                var fr = new FileReader();
                fr.onload = function(){
                    var hidden = '<input type="hidden" id=\''+id+'_hidden\' value=\''+url+'\' />';
                    var video = '<video src=\''+this.result+'\' id=\''+id+'_hi\' style="height: 50px;width: 50px;" onclick="openVideo(\''+id+'_hidden\')"></video>';
                    $("#"+id+"_div").append(hidden);
                    $("#"+id+"_div").append(video);
                    a.href="javascript:void(0);";
                    a.id=id+"_a";
                    div.appendChild(a);
                    var hxImg = '<Img src="/redmany/images/delete.jpg" style="position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;" onclick="delFile(\''+id+'\',\'video\')"></Img>';
                    $("#"+id+"_a").append(hxImg);
                }
                fr.readAsDataURL(value);
            }
        };
    })
}

// 播放视频
function openVideo(id) {
    var classVideo = document.getElementById(id).value;
    var index = layer.open({
        type: 2,
        // content: $('#video_hi'),
        // content: 'http://oa.redmany.com:50016/document/LSQ_20190505_161704372.mp4',
        content: classVideo,
        area: ['600px', '450px'],
        offset:'t',
        maxmin: true,
        end: function () {
        }
    });
}

//文件的上传
function uploadFile(e,id){
    var files = e.files;
    var demoListView = $('#file_list');
        $.each(files,function(key,value){
        var fileSize = getfilesize(files[key].size);
        var fileName = files[key].name;
        // var div = '<div class="attsep" id="123"><span id="uploader123"><input type="button" class="icon-prev" /><span>账号.txt</span><span>(580B)</span></span></div>';
        var div = '<div id="attDivId"><span>'+fileName+'</span>&nbsp<span style="color: #798699">('+fileSize+')</span><div id="progressDiv" style="width:100px;height:18px;border-radius:8px;background-color: whitesmoke;display: inline-block;"><div style="display: inline-block;width: 0px;height: 18px; background: #979797;float:left " id="progressSpan">上传进度：0%</div></div></div>';
        demoListView.append(div);

        var fd = new FormData();
        fd.set("type_file","file");
        fd.append('file'+key,value);
        xhr = new XMLHttpRequest();
        xhr.type = "";
        xhr.open('POST', '/redmany/uploadFile', true)
        xhr.onload = uploadComplete; //请求完成
        xhr.onerror =  uploadFailed; //请求失败
        xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
        xhr.upload.onloadstart = function(){//上传开始执行方法
            ot = new Date().getTime();   //设置上传开始时间
            oloaded = 0;//设置上传开始时，以上传的文件大小为0
        };
        xhr.send(fd); //开始上传，发送form数据
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {//后端接受完毕
                var obj = JSON.parse(xhr.responseText);
                var error = obj.error;
                var saveName = obj.name;
                if (error == "0") {//上传成功
                    $("#progressDiv").remove();//移除进度条
                    var newDivId =  fileAddOrDel('add','#'+id+'_hidden',saveName);
                    $('#attDivId').attr('id', newDivId);
                    //删除按钮
                    var delbar = '<a onclick="delAttachFile(\'Uploader1570600617755016568701975846012\');return false">删除</a>';
                    $('#file_'+newDivId).append(delbar);
                } else if (error == "1") {

                }
            }
        }
    });
}

//上传进度实现方法，上传过程中会频繁调用该方法
function progressFunction(evt) {
    var loaded = evt.loaded;//已经上传大小情况
    var tot = evt.total;//附件总大小
    var per = Math.floor(100*loaded/tot);  //已经上传的百分比
    $("#progressSpan").html( per +"%" );
    $("#progressSpan").css("width" , per +"%");
    // console.log('附件总大小 = ' + loaded);
    // console.log('已经上传大小 = ' + tot);

    // var progressBar = document.getElementById("progressBar");
    // var percentageDiv = document.getElementById("percentage");
    // // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
    // if (evt.lengthComputable) {//
    //     progressBar.max = evt.total;
    //     var ot = evt.total;
    //     progressBar.value = evt.loaded;
    //     var oloaded = evt.loaded;
    //     percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
    // }
    //
    // var time = document.getElementById("progressSpan");
    // var nt = new Date().getTime();//获取当前时间
    // var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
    // ot = new Date().getTime(); //重新赋值时间，用于下次计算
    //
    // var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
    // oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
    //
    // //上传速度计算
    // var speed = perload/pertime;//单位b/s
    // var bspeed = speed;
    // var units = 'b/s';//单位名称
    // if(speed/1024>1){
    //     speed = speed/1024;
    //     units = 'k/s';
    // }
    // if(speed/1024>1){
    //     speed = speed/1024;
    //     units = 'M/s';
    // }
    // speed = speed.toFixed(1);
    // //剩余时间
    // var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
    // time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
    // if(bspeed==0)
    //     time.innerHTML = '上传已取消';
}

//上传成功响应
function uploadComplete(evt) {
    //服务断接收完文件返回的结果
    // $("#progressDiv").remove();
    // alert("上传成功！");
    //删除按钮
    // onclick="delAttach('Uploader157052531331506145200131618667');return false"
}
//上传失败
function uploadFailed(evt) {
    alert("上传失败！");
}
//取消上传
function cancleUploadFile(){
    xhr.abort();
}

//文件大小转换函数(保留两位小数),Size为字节大小
function getfilesize(size) {
    if (!size) return "";
    var num = 1024.00; //byte
    if (size < num)
        return size + "b";
    if (size < Math.pow(num, 2))
        return (size / num).toFixed(2) + "kb"; //kb
    if (size < Math.pow(num, 3))
        return (size / Math.pow(num, 2)).toFixed(2) + "MB"; //M
    if (size < Math.pow(num, 4))
        return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
    return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
}

//文件控件，上传或删除时对隐藏域的处理(参数：【操作类型、隐藏域id、文件名】；返回值：控件id)
function fileAddOrDel(type,hiddenId,fileName){
    var hiddenValue = $(hiddenId).val();
    if(type=='add'){
        if(hiddenValue=='' || hiddenId==null || hiddenId!=undefined){
            hiddenValue=fileName;
        }else{
            hiddenValue=hiddenValue+","+fileName;
        }
        $(hiddenId).val(hiddenValue);
    }else if(type=="del"){
        if(hiddenValue=='') {
            hiddenValue = '';
        }else{
            if(hiddenValue.index(fileName)>0){
                hiddenValue.remove(fileName);
            }else if(hiddenValue.index(","+fileName)>0){
                hiddenValue.remove(","+fileName);
            }
        }
        $(hiddenId).val(hiddenValue);
    }
    var oneSub = fileName.substr(fileName.indexOf("-suff-")+6,fileName.length);
    var twoSub = oneSub.substr(0,oneSub.indexOf("."));
    return twoSub;
}

function delAttachFile(divId,fileId) {
    $("#"+divId).remove();
}

//selectOnline的自动完成（下拉域的id、搜索输入框的id、隐藏域id、搜索框的旧内容、下拉数据['','',...]）
//selectOnline的自动完成（formFiled的id、搜索框的旧内容、下拉数据['','',...]` 替换器名称 ）
function AutoComplete(id, old_value, namelist, valuelist, rname) {
    var old_value = old_value;
    var highlightindex = -1;   //高亮
    var param = $("#" + id + "1").val().trim();
    if (param != old_value || old_value == "") {
        var autoNode = $("#" + id + "_domain");   //缓存对象（弹出框）
        var carlist = new Array();
        var idlist = new Array();
        var n = 0;
        old_value = param;
        //ajax调用
        var result;
        $.ajax({
            type:"POST",
            dataType:"json",
            url: 'view',
            data:{viewType:3, rname:rname, pname:id, param:param},
            success: function(data){
                result = eval(data);
                // var myReg = /^[\u4e00-\u9fa5]+$/;	//判断汉字的正则表达式
                // if (myReg.test(param)) {	//输入的是汉字
                //     alert('result: '+result[0].name+" "+result[0].value);
                // }
                if(result!=null){
                    carlist = result;
                }
                for(var x in result ){
                    var name = result[x].name;
                    var value = result[x].value;
                    if (name.indexOf(old_value) >= 0) {
                        carlist[n] = name;
                        idlist[n] = value;
                        n++;
                    }
                }
                if (carlist == null || carlist == 'null' || carlist=='undefined') {
                    autoNode.hide();
                    return;
                }
                autoNode.empty();  //清空上次的记录
                for (i in carlist) {
                    var wordNode = carlist[i];   //弹出框里的每一条内容
                    // var newDivNode = $("<div>").attr("id", i);    //设置每个节点的id值
                    var newDivNode = $("<div>").attr("id", idlist[i]);    //设置每个节点的id值
                    newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;");
                    newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框
                    newDivNode.mouseover(function () {//鼠标移入高亮，移开不高亮
                        if (highlightindex != -1) {        //原来高亮的节点要取消高亮（是-1就不需要了）
                            autoNode.children("div").eq(highlightindex).css("background-color", "white");
                        }
                        highlightindex = $(this).attr("id");//记录新的高亮节点索引
                        $(this).css("background-color", "#ebebeb");
                    });
                    newDivNode.mouseout(function () {
                        $(this).css("background-color", "white");
                    });
                    $("#"+id+"0").val("");
                    newDivNode.click(function () {//鼠标点击文字上屏
                        // var comText = autoNode.hide().children("div").eq(highlightindex).text();//取出高亮节点的文本内容
                        var comText = $("#"+highlightindex+"").text();
                        $("#"+id+"0").val(highlightindex);//为隐藏域赋值
                        highlightindex = -1;
                        $("#"+id+"1").val(comText);//文本框中的内容变成高亮节点的内容
                    })
                    if (carlist.length > 0) {    //如果返回值有内容就显示出来
                        autoNode.show();
                    } else {               //服务器端无内容返回 那么隐藏弹出框
                        autoNode.hide();
                        //弹出框隐藏的同时，高亮节点索引值也变成-1
                        highlightindex = -1;
                    }
                }
            },error:function(xhr){alert(xhr.responseText)}
        });
        // for(i=0;i<3;i++){
        //     var name = namelist.substring(1,namelist.length-1).split(",")[i].trim();
        //     var id = valuelist.substring(1,valuelist.length-1).split(",")[i].trim();
        //     // alert(name+"  "+old_value);
        //     if (name.indexOf(old_value) >= 0) {
        //         carlist[n] = name;
        //         idlist[n] = id;
        //         n++;
        //     }
        // }
        // // if (carlist.length == 0) {
        // console.log("saddsg "+carlist)
        // if (carlist == null || carlist=='undefined') {
        //     console.log("sd "+carlist)
        //     autoNode.hide();
        //     return;
        // }
        // autoNode.empty();  //清空上次的记录
        // for (i in carlist) {
        //     var wordNode = carlist[i];   //弹出框里的每一条内容
        //     // var newDivNode = $("<div>").attr("id", i);    //设置每个节点的id值
        //     var newDivNode = $("<div>").attr("id", idlist[i].trim());    //设置每个节点的id值
        //     newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;");
        //     newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框
        //     newDivNode.mouseover(function () {//鼠标移入高亮，移开不高亮
        //         if (highlightindex != -1) {        //原来高亮的节点要取消高亮（是-1就不需要了）
        //             autoNode.children("div").eq(highlightindex).css("background-color", "white");
        //         }
        //         highlightindex = $(this).attr("id");//记录新的高亮节点索引
        //         $(this).css("background-color", "#ebebeb");
        //     });
        //     newDivNode.mouseout(function () {
        //         $(this).css("background-color", "white");
        //     });
        //     $("#"+id+"0").val("");
        //     newDivNode.click(function () {//鼠标点击文字上屏
        //         // var comText = autoNode.hide().children("div").eq(highlightindex).text();//取出高亮节点的文本内容
        //         var comText = $("#"+highlightindex+"").text();
        //         $("#"+id+"0").val(highlightindex);//为隐藏域赋值
        //         highlightindex = -1;
        //         $("#"+id+"1").val(comText);//文本框中的内容变成高亮节点的内容
        //     })
        //     if (carlist.length > 0) {    //如果返回值有内容就显示出来
        //         autoNode.show();
        //     } else {               //服务器端无内容返回 那么隐藏弹出框
        //         autoNode.hide();
        //         //弹出框隐藏的同时，高亮节点索引值也变成-1
        //         highlightindex = -1;
        //     }
        // }
    }
    //点击页面隐藏自动补全提示框
    document.onclick = function (e) {
        var e = e ? e : window.event;
        var tar = e.srcElement || e.target;
        if (tar.id != id+"1") {
            if ($("#" + id +"_domain").is(":visible")) {
                $("#" + id + "_domain").css("display", "none")
                var pidStr = $("#"+id+"0").val();
                if(pidStr==null || pidStr==''){
                    $("#"+id+"1").val("");
                }
            }
        }
    }
}

var datePircker_locale = {
    format: "YYYY-MM-DD HH:mm:ss", //设置显示格式
    applyLabel: '确定', //确定按钮文本
    cancelLabel: '取消', //取消按钮文本
    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
        '七月', '八月', '九月', '十月', '十一月', '十二月'],
    firstDay: 1
};

/**
 * 返回最大可选择时间（日期范围选择器中备用）
 *  // maxDate: delay(0, 24, new Date("2019-12-20"))
 *  以上例子效果为 2019-12-25为最大可选日期
 * @param min
 * @param hour
 * @param date
 * @returns {Date}
 */
function delay(min, hour, date) {
    var oldTime=date.getTime();
    oldTime += min * 60 * 1000 * 5;
    oldTime += hour *60 * 60 *1000 * 5;
    return new Date(oldTime);
}

//计算两个日期之间的间隔天数
function  getDaysBetween(dateString1,dateString2){
    var  startDate = Date.parse(dateString1);
    var  endDate = Date.parse(dateString2);
    var days=(endDate - startDate)/(1*24*60*60*1000);
    // alert(days);
    return  days;
}

// 租车网站预订车辆
function buy_car(id) {
    var hotPrice = $('#hotPrice').text();//单价
    var use_id = $('#use_id0').val();//车辆用途
    var car_id = $('#car_id').text();//car_id
    var dates = $('#dates0').val();//预约日期
    if(use_id==""){
        alert("请选择车辆用途！");
        return;
    }
    if(dates==""){
        alert("请选择预约时间！");
        return;
    }else{
        var dateStr = dates.split(" - ");
        var dateNum = getDaysBetween(dateStr[0],dateStr[1])+1;//租赁天数
        var price = hotPrice*dateNum;
        // 所需值： 用途、id、金额、取车时间、还车时间、定金
        // 判断是否有已经被租的天数 escape(transferParams)
        // if(){
        // }
        var transferParams = "buy_car:id="+id+"[^]globalVariable:car_id="+id+",use_id=3,deposit=123,price="+price+",start_time="+dateStr[0]+",end_time="+dateStr[1];
        var url = "queryStudentServlet?copformName=buy_car&showType=copForm&transferParams="+escape(transferParams);
        document.location = url;
        // submit:userInfo_P,listModifyForm,Id={cacheId}$$patientId={fromGlobal_patientId}╗uId={fromGlobal_uId} ╗state=2
    }
}