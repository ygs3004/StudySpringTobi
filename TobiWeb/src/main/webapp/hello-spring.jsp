<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="kr.co.ygs.HelloSpring" %>
<html>
<head>
    <title>HelloSpring</title>
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
