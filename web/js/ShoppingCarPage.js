function toString(arr) {
    var sel = '';
    for (var i = 0; i < arr.length; i++) {
        sel += arr[i];
        if (i != arr.length - 1) {
            sel += ',';
        }
    }
    return sel;
}
function removeByValue(arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == val) {
            arr.splice(i, 1);
            break;
        }
    }
}

function contains(arr, needle) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == needle) return true;
    }
    return false;
}
var sels=Array();

var allPay=0;
function changle(v, pay){
    var view = $(v);
    var val = view.val();
    console.log(val);
    if (contains(sels, val)){
        view.attr('checked', false);
        allPay -= pay;
        removeByValue(sels, val);
    }else{
        view.attr('checked', true);
        allPay += pay;
        sels.push(val);
    }
    if(allPay<0){
        allPay=0;
    }
    $('#item-all').text("总价："+allPay+"元");

}
function addOrder(url){
    //AffirmOrderPage:Id=
    document.location = url + "&transferParams="+escape("orderConfirm:ci.Id =" + toString(sels));
}



function showCount(count,shopId) {
    if (count > 0) {
        $('#item-count'+shopId).text(count);
    }else {
        $('#item-count'+shopId).text(0);
        count=0;
    }
}

var post = null;
function changeCount(userId, shopId,NowPrice,add) {

    var itemCountC=parseInt($('#item-count'+shopId).text());

    if(userId==0||userId=='0'){
        alert('请先登录，再返回刷新');
        return;
    }
    if (post != null) {
        post.abort();
    }

    if (add) {
        itemCountC++;
        allPay+= parseInt(NowPrice);
    } else {
        itemCountC--;
        allPay-= parseInt(NowPrice);
    }
    if(sels.length==0){

        $('#item-all').text("总价："+0+"元");
    }else {
        $('#item-all').text("总价："+allPay+"元");
    }

    showCount(itemCountC,shopId);
    post = $.ajax({
        type: 'POST',
        url: 'shopCar',
        data: {'type': 'mod','Quantity': itemCountC,'Id': shopId,'userName':userId },
        success: function (data) {
            // itemCount = data.count;
            showCount(itemCountC);
            console.log(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('修改数量失败');
        },
        dataType: "json",
    });

}

function orderPage(ServerId) {
    if (sels.length<=0){
        alert("请选择商品");
    }else {

        if (post != null) {
            post.abort();
        }
        post = $.ajax({
            type: 'POST',
            url: 'shopCar',
            data: {'type': 'payAll','totalPrice': allPay,'ServerId':ServerId},
            success: function (data) {
                document.location = "queryStudentServlet?copformName=AffirmOrderPage&showType=Cus_BzService_AffirmOrderForm&transferParams="+escape("s.ServerId=" +ServerId);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus);
                alert('下单失败');
            },
            dataType: "json",
        });

    }




}

function delShop(id,user) {
    $.ajax({
        type: 'POST',
        url: 'shopCar',
        data: {'type': 'del','Id': id,'userName':user },
        success: function () {
            alert('删除商品成功');
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('删除商品失败');
        },
        dataType: "json",
    });
}
function clearAllShop(user) {
    $.ajax({
        type: 'POST',
        url: 'shopCar',
        data: {'type': 'clear','user':user },
        success: function () {
            alert('清空所有商品成功');
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('清空所有商品失败');
        },
        dataType: "json",
    });
}


window.onload=function(){


    function professional(e) {
        var mainArr=["主任医师","副主任医师","主治医师","医师"];
        if($(e).text()!=null&&$(e).text()!=undefined){
            for(var i=0;i<$(e).text().length;i++){
                if ($(e).eq(i).text()==1){
                    $(e).eq(i).text(mainArr[0]);
                }else if($(e).eq(i).text()==2){
                    $(e).eq(i).text(mainArr[1]);
                }else if($(e).eq(i).text()==3){
                    $(e).eq(i).text(mainArr[2]);
                }else if($(e).eq(i).text()==4){
                    $(e).eq(i).text(mainArr[3]);
                }

            }
        }

    }

    professional(".Medical_MainMenu-item>#professionalNsme");
    professional(".Medical_getDoctorData-item >#professionalNsme");
    professional("#Medical_dordetail1>#professionalNsme");


}
