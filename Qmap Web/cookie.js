function Set_Cookie(names,value,expires,path,domain,secure)
{
  // set time, it's in milliseconds
  var today = new Date();
  today.setTime( today.getTime() );

  if(expires) { expires = expires * 1000 * 60 * 60 * 24; }
  var expires_date = new Date( today.getTime() + (expires) );

  document.cookie =names+"="+escape(value)+((expires)?";expires="+expires_date.toGMTString():"")+((path)?";path="+path:"")+((domain)?";domain="+domain:"")+((secure)?";secure":"");
}

function Get_Cookie(names) {
  var start=document.cookie.indexOf(names+"=");
  var len=start+names.length+1;
  if ((!start)&&(names!=document.cookie.substring(0,names.length)))
  {return null;}
  if(start==-1) return null;
  var end=document.cookie.indexOf( ";", len );
  if(end==-1) end = document.cookie.length;
  return unescape(document.cookie.substring(len,end));
}


function Delete_Cookie( names, path, domain ) {
  if(Get_Cookie(names)) document.cookie=names+"="+
((path)?";path="+path:"")+
((domain)?";domain="+domain:"")+
";expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

