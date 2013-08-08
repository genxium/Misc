<?$x=$_COOKIE['x'];$y=$_COOKIE['y'];?>

你确定要增加这个地标吗？地标位置为 <? echo "<l style='font-size: 12px;'><".$x.'°,'.$y."°></l><br>";?>
<? echo "<form id=\"mainform\" enctype=\"multipart/form-data\" action=\"opmap.php?x=$x&y=$y\" method=\"POST\" onload=\"cleartext();\">" ?>
   地标名称: <input id="spot" name="spot" type="text" value="" AUTOCOMPLETE=OFF></input><br/>
            <input type="button" value="create point" onclick="mainform.submit();cleartext();"/>
         </form>
