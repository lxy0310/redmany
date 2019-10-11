//gotoPage('goto:ServiceScreenPage,Cus_ServiceForm','ServiceScreenPage:s.parentId=5[^]globalVariable:classifyID=19');


function gotoPage(target, transferParams) {

    if(target.indexOf('goto:ServiceDetailsPage,Cus_ServiceDetailsForm')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('goto:NewsInfoList,listForm')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }

    if(target.indexOf('goto:preferencePage,copForm')>=0 && transferParams.indexOf('preferenceImg:p.state=1')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('goto:preferencePage,copForm')>=0 && transferParams.indexOf('preferenceImg:p.state=2')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('isNeedLogin:1[^]goto:WalletPage')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('isNeedLogin:1[^]goto:getCouponPage')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('isNeedLogin:1[^]goto:sharePage')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('isNeedLogin:1[^]goto:sharePage')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('goto:GoodRecommend')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }
    if(target.indexOf('isNeedLogin=1[^]goto:ClassfyCop')>=0){
        alert("相关功能还在持续优化升级中，敬请关注！");
        return;
    }

    //登出处理
    if (target.indexOf('logout:') >= 0) {
        //登出注销
         //获取后面的是否配置了goto事件
         if(target.indexOf('[^]') > 0){
             var strs = target.split('[^]');
             if(strs.length>=2){
                 var gotoStr=strs[1];
                 if(gotoStr.indexOf('goto:') >=0){
                     goto('login?out=1&url=' + escape(genUrl(gotoStr, null)));
                     return;
                 }
                 //没有配置goto
                 goto('login?out=1&url=' + escape(genUrl('goto:OaLoginHM,LoginForm', null)));
             }


         }else{
             goto('login?out=1&url=' + escape(genUrl('goto:OaLoginHM,LoginForm', null)));
         }

        return;
    }

    //登录事件
    if(target.indexOf('login:') >= 0){
        var loginAfterUrl=null;
        if(target.indexOf('[^]') > 0){
            var strs = target.split('[^]');
            if(strs.length>=2){
                var gotoStr=strs[1];
                if(gotoStr.indexOf('goto:') >=0){
                    loginAfterUrl=""+genUrl(gotoStr, null);

                }

             }

        }
        if(loginAfterUrl==null){
            //没有设置goto情况下，跳到默认界面
            loginAfterUrl=""+genUrl('goto:OaLoginHM,LoginForm', null);
         }
         //ajax
         //获取账号密码
         var UserName=$("#UserName0").val();
        //alert(UserName);
        var password=$("#password0").val();
        //alert(password);
     /*   $.ajax({
            url:'login',
            type:"post",
            data:{"userName":UserName,"password":password},
            dataType:"json",
            success:function (data) {
                alert(1);
            }

        });*/
        $.post("login",{"userName":UserName,"password":password},function (data) {
                var result=data.toString();
                if(result=="success"){
                    goto(loginAfterUrl);
                }else{
                    alert(data);
                }
        },"json");
        return;
    /*    $.ajax({
            url:"login",
            type:"post",
            data:{"userName":UserName,"password":password},
            dataType:"json",
            success:function(result){
                var flag = result.flag;
                if(flag==true){
                    //如果登录成功则跳转到成功页面
                    window.location.href="<%=basePath%>/pages/front/success.jsp";
                }else{
                    //跳回到Index.jsp登录页面，同时在登录页面给用户一个友好的提示
                    $(".tip").text("您输入的用户名或者密码不正确");
                }
            }

        });*/

    }

   //刷新事件
  /*  if(target.indexOf('refresh:') >= 0){
        location.reload();
    }*/

    if (target.indexOf('submit:') >= 0 ) {




        //{Id}在生成的页面的java，替换好
      var strs = target.split('[^]');
        var submit = strs[0];
        if (strs[0].length > 0) {
            if(submit=="isNeedLogin:1"){
                 submit = strs[1]
            }
            // var submit = strs[0];//submit:serviceOrder,newForm,$$itemProvideId={Id}╗price={totalPrice}
            var submitArr = submit.split('$$');
            var submit1 = submitArr[0].indexOf(":") >= 0 ? submitArr[0].split(":")[1] : submitArr[0];//submit:bondUser,newForm,
            var submit1s = submit1.split(",");
            for(var i =0;i<submit1s.length;i++){
                if(submit1s[i]==""){
                    submit1s.pop(submit1s[i]);
                }
            }
            var formName = submit1s[0];
            var showType = submit1s[1];
            //alert(formName);
           // alert(showType);
            //参数 itemProvideId={Id}╗price={totalPrice}
            var subparam="Company_Id=" + gCompany_Id + "&userid=" + gUesrId;
            //获取表单参数集
            //获取sumit formName
            //var formParam=    $("#"+formName+"Form").serialize();
            //alert( formParam);
           // $("#"+formName+"Form").attr("action","submit?formName="+formName+"&showType="+showType);
            //$("#"+formName+"Form").submit();
            var form=document.getElementById(formName+"Form");
            var formData=new FormData(form);
            formData.set("formName",formName);
            formData.set("showType",showType);
            //alert(formData);
            $.ajax({
               url:"submit",
               type:"post",
                data:formData,
                contentType:false,
                processData:false,
                cache:false,
                success:function (data) {

                    alert(data);

                }
            });
       /*    for (var t=1;t<submitArr.length;t++) {

               if (submitArr[t].indexOf("╗") >= 0) {
                   // var subparam = submitArr[1].replace('╗', '&') + "Company_Id=" + gCompany_Id + "&userid=" + gUesrId;

                   var subparam1 = submitArr[t].split('╗');//submitArr[1] = 'account={account}╗'   subparam1=  0:account={account}  1:null
                   var subparam2 = subparam1[0];  //subparam2='account={account}'
                   //alert(subparam2);
                   var subparam3 = subparam2.split('='); //subparam3   0:account  1:{account}
                   var s1=subparam3[1]+'0';
                //  alert(s1);
                   var s=document.getElementById(subparam3[0]+'0').value;
                //   alert(s);
                   var subparam4 = subparam2.replace(subparam3[1],s);//subparam4='  account= id对应的值'
                   subparam += "&" + subparam4;
               }
            }
            console.log(subparam);*/
          //  alert(subparam);

            //  submitArr循环

         /*   for (var j = 2; submit1s.length > j; j++) {
                subparam += "&" + submit1s[j];
             //   alert(submit1s[j])
            }

             var submitUrl = SumbitUrl+'formName=' + formName + '&showType=' + showType + '&' + subparam;*/

            //支付后跳转页面
            //strs[1]=goto:payFormBuyingService,freeForm
            // var refresh = strs[1].indexOf('refresh:') >= 0;
            // if (!refresh) {
            //     if (strs[1].indexOf('pay') >= 0) {
            //         var url = genUrl(strs[1], transferParams);
            //         payUrl = "wxpay?url=" + escape(url) + "&" + subparam;
            //     } else {
            //         payUrl = genUrl(strs[1], transferParams);
            //     }
            // } else {
            //     payUrl = null;
            // }
            // $.ajax({
            //     type: 'post',
            //     url: submitUrl,
            //     success: function (data) {
            //             //goto(submitUrl);
            //         /!*    alert(data)
            //         alert("注册成功");
            //         window.location.href="http://localhost:8080/queryStudentServlet?copformName=Service_mainPage&showType=copForm";*!/
            //        /!* var temp = 'qwerrreqwqwqw';
            //         alert(data.indexOf("success"));
            //         alert(temp.indexOf("success"));*!/
            //          /!* if (data.indexOf("success") != -1 ){
            //                 window.location.href="http://localhost:8080/queryStudentServlet?copformName=OaLoginHM&showType=LoginForm";
            //                 alert("注册成功");
            //             }*!/
            //     }
            // });

        }
        if(target.indexOf("[^]")>=0){

        }else{
            return;
        }


    }
    else if (target.indexOf('finish:') >= 0) {
        window.history.back();
        return;
    }
    else if (target == 'changepwd:') {
        var url = genUrl('goto:修改密码deformname,修改密码showtype', transferParams);
        goto(url);
        return;
    }
    else if(target.indexOf('wxpay:') >= 0 && target.indexOf('[^]') > 0){
        var nstr = target.split('[^]');
        if (nstr[0].indexOf('wxpay') >= 0) {
           goto('wxpay');
        }
    }
    else if (target.indexOf('goto:') < 0) {
       // alert(transferParams);
        postdo(target, transferParams);

        return;

    }
    if (target.indexOf('goto:') < 0) {
        //非跳转
        var _i = target.indexOf(':');
        if (_i > 0) {
            //点击条件，比如需要登录
            var ss = target.substring(0, _i).split("=");
            target = target.substring(_i + 1);
            var k = ss[0].trim();
            if (ss.length > 1) {
                var v = ss[1].trim();
                if (gValues[k] == null || '' + gValues[k] == '' + v) {
                    //条件判断不对
                    if ('isNeedLogin' == k) {
                        console.log('需要登录');
                        //跳到登录页
                        if (transferParams != null && transferParams.length > 0) {
                            console.log(escape(targetUrl), " escape(targetUrl)=====");
                            console.log(escape(transferParams), "escape(transferParams)");
                            goto('login?autologin=1&goto=' + escape(targetUrl) + "&transferParams=" + escape(transferParams));
                        } else {
                            goto('login?autologin=1&goto=' + escape(targetUrl));
                        }
                        console.log('need login ' + str + ' after goto ' + targetUrl);
                    } else {
                        console.log('dont work ' + str);
                    }
                    return;
                }
            }
        }
        return;
    }

    var ws = target.split('[^]');
    var targetUrl = null;
    var ifIndex = 0;

    for (var i = 0; i < ws.length; i++) {
        var str = ws[i];
        if (str.indexOf('goto:') == 0) {
            targetUrl = str;
            ifIndex = i;
            break;
        } else if (str.indexOf('homepage:') == 0) {
            targetUrl = 'goto:mainPage,copForm';
            if (str.indexOf('tabBarForm,') > 0) {
                if (str.lastIndexOf('1') == str.length - 1) {
                    //订单
                    targetUrl = 'goto:Service_classify,MainClassifyForm';
                } else if (str.lastIndexOf('2') == str.length - 1) {
                    //订单
                    targetUrl = 'goto:Service_order,copForm';
                } else if (str.lastIndexOf('3') == str.length - 1) {
                    //我的
                    targetUrl = 'goto:Service_personal,copForm';


                } else if (str.lastIndexOf('4') == str.length - 1) {
                    //客服
                    targetUrl = 'goto:OaLoginHM,LoginForm';
                } else {
                    alert('底部菜单没有分类');
                }
            }
            ifIndex = i;
            break;
        }

    }
    if (ifIndex > 0) {
        for (var i = 0; i < ifIndex; i++) {
            var str = ws[i];
            var ss = str.indexOf(':') > 0 ?
                str.split(":") :
                str.split("=");
            var k = ss[0].trim();
            if (ss.length > 1) {
                var v = ss[1].trim();
                if (gValues[k] == null || '' + gValues[k] == '' + v) {
                    //条件判断不对
                    if ('isNeedLogin' == k) {
                        //跳到登录页
                        if (transferParams != null && transferParams.length > 0) {
                            goto('login?autologin=1&goto=' + escape(targetUrl) + "&transferParams=" + escape(transferParams));
                        } else {
                            goto('login?autologin=1&goto=' + escape(targetUrl));
                        }

                        console.log('need login ' + str + ' after goto ' + targetUrl);
                    } else {
                        console.log('dont work ' + str);
                    }
                    return;
                } else {
                    break;
                }
            } else {
                console.log('don\'t check ' + str);
            }
        }
    }
    if (targetUrl == null) {
        console.error("无法处理：" + target);
        return;
    }
    var url = genUrl(targetUrl, transferParams);
    goto(url);
}

