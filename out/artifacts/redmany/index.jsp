<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Title</title>
  <link href="layui/css/layui.css">
  <script src="layui/layui.js"></script>

</head>
<body>
<input type="text" class="layui-input test-item" placeholder="yyyy-MM-dd">
<div class="layui-form-item ">
  <div class="layui-input-block">
    <div>
      <label>时间</label>
      <input placeholder="请选择时间" type="text" class="layui-input test-item">
    </div>
  </div>
</div>

<!-- 引入 highcharts.js -->
<%--<div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
      <input type="text" class="layui-input test-item" placeholder="yyyy-MM-dd">
    </div>
    <div class="layui-inline">
      <input type="text" class="layui-input test-item" placeholder="yyyy-MM-dd">
    </div>
    <div class="layui-inline">
      <input type="text" class="layui-input test-item" placeholder="yyyy-MM-dd">
    </div>
  </div>
</div>--%>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
  layui.use('laydate', function(){
    var laydate = layui.laydate;

    //同时绑定多个
    lay('.test-item').each(function(){
      laydate.render({
        elem: this
        ,trigger: 'click'
      });
    });

  });
</script>
</body>
</html>