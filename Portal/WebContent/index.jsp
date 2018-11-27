<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Generate and Verify Token Demo</title>
<script type="text/javascript">


function authenUser(web){
	
	if(web == 1){
		document.forms[0].action = "/AgentSSO/AuthenServlet";
		document.forms[0].submit(); 
	}else if(web == 2){
		document.forms[0].action = "/SSOServer/WebAuthenTokenServlet";
		document.forms[0].submit(); 
	}
}

</script> 
</head>
<body>
	<form action="" method="post">
		<h3> portal Site</h3>
		<table>
			<tbody>
				<tr>
					<td>username : </td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>password : </td>
					<td><input type="password" name="password"></td>
				</tr>
			</tbody>
		</table>
		<input type="button" onclick="authenUser(1);" value="Login/Generate Token in AgentServer">
		
		<br>
		<br>
		
		<table>
			<tbody>
				<tr>
					<td>Token Generate with ID/PW : </td>
					<td><textarea name="tokenServerKey" rows="5" style="width: 800px;"><%= request.getAttribute("tokenServerKey") %></textarea></td>
				</tr>
			</tbody>
		</table>
		
		<br>
		
		<input type="button" value="Verify Token" onclick="authenUser(2);">
		
		<br>
		<h3>isValidToken : <span><%= request.getAttribute("isVidTokenServer")%></span></h3>
		<table>
			<tbody>
				<tr>
					<td>Username : </td>
					<td><%= request.getAttribute("username")%></td>
				</tr>
				<tr>
					<td>name : </td>
					<td><%= request.getAttribute("name")%></td>
				</tr>
				<tr>
					<td>surname : </td>
					<td><%= request.getAttribute("surname")%></td>
				</tr>
				<tr>
					<td>dob : </td>
					<td><%= request.getAttribute("dob")%></td>
				</tr>
				<tr>
					<td>sex : </td>
					<td><%= request.getAttribute("sex")%></td>
				</tr>
			</tbody>
		</table>
		
		<input type="hidden" name="contextFrom" value="webapp1">
	</form>
</body>
</html>