var posttask = null;

function postdo(_action, _params, _ok, _error) {
    console.log(_action);
    console.log(_params);
    var action = _action.replace('[^]', '#');
    var arr = new Array();
    arr = action.split(':');
    if (arr.length > 1 && arr[1] == '1') {
        gotoPage('goto:loginPage,OaLoginForm');
    }

    // var newAction=_action.split("[^]");
    // var arr=[];
    // for (var idx=0;idx<newAction.length;idx++){
    //     if(newAction[idx].indexOf(":")>0){
    //         arr.push(newAction[idx].split(':')[0]);
    //     }
    // }
    // if(_params!=null&&_params.indexOf("$flag")>0){
    //     var newParams=_params.split("$")[1];
    //     if(newParams.indexOf("=")>0){
    //         var flag=newParams.split("=")[1];
    //     }
    // }
    // for (var nidx=0;nidx<arr.length;nidx++){
    //     if(arr[nidx].indexOf("addshoppingcart")&&flag==="0"){
    //         console.log("加入购物车");
    //     }
    //     if(arr[nidx].indexOf("addshoppingcart")&&flag==="1"){
    //         console.log("立即购买");
    //     }
    // }

}

function genUrl(targetUrl, transferParams) {

    if(targetUrl.indexOf('goto:')>= 0){
        var strs = (targetUrl.split('goto:')[1]).split(',');

        var url = getUrl(strs[0], strs[1]);
    }
    if (transferParams != undefined && transferParams != null && transferParams != "") {
        url = url + '&transferParams=' + escape(transferParams);
    }
    return url;
}

