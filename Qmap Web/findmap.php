<?php
    if(isset($_GET['cname'])) $cname=$_GET['cname'];
    else $cname=0;
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
  
    //get all record
    $qcommand= "select * from ".$dbtable." where name LIKE'%".$cname."%';";
    $rs=mysql_query($qcommand,$con);
    if(!$rs) die("Query error!");
    while($row=mysql_fetch_row($rs)){
      echo $row[1]."%".$row[2].",".$row[3].";";
    } 
    mysql_close($con);
?>

