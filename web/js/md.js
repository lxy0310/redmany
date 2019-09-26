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

});

function iframe(src) {
    if(ie && version < 9) {
        var iframe = document.createElement('<iframe src=""></iframe>');
        return iframe;
    } else {
        var iframe = document.createElement('iframe');
        iframe.setAttribute('src','');
        return iframe;
    }
}
// function f(title,content) {
//
// }
function addMDform(url) {
    //var s = iframe(url);
// alert(url);
//alert(s);
    alert(url);

    layer.tab({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: [{
            title: 'TAB1',
            content: url
        }, {
            title: 'TAB2',
            content: '<div><h3>我是标题</h3></div>'
        },]
    });

   /* layer.tab({
        area: ['600px', '300px'],
        tab: [{
            title: 'TAB1',
            content: v1
        }, {
            title: 'TAB2',
            content: '<div><h3>我是标题</h3></div>'
        }, {
            title: 'TAB3',
            content: '内容3'
        }],
        btnAlign: 'c',
        area: ['1000px', '700px'],
        btn:['提交','取消'],
        yes:function(index,layero){

            layer.msg("aaaa");
        }

    });*/
}