function getUrl(copformName, showType) {
    /*var num =copformName.indexOf("|");
    if(num >-1){
        copformName=copformName.substr(0,num);
    }*/
    return 'queryStudentServlet?copformName=' + copformName + '&showType=' + showType;
}

function goto(url) {
    document.location = url;
}

var curId = '';

function findAddress(position) {
    startLoc(curId, position);
}

function checkLoc(Id) {
    curId = Id;
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(findAddress);
        return;
    }
    startLoc(Id, null);
}

function startLoc(Id, position) {
    var lat = '';
    var lon = '';
    if (position != null && position.coords != null) {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
    }

    var c = $('#' + Id);
    $.ajax({
        type: 'POST',
        url: 'location',
        data: {'lat': lat, 'lon': lon},
        success: function (data) {
            //返回的验证码
            console.log(data);
            var rs = eval('(' + data + ')');
            if (rs.statu == 0) {
                if (rs.shortName !== undefined) {
                    c.text(rs.shortName);
                } else if (rs.city !== undefined) {
                    c.text(rs.city);
                }
                // alert('当前城市是' + rs.city);
            }
            console.log(Id + "=" + rs);
        },
        error: function () {
            alert('定位失败');
        },
        dataType: JSON,
    });
}

function loc(Id) {
    gotoPage('goto:SelectArea,SelectAreaForm');
}

