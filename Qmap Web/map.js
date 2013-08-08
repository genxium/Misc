var map,searchService;
var xpos,ypos,address;
var marker_local,marker;
/*
  Function list:
1. init() // initializing the map and mouse events
2. cleartext() // just for textareas
3. findcompany(cname) // search in local database for keyword "cname"
4. parsepos(str) // a function to analyze a string "str" which contains a list of position informain in this form <name%x-coordinate,y-coordinate;> 
5. searchKeyword() // Main search function, calls Tecent's official search first, and then findcompany(cname)
*/

var init = function() {
  xpos=new Array(); ypos=new Array(); 
  marker=new Array(); marker_local=new Array();
  address=new Array();
  map = new QQMap.QMap(
     document.getElementById('container'),
     {
        center: new QQMap.QLatLng(39.916527,116.397128),
        zoomLevel: 13
   });
   map.setZoomInByDblClick(false);
   QQMap.QEvent.addListener(
      map, 
      'dblclick', 
      function(event) {
         var x=event.qLatLng.getLat(),y=event.qLatLng.getLng();
         Set_Cookie('x',x,1,'/',null,null);Set_Cookie('y',y,1,'/',null,null);
         var xmlhttp0;
         //create request   	  
         if (window.XMLHttpRequest) {xmlhttp0=new XMLHttpRequest();}
         else{xmlhttp0=new ActiveXObject("Microsoft.XMLHTTP");}
         //setup http header
         xmlhttp0.open("post","fill.php",true);
         xmlhttp0.onreadystatechange=function(){
           if (xmlhttp0.readyState==4 && xmlhttp0.status==200){
            document.getElementById("create").innerHTML=xmlhttp0.responseText;
           }
         }
         //send request here
         xmlhttp0.send();
   });
   searchService = new QQMap.QSearchService();
}

function cleartext() {setTimeout(document.getElementById("spot").value="",200);}

function findcompany(cname){
   var xmlhttp1;
   //create request   	  
   if (window.XMLHttpRequest){xmlhttp1=new XMLHttpRequest();}
   else{xmlhttp1=new ActiveXObject("Microsoft.XMLHTTP");}
   //setup http header
   xmlhttp1.open("post","findmap.php?cname="+cname,true);
   xmlhttp1.onreadystatechange=function(){
      if (xmlhttp1.readyState==4 && xmlhttp1.status==200)
      {
         var tb=xmlhttp1.responseText.toString();
         //document.getElementById("list").innerHTML=tb;
         parsepos(tb);
      }
   }
   //send request here
   xmlhttp1.send();
}

function parsepos(str){
  var i,j,st=0,ed;
  //for(i=0;i<str.length;i++)  document.getElementById("list").innerHTML+=str[i]+"&nbsp;";
  
  var input,index=0;
  for(i=st;i<str.length;i++){
     if(str[i]=='%'){
       ed=i;address[index]="";
       for(j=st;j<ed;j++)  address[index]+=str[j];
       st=i+1; }
     if(str[i]==';'){
       ed=i;input="";
       for(j=st;j<ed;j++)  input+=str[j];
       var latlngStr = input.split(",",2);
       xpos[index]= parseFloat(latlngStr[0]);ypos[index]= parseFloat(latlngStr[1]);
       st=i+1;++index; }
  }
  var latlngBounds = new QQMap.QLatLngBounds();
  
  for(i=0;i<index;i++){
     var latlng = new QQMap.QLatLng(xpos[i], ypos[i]);
     latlngBounds.extend(latlng); 
     marker_local[i] = new QQMap.QMarker({
          map: map,
          position: latlng
     });
     marker_local[i].setDraggable(true);
     var info = new QQMap.QInfoWindow({map: map});
                        
     QQMap.QEvent.addListener(marker_local[i], 'click', function(event) {
           var target=0,mini=9999999;
           for(var k=0;k<index;k++){ //choose the nearest point
              var dx=Math.abs(event.qLatLng.getLat()-xpos[k]);
              var dy=Math.abs(event.qLatLng.getLng()-ypos[k]);
              if(mini>dx+dy) {target=k;mini=dx+dy;}
           }
        info.open('<div style="width:350px;height:100px;">'+address[target]+'</div>',event.qLatLng);                       
      });//addListener
  }
  
  map.fitBounds(latlngBounds);//设置地图的中心点地理坐标和缩放级别，使其与指定范围相符。
  
  /*显示搜索结果*/

  var result=document.getElementById("result_local");
  var tab=document.createElement("TABLE");
  
  for(j=0;j<index;j++){
      var tr=tab.insertRow(j); tr.innerHTML+="地标";
      var td=tr.insertCell(0); td.innerHTML=j;
      tr.innerHTML+=":&nbsp;"+address[j]+"<br>";
      tr.style.color='#BF55BD';
      tr.addEventListener('click',
                          function(){
                             var num=parseInt(this.cells[0].innerHTML);
                             var latlng = new QQMap.QLatLng(xpos[num], ypos[num]);
                             map.moveTo(latlng);
                          },false);
   }
   result.innerHTML="";
   result.appendChild(tab);
}

