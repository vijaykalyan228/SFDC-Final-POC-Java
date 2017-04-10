<%-- 
    Document   : index
    Created on : 20 Feb, 2017, 6:22:22 PM
    Author     : user
--%>

<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index | SF Poc</title>
    </head>
    <body>
        <!--<h1>Hello World!</h1>-->
        <% 
            AuthenticateAuthorizeTest.opportunityID = request.getParameter("opportunityId");
            AuthenticateAuthorizeTest.accountID = request.getParameter("accountId");
            AuthenticateAuthorizeTest.accountName = request.getParameter("accountName");
            response.sendRedirect(new com.helpers.AuthenticateAuthorizeTest().getURI().toString()); 
        %>
    </body>
</html>
