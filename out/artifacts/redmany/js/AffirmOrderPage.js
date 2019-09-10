var couponV = 0;
function chooseCoupon(count, id){
    var pay = $('#coupon-'+id).attr('pay');
    couponV = pay;
    var rmb = count-pay;
    if (rmb < 0) {
        rmb = 0;
    }
    $('#realPay').val(rmb);
    $('#item-all').text('总价:'+count+' (实际:'+rmb+')元');
}
//$('#payform').submit();
function checkPayForm(){

    if($('#addressId').val()=='0'){
        //
        var name = $('#name').val();
        var phone = $('#phone').val();
        var address = $('#address').val();
        var adoorplate = $('#adoorplate').val();

        var myreg = /^(1[1-9]\d{9})$/;
        if (!myreg.test(phone)) {
            alert('请输入有效的手机号码！');
            console.log('phone='+phone);
            return;
        }
        if(name == null||name.length==0){
            alert('收货人不能为空');
            return;
        }
        if(address == null||address.length==0){
            alert('地址不能为空');
            return;
        }
        if(adoorplate == null||adoorplate.length==0){
            alert('门牌不能为空');
            return;
        }
    }else{
        console.log('addressId='+$('#addressId').val());
    }
    $('#payform').submit();
}