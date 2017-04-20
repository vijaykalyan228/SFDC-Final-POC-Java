<%-- 
    Document   : callback
    Created on : Feb 2, 2017, 6:58:50 PM
    Author     : admin
--%>

<%@page import="com.helpers.LoginHelper"%>
<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Redirect JSP | Sfdc CPQ Test</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div id="container-fluid">

            <table class="table table-striped">
                <tr>
                    <td><b>Access Token</b></td>
                    <td><%= LoginHelper.getAccessToken(request.getParameter("code"))%></td>
                </tr>
                <tr/>
                <tr/>
                <tr>
                    <td>Redirecting.....</td>
                </tr>
            </table>

            <script>
                var timer = setTimeout(function () {
//                    window.location = 'products.jsp';
                    window.location = 'https://vijaykalyan228.github.io/sfpoc-ng/';
                }, 1);
            </script>
        </div>
    </body>
</html>
