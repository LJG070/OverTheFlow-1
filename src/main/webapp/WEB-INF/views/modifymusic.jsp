<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form id="music" action="/music/modify" method="post" accept-charset="utf-8">
		���̵� : <input type="text" name="id" value="${music.id}"/> <br>
		���� : <input type="text" name="title" placeholder="${music.title}"/><br>
		����ð� : <input type="text" name="playtime" placeholder="${music.playtime}"/><br>
		�Ұ��� : <input type="text" name="description" placeholder="${music.description}"/><br>
		���� ���� : <input type="text" name="visibility" placeholder="${music.visibility}"/><br>
		�ٿ�ε� ��� ���� : <input type="text" name="downloadable" placeholder="${music.downloadable}"/><br>
		���Ƚ�� : <input type="text" name="playCount" placeholder="${music.playCount}"/><br>
		ī�װ� ���̵� : <input type="text" name="categoryId" placeholder="${music.categoryId}"/><br>
		ȸ�� ���̵� : <input type="text" name="memberId" placeholder="${music.memberId}"/><br>
		ȸ�� �г��� : <input type="text" name="memberNickname" placeholder="${music.memberNickname}"/><br>
		<button type="submit">�߰��ϱ�</button>
	</form>
</body>
</html>