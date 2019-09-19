<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <script type="text/javascript" src="js/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="layui/layui.js"></script>
    <link rel="stylesheet" href="layui/css/layui.css">
    <link rel="stylesheet" href="css/backLogins.css">
</head>
<body>
<script src="js/backLogin.js"></script>
<div class="wrap">
    <div class="company-name">
        <div class="com-logo"></div>
        <div class="com-name"><h3>红森林软件</h3></div>
    </div>
    <div class="log-con">
        <%--<h1>欢迎登录</h1>--%>
        <form class="logForm layui-form" action="">
            <div class="login-input">
                <label class="login-label"><i class="layuiusername-icon layui-icon-website" style="font-size: 30px; color: #1E9FFF;"></i>   <span>企业ID</span> </label>
                <input lay-verify="required" name="CompanyId" type="text" placeholder="请输入您的企业ID" />
            </div>
            <div class="login-input">
                <label class="login-label"><i class="layui-icon layui-icon-username" style="font-size: 30px; color: #1E9FFF;"></i>  <span>用户名</span> </label>
                <input lay-verify="required" name="username" type="text" placeholder="请输入您的用户名" />
            </div>
            <div class="login-input">
                <label class="login-label"><i class="layui-icon layui-icon-password" style="font-size: 30px; color: #1E9FFF;"></i>   <span>密     &nbsp;  码</span> </label>
                <input lay-verify="required" name="password" type="password" placeholder="请输入您的密码" />
            </div>
            <div class="login-input log-msg">
                    
                <label class="login-label"><i class="layui-icon layui-icon-vercode" style="font-size: 30px; color: #1E9FFF;"></i>   <span>验证码</span> </label>
                <input name="massage" type="text" placeholder="请输入您的验证码" style="width: 130px;" />
                <img alt="验证码" width="70" height="30" id="imagecode" src="<%= request.getContextPath()%>/servlet/ImageServlet"/>
               <%-- <div class="massage-reload">

                    <a href="javascript:reloadCode();" style="color: #fff;">看不清楚</a><br>
                &lt;%&ndash;<input type="submit" value="提交">&ndash;%&gt;
                </div>--%>
            </div>
            <div class="login-button">
               <%-- <input class="layui-btn login-sub" type="submit" value="登录"/>--%>
                <button class="layui-btn login-submit" lay-submit lay-filter="formDemo">登录</button>
            </div>
        </form>

    </div>

</div>
<script>

</script>

</body>
</html>
