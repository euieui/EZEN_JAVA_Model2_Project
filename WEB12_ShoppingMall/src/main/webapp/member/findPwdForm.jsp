<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{background:#fde8ff;}
</style>
</head>
<body>
<h2>비밀번호 찾기</h2>
<form method="post" name="frm" action="shop.do">
<input type="hidden" name="command" value="findPwdStep1">
<table align="center" bgcolor="black" cellspacing="1" width="400">
	<tr align="center" bgcolor="#fde8ff"> <td width="430"><h3>아이디
		<input type="text" name="id" value="${id}"></h3></td></tr>
	<tr align="center" bgcolor="#fde8ff"> <td width="430"><h3>성명
		<input type="text" name="name" value="${name}"></h3></td></tr>
	<tr align="center" bgcolor="#fde8ff"> <td width="430">
		<h3>전화번호 <input type="text" name="phone" value="${phone }">
		</h3></td></tr>
	<tr align="center" bgcolor="#Fde8ff">
		<td width="430"><h3>	<input type="submit" value="인증번호 전송"></h3>
		${mgs}</td></tr>
</table>
</form>
</body>
</html>