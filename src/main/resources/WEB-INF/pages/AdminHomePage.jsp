<%@page import="java.util.List"%>
<%@page import="com.gongsi.app.persistence.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/library.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminHome Page</title>
</head>
<style type="text/css">
div.infoPos {
	position: absolute;
	top: 0;
	right: 0;
	padding: 20px;
}

div.tag {
	padding: 50px;
	background: #ccc;
	position: fixed;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}
</style>
<body>
	<div class="infoPos">
		<p align="right">Admin, <sec:authentication property="principal.username" />
		(<a href="/logout">Logout</a>)</p>
	</div>
	<div class="tag">
		<a href="/adminAdd">Add new user</a>
		<mytag:usertag users="${users}" />
	</div>
</body>
</html>