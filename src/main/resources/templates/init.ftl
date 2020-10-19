<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>初始化入口點</title>
</head>
<body>
<a id="urladdress" style="display: none;" href="${entryCode}"></a>
<div id="urladdress2">地址：</div>
<div>用户名：${user}</div>
<div>密码：${pass}</div>
<div>请牢记入口点及用户名密码，如忘记入口点或密码，可以登录至服务器，查找entry/config.json文件查看</div>
<script>
    var href = document.getElementById("urladdress").href;
    document.getElementById("urladdress2").innerText = "地址："+href;
</script>
</body>
</html>
