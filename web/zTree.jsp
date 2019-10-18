<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">

    <script type="text/javascript" src="js/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="js/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="layui/layui.js"></script>
    <link rel="stylesheet" href="layui/css/layui.css">

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">红森林公众号后台</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="javascript:;" class="con-demo-active" dataUrl="addHtmls.jsp" >添加页面</a></li>
            <li class="layui-nav-item"><a href="addHtml.jsp">商品管理</a></li>
            <li class="layui-nav-item"><a href="javascript:;" onclick="menuClick(addHtml.jsp)">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">

                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    ${sessionScope.realName}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                    <dd><a href="login?out=1&url=backLogin.jsp">登出</a></dd>
                </dl>
            </li>
            <!-- <li class="layui-nav-item"><a href="">退了</a></li> -->
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="demo" >
                    <c:forEach items="${sessionScope.panelList}" var="p">
                        <li class="layui-nav-item layui-nav-itemed">
                            <a class="" href="javascript:;" target="menuFrame">${p.PanelName}</a>
                            <dl class="layui-nav-child">
                                <c:forEach items="${sessionScope.menuList }" var="m">
                                    <c:if test="${m.panel==p.ID }">
                                        <c:if test="${m.ParentMenu==0 }">
                                            <dd>
                                                <a href="javascript:;" class="con-left-active" lay-id="${m.Id}" dataUrl="queryStudentServlet?copformName=${m.FormName}&showType=${m.ShowType}">${m.MenuName }</a>
                                                <c:if test="${sessionScope.getPanelId!=null}">
                                                   <c:forEach items="${sessionScope.getPanelId}" var="panel">
                                                        <c:if test="${m.Id==panel.ParentMenu}">
                                                            <dl class="layui-nav-child">
                                                                <dd>
                                                                    <a href="javascript:;" class="con-demo-active" dataUrl="${panel.TemplateFrom}" >${panel.MenuName }</a>
                                                                </dd>
                                                            </dl>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </dd>
                                        </c:if>
                                    </c:if>
                                </c:forEach>

                            </dl>

                        </li>
                    </c:forEach>
            </ul>
        </div>
    </div>

    <div class="layui-body main-body">
        <!-- 内容主体区域 -->
        <!--     <div style="padding: 15px;">内容主体区域</div> -->
        <div id="page_content">
            <iframe id="iframe-page-content" src="index.jsp" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes"></iframe>

            <!-- <iframe id="menuFrame" name="menuFrame" src="addHtml.jsp" style="overflow:visible;"
            scrolling="yes" frameborder="no"></iframe> -->
        </div>
    </div>

    <!-- <div class="layui-footer">
      底部固定区域
      © layui.com - 底部固定区域
    </div> -->
</div>
<script src="./layui/layui.js"></script>
<script src="js/newForm.js"></script>

<script>
    /* var menuClick = function(menuUrl) {
    $("#iframe-page-content").attr('src',menuUrl);
    } */
    //JavaScript代码区域
    layui.use(['layer', 'form','jquery','element','laypage','table'], function() {
        var layer = layui.layer,
            $ = layui.jquery,
            element = layui.element,
            laypage=layui.laypage,
            laydate=layui.laydate,
            table=layui.table
            ,form = layui.form;



        $(".layui-nav-third-child").hide();
        $(".third-class").on('click',function () {
            $(".layui-nav-third-child").hide();
            $(this).next().show();
        });

        //点击菜单
        $('.con-demo-active').on('click',function(){
            var dataid=$(this);
            var a=dataid.attr("dataUrl");
            var b="http://oa.redmany.com:50003/"+a;
           // alert(b);
            $("#iframe-page-content").attr("src",b);
        });

        $('.con-left-active').on('click',function(){
            var dataid=$(this);
            var a=dataid.attr("dataUrl");
            var b='/WEB-INF/jsp/'+a;
            $("#iframe-page-content").attr("src",a);
          //  alert(a);
        });

    });
</script>


</body>
</html>