function searchAera() {
    gotoPage('goto:SelectArea,SelectAreaForm');
}
//搜索内容
function searchContent() {
    gotoPage('goto:Medical_selectPage,copForm');
}

function gotoOrder(state, id) {
    if (state == 4) {
        location.href = getUrl('OrderMidComment', 'freeForm') + '&orderId=' + id;
    } else if (state == 1) {
        //去支付页面
        var curUrl = location.href;
        alert('跳到支付');
    } else {
        var curUrl = location.href;
    }
}

var hidepwd = true;

function showHidePwd(Id) {
    if (hidepwd) {
        hidepwd = false;
        $('#passWord').val($('#passWord2').val());
        $('#passWord').show();
        $('#passWord2').hide();
        $('#' + Id).attr('src', 'images/show_pwd.png');
    } else {
        hidepwd = true;
        $('#passWord2').val($('#passWord').val());
        $('#passWord2').show();
        $('#passWord').hide();
        $('#' + Id).attr('src', 'images/hide_pwd.png');
    }
}

function showForm(phone) {
    if (phone) {
        $('#normal-login').hide();
        $('#phone-login').show();
        $('#lb-phone').removeClass('lb');
        $('#lb-phone').addClass('lb-active');
        $('#lb-name').removeClass('lb-active');
        $('#lb-name').addClass('lb');
    } else {
        $('#normal-login').show();
        $('#phone-login').hide();
        $('#lb-phone').removeClass('lb-active');
        $('#lb-phone').addClass('lb');

        $('#lb-name').removeClass('lb');
        $('#lb-name').addClass('lb-active');
    }
}

