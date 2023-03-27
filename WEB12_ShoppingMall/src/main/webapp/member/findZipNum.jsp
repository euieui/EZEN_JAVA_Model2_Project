<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{font-family:Verdana; background:#FDE8FF;}
#popup{padding:0 10px;}

table#zipcode{border-collapse:collapse; border-top:3px solid #6F0C78; border-bottom:3px solid #6F0C78
margin-top:15px; width:100%; font-size:100%;}
table#zipcode th, table#zipcode td{text-align:center; border-bottom:1px dotted #6F0C78;}
table th, td{padding:10px;}
table#zipcode a{display:block; height:20px; text-decoration:none; padding:10px;}
table#zipcode a:hover{font-weight:bold;}

</style>
<script type="text/javascript">
function result(zipNum,sido,gugun,dong){
	opener.document.joinForm.zip_num.value=zipNum;
	opener.document.joinForm.addr1.value=sido+" "+gugun+" "+dong;
	self.close();
}
</script>
</head>
<body>
<div id="popup">
	<h1>우편번호검색</h1>
	<form method="post" name="formm" action="shop.do">
		<input type="hidden" name="command" value="findZipNum">
		동 이름 : <input type="text" name="dong">
		<input type="submit" value="찾 기" class="submit">
	</form>
	<!-- 검색된 우편번호와 동이 표시되는 곳 -->
	<table id="zipcode">
		<tr><th width="100">우편번호</th><th>주소</th></tr>
		<c:forEach items ="${addressList }" var="addressVo">
			<tr>
				<td>${addressVo.zip_num}</td>
				<!-- onClick="result(우편번호, 시도,구군,동);" -->
				<td><a href="#" onClick="result('${addressVo.zip_num}',
				'${addressVo.sido }', '${addressVo.gugun }' ,'${addressVo.dong }' );">
				${addressVo.sido } ${addressVo.gugun } ${addressVo.dong } ${addrssVo.bunji}</a></td>
			</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>