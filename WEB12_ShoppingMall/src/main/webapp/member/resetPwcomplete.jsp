<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{background:#fde8ff;}
</style>
<script type="text/javascript">
function move_login(){
	opener.location.href="shop.do?command=loginForm";
	self.close();
}
</script>
</head>
<body>
<h2>비밀번호 재설정 완료</h2>
<table aling="center" bgcolor="black" cellspacing="1" width="400">
	<tr aling="center" bgcolor="#fde8ff" hegiht="200">
		<td width="230"><h3>비밀번호 재설정이 완료되었습니다.</h3><br>
			<input type="button" value="로그인 페이지로"
				onClick="move_login();">
		</td>
	</tr>		
</table>
</body>
</html>