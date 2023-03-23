<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 다운로드 연습</title>
</head>
<body>


	<!-- 4. -->
	<h1>파일 업로드가 완료되었습니다. ${map.fileList}</h1>
	
	<div class = 'result-images'>
	<c:forEach var='imageFileName' items='${map.fileList}'>
		<!-- 5. -->
		<img src="/download?imageFileName=${imageFileName}">
		<br><br>
	</c:forEach>
	</div>

</body>
</html>