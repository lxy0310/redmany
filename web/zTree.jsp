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
    <link rel="stylesheet" href="css/z-tree.css">
    <style>
        .layui-nav-tree .layui-nav-item a:hover{
            background-color: red;
        }
        .iframe-body{
            margin: 5px;
        }
    </style>
</head>
<%--<c:if test="${sessionScope.realName eq null}">
    <script>
        $(function () {
            window.location.href = "backLogin.jsp";
        });
    </script>
</c:if>--%>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">
    <div class="layui-header" style="background-color: #fff;color: #000;box-shadow: 0px 0.1px 8px #898989">
        <div class="layui-logo">
            <img width="25px" src="images/log_logo.png"/>
            <span style="color: #000;">红森林企业平台</span>
        </div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
       <%-- <ul class="layui-nav layui-layout-left">
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
        </ul>--%>
        <ul class="layui-nav layui-layout-right">
            <%--<li class="layui-nav-item">
                <a>
                    <img width="20px;" src="images/主页@2x.png">
                </a>
            </li>
            <li class="layui-nav-item">
                <a>
                    <img width="20px;" src="images/组 21@2x.png">
                </a>
            </li>
            <li class="layui-nav-item">
                <a>
                    <img width="20px;" src="images/我的@2x.png">
                </a>
            </li>--%>
            <li class="layui-nav-item">
                <a href="javascript:;" style="color: #898989">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    ${sessionScope.realName}
                   <%-- ${sessionScope.RealName}--%>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                    <dd><a href="login?out=1&url=backLogin.jsp">登出</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="login?out=1&url=backLogin.jsp">
                    <img width="20px;" src="images/out@2x.png">
                </a>
            </li>
            <!-- <li class="layui-nav-item"><a href="">退了</a></li> -->
        </ul>
    </div>
<%--    <div class="layui-side-scroll">
        <ul>
            <li class="menu-left" onclick="btn_toggle();">
                <div class="menu-left-divimg" >
                    <img class="menu-left-div-img" src="img/1@2x.png" />
                </div>
                <div class="menu-left-text" >
                    <img class="menu-show-icon" src="img/椭圆 4@2x.png" />
                    <a class="menu-left-text-t">首页</a>
                </div>
            </li>
            <li class="menu-left" >
                <div class="menu-left-divimg" >
                    <img class="menu-left-div-img" src="img/1@2x.png" />
                </div>
                <div class="menu-left-text" >
                    <a class="menu-left-text-t">首页</a>
                </div>
            </li>
            <li class="menu-left" >
                <div class="menu-left-divimg" >
                    <img class="menu-left-div-img" src="img/1@2x.png" />
                </div>
                <div class="menu-left-text" >
                    <a class="menu-left-text-t">首页</a>
                </div>
            </li>
        </ul>
    </div>--%>
    <div class="layui-side " style="box-shadow: 3px 0px 3px #898989;">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="demo" style="background-color: #006bff;">
                <li class="layui-nav-item layui-nav-itemed" id="speardIcon" style="display: inline-block;position: absolute;right: 20px;top: 10px;">
                    <div style="display: inline-block;" class="menu-icon"><img src="images/组 21@2x(1).png" width="20px"/></div>
                </li>
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:;" class="con-left-active" lay-id="01" dataUrl="queryStudentServlet?copformName=homecp_index&showType=copForm">
                        <div style="display: inline-block;" class="menu-icon"><img src="images/1@2x.png" width="20px"/></div>
                        <cite class="menu-cite">主页</cite>
                    </a>
                </li>
                    <c:forEach items="${sessionScope.panelList}" var="p">
                        <li class="layui-nav-item layui-nav-itemed">
                            <a class="" href="javascript:;">
<%--                                <div><img src="images/log_logo.png" width="25px"/></div>   target="menuFrame"--%>
                                <div style="display: inline-block;"><img src="http://oa.redmany.com:50003/document/2@2x.png" width="20px"/></div>
                                <cite class="menu-cite">${p.PanelName}</cite>
                            </a>
                            <dl class="layui-nav-child">
                                <c:forEach items="${sessionScope.menuList }" var="m">
                                    <c:if test="${m.panel==p.ID }">
                                        <c:if test="${m.ParentMenu==0 }">
                                            <dd>
                                                <a href="javascript:;" class="con-left-active" lay-id="${m.Id}" dataUrl="queryStudentServlet?copformName=${m.FormName}&showType=${m.ShowType}">
                                                        ${m.MenuName }</a>
                                                <c:if test="${sessionScope.getPanelId!=null}">
                                                   <c:forEach items="${sessionScope.getPanelId}" var="panel">
                                                        <c:if test="${m.Id==panel.ParentMenu}">
                                                            <dl class="layui-nav-child">
                                                                <dd>
                                                                    <a href="javascript:;" class="con-demo-active"  lay-id="${m.Id}" dataUrl="${panel.TemplateFrom}" >${panel.MenuName }</a>
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
        <div id="page_content" style="background: #f2f2f2;">
            <iframe id="iframe-page-content" class="iframe-body" src="queryStudentServlet?copformName=homecp_index&showType=copForm" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight=" 0" scrolling="yes" ></iframe>
            <%--  allowtransparency="true"  --%>
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
        var falg = true;

        $('#speardIcon').on('click',function(){
            if(falg){
               /* $(".menu-left-text").css("display","none");
                $(".menu-left").css("width","40px");
                $(".layui-body").css("left","40px");*/
               $(".layui-nav-tree").css("width","60px");
               $(".layui-nav-tree .layui-nav-child").css("display","none");
               $(".menu-icon").css("display","none");
                $(".layui-nav-tree .layui-nav-more").css("display","none");
                $(".menu-cite").css("display","none");
                falg =false;
            }else{
                $(".layui-nav-tree").css("width","200px");
                $(".layui-nav-tree .layui-nav-child").css("display","inline-block");
                $(".menu-icon").css("display","inline-block");
                $(".layui-nav-tree .layui-nav-more").css("display","inline-block");
                $(".menu-cite").css("display","inline-block");
               /* $(".menu-left-text").css("display","inline-block");
                $(".menu-left").css("width","240px");
                $(".layui-body").css("left","200px");*/
                falg =true;
            }
        });

    });
</script>


</body>
</html>