function checkForm(phone) {
    if (!phone) {
        if (hidepwd) {
            $('#passWord').val($('#passWord2').val());
        }
        var userName = $('#userName').val();
        if (userName == null || userName.length == 0) {
            alert('请输入账号！');
            return false;
        }
        var password = $('#passWord').val();
        if (password == null || password.length == 0) {
            alert('请输入密码！');
            return false;
        }
        return true;
    } else {
        var phone = $('#phone').val();
        var myreg = /^(1[1-9]\d{9})$/;
        if (!myreg.test(phone)) {
            alert('请输入有效的手机号码！');
            return false;
        }
        if (mPhone != phone) {
            alert('手机号码变化，请重新接收验证码！');
            return false;
        }
        //验证码
        if (!ignorePhone) {
            if (PhoneCode == '') {
                alert('请用手机接收验证码');
                return false;
            }
            var code = $('#code').val();
            if (code == null || code == '') {
                alert('验证码不能为空');
                return false;
            }
            if (code != PhoneCode) {
                alert('验证码不正确');
                return false;
            }
        }
    }
    return true;
}

function submitForm(Id) {
    var is_phone = 'phone-login' == Id;
    if (checkForm(is_phone)) {
        $('#' + Id).submit();
    }
}

var waitCode = 0;
var timeCount = 0;

//忽略验证码
var ignorePhone = false;
var PhoneCode = '';
var mPhone = '';

