<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Data Importer</title>
  </head>
  <body>
    Import XML:<br/>
    <g:form controller="import" method="post" action="importXML"
      enctype="multipart/form-data">
        <input type="file" name="file"/><br/>
        <input type="submit"/>
    </g:form>
  </body>
</html>
