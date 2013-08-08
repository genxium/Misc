<?php
  $name=$_POST['spot'];
  $x=$_COOKIE['x'];
  $y=$_COOKIE['y'];
  if(strcmp($name,"")==0) {header("location: map.php?warning=1");return;}
  else echo "name==&nbsp;".$name."<br>";
  //Database connection
  $dbhost="localhost:3306";
  $dbname="qqmap";
  $dbusr="root";
  $dbpsw="mardis1988";
  $dbtable="company";
  $con=mysql_connect($dbhost,$dbusr,$dbpsw);
  if(!$con) die("Can not connect to host!<br/>");
  
  //Select database
  mysql_select_db($dbname,$con) or die ("Couldn't select the database.");
  
  //insert new post into database
  //sample insert into company(name,x,y) values('MyHouse','5.00','3.14');
  $icommand= "insert into ".$dbtable."(name,x,y) values('".$name."','".$x."','".$y."');";
  mysql_query($icommand,$con);
  mysql_close($con);
  header("location: map.php");
?>