function sendCode(Id) {
    if (waitCode == 1) {
        return;
    }
    var myreg = /^(1[1-9]\d{9})$/;
    var phone = $("#phone").val();
    if (!myreg.test(phone)) {
        alert('请输入有效的手机号码！');
        return false;
    }
    waitCode = 1;
    if (ignorePhone) {
        return;
    }
    $.ajax({
        type: 'GET',
        url: 'phonecode?phone=' + phone,
        success: function (data) {
            //返回的验证码
            mPhone = phone;
            var rs = eval('(' + data + ')');
            PhoneCode = rs.code;
            console.log(data + " " + rs + ",PhoneCode=" + PhoneCode);
            waitCode = 1;
            //时间
            timeCount = 60;
            $('#' + Id).val(timeCount + "秒才能再次发送");
            $('#' + Id).attr("disabled");

            //短信sdk发送短信
            setTimeout("nextTime('" + Id + "');", 1000);
        },
        error: function () {
            waitCode = 0;
            $('#' + Id).val("发送失败");
        },
        dataType: JSON,
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


function addShoppingcart(userid, companyId, formName, pId) {
    $.ajax({
        type: 'GET',
        url: 'queryStudentServlet?addshop=1&pId=' + pId + '&userid=' + userid + '&companyId=' + companyId + '&formName=' + formName
        + '&copformName=test&showType=test',
        success: function (data) {
            var main = $('#main');
            $(main).append(data);
            $('#heyesb').addClass('modal-actionsheet_toggle');
        },
        error: function (data) {
            console.error("data请求失败");
        }
    });
}

function chooseSpec($this) {
    $('#specdiv').find('button').each(function (index, item) {
        if ($($this).attr('name') == $(this).attr('name')) {
            $(this).css('background', 'red');
            $(this).attr('data-choose', '1');
            $('#heyesb').find('span[name=price]').text($(this).attr('data-price'));
            $('#heyesb').find('span[name=stock]').text($(this).attr('data-stock'));
            $('#heyesb').find('span[name=cspec]').text($(this).attr('data-cspec'));
        } else {
            $(this).css('background', '');
            $(this).attr('data-choose', '0');
        }
    });
}

function numberAdd(shopId,userId) {
    var number = $('#heyesb').find('span[name=number]').text();
    var stock = $('#heyesb').find('span[name=stock]').text();
    if (parseInt(number) == parseInt(stock)) {
        alert("购买数量不能超过库存数量");
        return;
    }
    var itemCount=parseInt(number) + 1;
    $('#heyesb').find('span[name=number]').text(itemCount);
}

function numberSub() {
    var number = $('#heyesb').find('span[name=number]').text();
    var stock = $('#heyesb').find('span[name=stock]').text();
    if (parseInt(number) == 1) {
        alert("购买数量最少为1");
        return;
    }
    $('#heyesb').find('span[name=number]').text(parseInt(number) - 1);
}

function addShopCart(userid) {
    var id = 0;
    var number = 0;
    $('#specdiv').find('button').each(function (index, item) {
        if ($(this).attr('data-choose') == '1') {
            id = $(this).attr('data-id');
            number = $('#heyesb').find('span[name=number]').text();
        }
    });
    $.ajax({
        type: 'GET',
        url: 'queryStudentServlet?addshop=2&userid=' + userid + '&id=' + id + '&number=' + number + '&formName=test'
        + '&copformName=test&showType=test',
        success: function (data) {
            console.log(data);
            alert('添加成功！');
        },
        error: function (data) {
            console.error("data请求失败");
        }
    });
}

function hider($this) {
    $('#zzc').remove();
    $('#heyesb').remove();
}

var result = Array();

var post = null;

function searchAreaList() {
    if (post != null) {
        post.abort();
    }
    var text = $('#sc-text').val();
    post = $.ajax({
        type: 'POST',
        url: 'search',
        data: {'type': 'area', "key": text, 'tag': '房地产'},
        success: function (data) {
            if (data == null) {
                $('#find-div').show();
            } else if (data.results === undefined) {
                addList(data.result);
            } else {
                addList(data.results);
            }
        },
        error: function () {
            alert('搜索失败');
        },
        dataType: "json",
    });
    return false;
}

function addList(rs) {
    console.log(rs);
    result = rs;
    var list = $('#list');
    list.empty();
    if (rs.length == 0) {
        $('#find-div').show();
        return;
    }
    $('#find-div').hide();
    $.each(result, function (idx, obj) {
        var text = obj.address === undefined ? obj.name : "(" + obj.name + ") " + obj.address;
        var item = $('<div class="item" onClick="choose(' + idx + ');">' + text + '</div>');
        list.append(item);
    });
}

function choose(index) {
    var obj = result[index];
    var name = obj.name === undefined ? "" : obj.name;
    var address = obj.address === undefined ? "" : obj.address;
    if (post != null) {
        post.abort();
    }
    post = $.ajax({
        type: 'POST',
        url: 'location?save=1',
        data: {'lat': obj.location.lat, "lon": obj.location.lng, 'name': name, 'address': address},
        success: function (data) {
            console.log(data);
            location.href = 'queryStudentServlet';
        },
        error: function (req, statu, e) {
            console.log(statu);
            console.log(e);
            alert('保存出错，请重新选择');
            location.href = 'queryStudentServlet';
        },
        dataType: "text",
    });
}

// TabMenuForm
function changeGuide(formName,showType,transferParams) {
    $(".content").load("queryStudentServlet?copformName="+formName+"&showType="+showType);

}
function switchSexMan() {
    var imgSrc=$(".medical-content").attr("src");
    if(imgSrc.indexOf("wo1")>0||imgSrc.indexOf("boy1")>0){
        $(".medical-content").attr("src","images/medical_man1.png");
    }else if(imgSrc.indexOf("wo2")>0||imgSrc.indexOf("boy2")>0){
        $(".medical-content").attr("src","images/medical_man2.png");
    }
}
function switchSexWomen() {
    var imgSrc=$(".medical-content").attr("src");
    if(imgSrc.indexOf("man2")>0||imgSrc.indexOf("boy2")>0){
        $(".medical-content").attr("src","images/medical_wo2.png");
    }else if(imgSrc.indexOf("man1")>0||imgSrc.indexOf("boy1")>0){
        $(".medical-content").attr("src","images/medical_wo1.png");
    }
}
function switchSexChild() {
    var imgSrc=$(".medical-content").attr("src");
    if(imgSrc.indexOf("man2")>0||imgSrc.indexOf("wo2")>0){
        $(".medical-content").attr("src","images/medical_boy2.png");
    }else if(imgSrc.indexOf("man1")>0||imgSrc.indexOf("wo1")>0){
        $(".medical-content").attr("src","images/medical_boy1.png");
    }
}
function switchSex() {
    var imgSrc=$(".medical-content").attr("src");
    if(imgSrc.indexOf("wo1")>0){
        $(".medical-content").attr("src","images/medical_wo2.png");
    }else if(imgSrc.indexOf("wo2")>0){
        $(".medical-content").attr("src","images/medical_wo1.png");
    } else if(imgSrc.indexOf("man1")>0){
        $(".medical-content").attr("src","images/medical_man2.png");
    } else if(imgSrc.indexOf("man2")>0){
        $(".medical-content").attr("src","images/medical_man1.png");
    } else if(imgSrc.indexOf("boy1")>0){
        $(".medical-content").attr("src","images/medical_boy2.png");
    }else if(imgSrc.indexOf("boy2")>0){
        $(".medical-content").attr("src","images/medical_boy1.png");
    }
}
function switchNew(id) {
    var infos = "#newsinfo-"+id;
    $(infos).css("display","block").siblings().css("display","none");
}
function selDoctorOffice(id) {
    var office = ".doctor-content-"+id;
    var officeNameId=".officename-"+id;
    var officeName =$(officeNameId).text();
    $("#Medical_DoctorConsult-title").text(officeName)
    $(office).css("display","block").siblings().css("display","none");
}

function subOrder(userId,deliveryAddressId,cartInfoId,amountPaid,freightPayable,shopId) {
    if(cartInfoId.lastIndexOf("^")){
        cartInfoId=cartInfoId.slice(0,-1);
        console.log(cartInfoId);
    }
    if(shopId.lastIndexOf("^")){
        shopId=shopId.slice(0,-1);
        console.log(shopId);
    }
    var customerMsg=$("textarea").val();
    $.ajax({
        type: 'POST',
        url: 'shopCar',
        data: {'type': 'subOrder','userId': userId,'deliveryAddressId':deliveryAddressId,'cartInfoId':cartInfoId,'amountPaid':amountPaid,'freightPayable':freightPayable,'shopId':shopId,'customerMsg':customerMsg},
        success: function (data) {
            goto('wxpay');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
            alert('下单失败');
        },
        dataType: "json",
    });
}
function quickBuy(userid) {
    var nid = 0;
    var lastCount = 0;
    $('#specdiv').find('button').each(function (index, item) {
        if ($(this).attr('data-choose') == '1') {
            nid = $(this).attr('data-id');
            lastCount = $('#heyesb').find('span[name=number]').text();
        }
    });
    $.ajax({
        type: 'GET',
        url: 'queryStudentServlet?addshop=3&userid=' + userid + '&id=' + nid + '&number=' + lastCount + '&formName=test'
        + '&copformName=test&showType=test',
        success: function (data) {
            console.log(data);
            // success(数据提交成功)[609]{cartId=601}
            if(data.indexOf("cartId")>0){
                var seat=data.indexOf("cartId");
                var cartMsg=data.slice(seat,-1);
                if(cartMsg.indexOf("=")>0){
                    var cartId=cartMsg.split("=")[1];
                }
            }
            document.location = "queryStudentServlet?copformName=orderConfirm&showType=OrderConfirmForm&transferParams="+escape("orderConfirm:ci.Id =" + cartId);
        },
        error: function (data) {
            console.error("data请求失败");
        }
    });
}


function register(username,password) {
    $.ajax({
        type:'GET',
        url:userLogin+'company_id='+gCompany_Id+'&username=' + username+'&password=' + password,
        success:function (data) {
            console.log(data);
            alert("注册成功")
        }
    })

}

function regMessage() {
    var userName = $("#userName").val();
    var nickName = $("#nickName").val();
    var passWord = $("#passWord").val();
    var passWord2 = $("#passWord2").val();
    var code0 = $("#code0").val();
    if(userName!=null&&userName!=""&&userName!=undefined){
        if(nickName!=null&&nickName!=""&&nickName!=undefined){
            if(passWord!=null&&passWord!=""&&passWord!=undefined){
                if(passWord2!=null&&passWord2!=""&&passWord2!=undefined){
                    if(passWord==passWord2){
                        $.ajax({
                            type:'POST',
                            url:'register?company_id='+gCompany_Id+'&username=' + userName+'&password=' + passWord+'&nickName='+nickName+'&code='+code0,
                                success:function (data) {
                                console.log(data);
                                alert("注册成功");


                            }
                        })
                    }else {
                        $("#label-code").text("两次输入密码不一致");
                    }
                }else {
                    $("#label-code").text("请再次确认密码密码");
                }
            }else {
                $("#label-code").text("请输入密码");
            }
        }else {
            $("#label-code").text("请输入昵称");
        }
    }else {
        $("#label-code").text("请输入用户名");
    }
}


//分页跳转
function pageJump(formName,showType,pageIndex) {
    var url="queryStudentServlet?copformName="+formName+"&showType="+showType+"&pageIndex="+pageIndex;

    if(pageIndex=='goText'){
        url="queryStudentServlet?copformName="+formName+"&showType="+showType+"&pageIndex="+$("#goText").val();
    }
    goto(url);
}