<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
if(window.name=="update"){
	window.opener.location.href = "board.do?command=boardUpdateForm&num=${param.num}";
} else if (window.name=="delete"){
	window.opener.location.href = "board.do?command=boardDelete&num=${param.num}"; 
}
self.close();

// if문의 경우의 수 모두 수정- 삭제 동작에 해당하는 command 루틴을 생성하여 실해오디게 합니다.
// 모든 작업을 마치면 boardlist 로 이동합니다.

// 여기 update와 delete 에 따른 이동과 opener 팝업 등을 이용하는 방법 잘알아두자
</script>
</body>
</html>