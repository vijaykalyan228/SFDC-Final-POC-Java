<%-- 
    Document   : oauth
    Created on : 11 Apr, 2017, 4:56:57 PM
    Author     : user
--%>

<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <ul>
            <li><%= request.getParameter("access_token")%></li>
            <li><%= request.getParameter("instance_url")%></li>
            <li><%= request.getParameter("token_type")%></li>
            <li><%= request.getPathInfo()%></li>
            <li id="location"></li>
        </ul>
        <script>
            window.onload = function () {
                var url = window.location.href;
                var arr = url.split("#");
//                window.location = "https://vijaykalyan228.github.io/sfpoc-ng/oauth" + "?" + arr[1];
                if (arr.length > 1) {
                    window.location = arr[0] + "?" + arr[1];
                }
//                var event = new CustomEvent("oauthCallback", {'detail': arr[0] + "?" + arr[1]});
//                window.opener.document.dispatchEvent(event);

            <%
                AuthenticateAuthorizeTest.accessToken = request.getParameter("access_token");
                AuthenticateAuthorizeTest.loginInstanceUrl = request.getParameter("instance_url");
            %>
                if (arr.length === 1) {
                    window.close();
                }
            };
        </script>
    </body>
</html>
