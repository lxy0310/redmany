var layer,$,form,upload;
//一般直接写在一个js文件中

layui.use(['layer','element','form','upload'],function() {
    layer = layui.layer,
        element = layui.element,
        upload = layui.upload,
        form = layui.form;


    $(".addMDform").click(function () {

    });






    $('.site-demo-active').on('click', function(){
        var othis = $(this), type = othis.data('type');
        active[type] ? active[type].call(this, othis) : '';
    });

    //触发事件 选项卡切换
    var active = {
        tabAdd: function(){
            //新增一个Tab项
            element.tabAdd('sform', {
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
            element.tabChange('sform', '2'); //切换到：用户管理
        }
    };

    //Hash地址的定位
    var layid = location.hash.replace(/^#sform=/, '');
    element.tabChange('sform', layid);
    element.on('tab(sform)', function(elem){
        location.hash = 'sform='+ $(this).attr('lay-id');
    });


    $(".saveBtn").click(function() {
        var dataList=$(".saveData").val();
        var dataJson=eval('('+dataList +')');

        var d = {};
        //循环获取input的值
        var t=$('form').serializeArray();
        $.each(t, function() {
            d[this.name] = this.value;
        });
        //alert(JSON.stringify(d));
        //queryStudentServlet?copformName=Order_management,Order_management_info,Order_info_id&showType=MDnewForm
        //获取参数
        var paramId = $(".paramId").val();
        //alert(paramId);
        var FormName = $(".formName").val();
        alert(FormName);
        $.ajax({
            url:"common",
            data:{"method":"addForm","mdAddForm":JSON.stringify(d),"FormName":FormName,"paramId":paramId},
            //async:false,
            success:function(data){
                if (data>0){
                    layer.msg("操作成功！",{icon:6});
                    alert(data);
                    $("#mdID").value(data);
                    location.href = "";
                    // window.parent.location.reload();
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


});




function addMDform(url) {
    layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: url
    });
}