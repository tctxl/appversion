<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,maximum-scale=1.0,minimum=1.0,user-scalable=no">
    <title><#if app??>${app.appName}</#if></title>
    <style>
        html, body {
            height: 100%;
            width: 100%;
            padding: 0;
            margin: 0;
            background-color: #f5f5f5;
            color: #333;
        }
    </style>
</head>
<body>
<div style="display: flex;flex-direction: column;align-items: center;padding-top: 80px;">
    <#if app??>
        <div style="margin-top: 20px;"><img src="${app.icon!''}"
                                            style="width: 80px;height: 80px;border-radius: 50%;object-fit: cover;">
        </div>

        <div style="margin-top: 10px;">
            <div style="font-size: 12px;color: #aaaaaa;text-align: center;">应用名称：</div>
            <div style="font-weight: bold;margin-top: 4px;">${app.appName}</div>
        </div>
        <div style="width: 300px;font-size: 14px;">
            <div>
                <div style="font-size: 12px;color: #aaaaaa;">平 台：</div>
                ${app.platform}
            </div>
            <div style="margin-top: 10px;">
                <div style="font-size: 12px;color: #aaaaaa;">版 本：</div>
                <div style="overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp:3;-webkit-box-orient:vertical;">${app.channels[0].versionName}</div>
            </div>
            <div style="margin-top: 10px;">
                <div style="font-size: 12px;color: #aaaaaa;">应用简介：</div>
                <div style="overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp:3;-webkit-box-orient:vertical;">${app.appDesc}</div>
            </div>
            <div onclick="window.location.href = '${app.channels[0].url}'" target="_blank" style="font-size: 14px;background-color: #000000;color: white;padding: 10px;text-align: center;border-radius: 20px;margin-top: 40px;box-shadow: 0 0 10px 3px #ccc;cursor: pointer;">下载</div>
        </div>
    </#if>
</div>
</body>
</html>
