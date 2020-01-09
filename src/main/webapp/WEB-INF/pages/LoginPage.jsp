<!doctype html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
</head>
<style type="text/css">
form {
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
	<form method="post" action="/login" name="userform"
		class="needs-validation">
		<table align="center" width="300px" class="table">
			<tr>
				<td colspan=2 style="font-weight: bold;" align="center">Login</td>
			</tr>
			<tr>
				<td align="right">Username</td>
				<td><input name="username" class="form-control" /></td>
			</tr>
			<tr>
				<td align="right">Password</td>
				<td><input name="password" type="password"
						class="form-control" /></td>
			</tr>
			<tr>
				<td></td>
				<td><p>
						<input type="submit" name="Submit" value="login"> <a
							href="/registr" class="btn btn-secondary btn-lg active"
							role="button" aria-pressed="true">Registration</a>
					</p></td>
			</tr>
		</table>
	</form>
</body>
</html>