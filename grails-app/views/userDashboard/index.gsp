<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main">   
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  </head>
  <body>
    <br/>
    <br/>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>    
  <h3><g:message code="label.some_heading" default="Some Heading"/></h3>

  <div class="meritBadge">
    Some detailed info
  </div>	



</body>
</html>