function searchKeyword() {
     var index=0,i,j,k,pois;
     var keyword = document.getElementById("keyword").value;
     var region = document.getElementById("region").value;
     var latlngBounds = new QQMap.QLatLngBounds();
     /*
       search(request: QSearchRequest, callback: Function)
     */
     searchService.search({'keyword': keyword,'region':region}, function(results, status) {
        if (status == QQMap.QSearchStatus.OK) {
              var original = results.resultPois; //搜索结果存放 
              pois=new Array();
              // results : class QSearchResult , resultPois: class QSearchResultPoi
              // QSearchResultPoi includes an attribute "latlng:class QLatLng" which can call coordinates by getLat() and getLng()
              
              for(i=0;i<original.length;i++){
                 for(j=0;j<index;j++) if(original[i].address==pois[j].address) break;
                 if(j==index) pois[index++]=original[i];
              }// 去除重复结果
               
              for(i = 0;i < index; i++){
                  latlngBounds.extend(pois[i].latlng);    
                   marker[i]= new QQMap.QMarker({map: map,position: pois[i].latlng});
                   marker[i].setDraggable(true);
                   var info = new QQMap.QInfoWindow({map: map});
                     
                   QQMap.QEvent.addListener(marker[i], 'click', function(event) {
                      var target=0,mini=9999999;
                      for(k=0;k<pois.length;k++){ //choose the nearest point
                          var dx=Math.abs(event.qLatLng.getLat()-pois[k].latlng.getLat());
                          var dy=Math.abs(event.qLatLng.getLng()-pois[k].latlng.getLng());
                          if(mini>dx+dy) {target=k;mini=dx+dy;}
                      }
                      info.open('<div style="width:350px;height:100px;">'+pois[target].address+'</div>',pois[target].latlng);                       
                           
                   });//addListener
              }
              map.fitBounds(latlngBounds);//设置地图的中心点地理坐标和缩放级别，使其与指定范围相符。

              /*显示搜索结果*/
              var result=document.getElementById("result");
              var tab=document.createElement("TABLE");
              
              for(j=0;j<index;j++){
                   var tr=tab.insertRow(j); tr.innerHTML+="地点";
                   var td = tr.insertCell(0); td.innerHTML=j;
                   tr.innerHTML+=":&nbsp;"+pois[j].address+"<br>";
                   tr.style.color='#FF0066';
                   tr.addEventListener('click',
                                       function(){
                                         var num=parseInt(this.cells[0].innerHTML);
                                         map.moveTo(pois[num].latlng);
                                       },false);
              }
              result.innerHTML="";
              result.appendChild(tab);
        } //if status
    });
    findcompany(keyword);
}

