<%--
  Created by IntelliJ IDEA.
  User: ygs30
  Date: 2023-12-29
  Time: 오전 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="kr.co.ygs.HelloSpring" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
</head>
<body>
    <%
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
                request.getSession().getServletContext());

        HelloSpring helloSpring = context.getBean(HelloSpring.class);
        out.println(helloSpring.sayHello("Root Context"));
    %>
</body>
</html>
