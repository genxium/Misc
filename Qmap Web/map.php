<script charset="utf-8" src="http://s.map.qq.com/api/js/beta/v2.1/QQMapAPI.js"></script>
<script type="text/javascript" src="cookie.js"></script>
<script type="text/javascript" src="map.js"></script>
<?
  if(isset($_GET['warning'])) echo "请输入地标名称～<br>";
?>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>QQMap</title>    
  </head>

  <body BACKGROUND="bckgrd3.jpg" onload="init()" style="background-size: 100%;">
    <p style="backgroud: pink;color: blue;font-size: 14px;">Version 7 测试版， 请双击地图然后输入想要添加的地标名称并按确定，在‘目的地’框内输入你刚才添加的地标，看是否能搜索出来，另：滚动鼠标以缩放地图，点击搜索结果可自动切换到该地点</p><br><br>
    <div>
        目的地<input id="keyword" type="textbox" value="中">所在城市<input id="region" type="textbox" value="">
        <input type="button" value="搜索" onclick="searchKeyword()">
    </div>
    <br>
    <div style="width: 480px;height:250px" id="container"></div> 
    <p id="list"></p>
    <div id="create"></div>
    
    <div style="position: absolute;top: 120px;left: 510px; height: 300px; overflow: auto;" id="result"></div>
    <div style="position: absolute;top: 420px;left: 510px; heigth: 200px; overflow: auto" id="result_local"></div>
  </body>
</html>


