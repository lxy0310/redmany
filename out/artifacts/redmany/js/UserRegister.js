var waitCode = 0;
var timeCount = 0;

//忽略验证码
var ignorePhone = true;
var PhoneCode='';
var mPhone='';
function sendCode(Id) {
    if (waitCode == 1) {
        return;
    }
    var myreg = /^(1[1-9]\d{9})$/;
    var phone = $("#mobile").val();
    if (!myreg.test(phone)) {
        alert('请输入有效的手机号码！');
        console.log($("#mobile").val());
        return false;
    }
    waitCode = 1;
    if(ignorePhone){
        return;
    }
    $.ajax({
        type: 'GET',
        url: 'phonecode?phone='+phone,
        success: function(data) {
            //返回的验证码
            mPhone = phone;
            var rs = eval('('+data+')');
            PhoneCode = rs.code;
            console.log(data+" "+rs+",PhoneCode="+PhoneCode);
            waitCode=1;
            //时间
            timeCount = 60;
            $('#' + Id).val(timeCount + "秒才能再次发送");
            $('#' + Id).attr("disabled");

            //短信sdk发送短信
            setTimeout("nextTime('" + Id + "');", 1000);
        } ,
        error : function(){
            waitCode=0;
            $('#' + Id).val("发送失败");
        },
        dataType:JSON,
    });
}

function nextTime(Id) {
    timeCount--;
    if (timeCount <= 0) {
        waitCode = 0;
        $('#' + Id).val("发送验证码");
        $('#' + Id).removeAttr("disabled");
        return;
    }
    $('#' + Id).val(timeCount + "秒才能再次发送");
    setTimeout("nextTime('" + Id + "');", 1000);
}

var checkNameOK = 0;
var userName ='';

function checkName() {
    var name = $('#userName').val();
    if (name == null || name == '') {
        alert('用户名不能为空');
        return;
    }
    checkNameOK = 0;
    $.ajax({
        type: 'GET',
        url: 'register?check=1&userName='+ name,
        success: function(data) {
            console.log(data);
            if("ok"==data){
                checkNameOK = 1;
                userName=name;
                $('#lb-tip').text('用户名可用');
            }else{
                $('#lb-tip').text('用户名存在');
            }
        } ,
        error : function(){

        }
    });

}

function checkForm() {
    //用户名
    var name = $('#userName').val();
    if (name == null || name == '') {
        alert('用户名不能为空');
        return false;
    }
    if (checkNameOK == 0||userName!= $('#userName').val()) {
        alert('请检查先用户名是否可用');
        return false;
    }
    //密码
    if ($('#passWord').val() != $('#passWord2').val()) {
        $('#label-code').text('2次密码不一致');
        return false;
    }
    if($('#passWord').val().length<6){
        $('#label-code').text('密码至少6位');
        return false;
    }
    //手机号
    var myreg = /^(1[1-9]\d{9})$/;
    var phone = $("#mobile").val();
    if(!ignorePhone) {
        if (mPhone != phone) {
            alert('手机号变了请重新接收验证码');
            return false;
        }
    }
    if (!myreg.test(phone)) {
        alert('请输入有效的手机号码！');
        return false;
    }
    //验证码
    if(!ignorePhone) {
        if(PhoneCode==''){
            $('#label-code').text('请用手机接收验证码');
            return false;
        }
        var code = $('#code').val();
        if (code == null || code == '') {
            $('#label-code').text('验证码不能为空');
            return false;
        }
        if (code != PhoneCode) {
            $('#label-code').text('验证码不正确');
            return false;
        }
    }
    return true;
}
