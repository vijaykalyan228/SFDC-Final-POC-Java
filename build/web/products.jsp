<%-- 
    Document   : products
    Created on : 20 Mar, 2017, 4:07:22 PM
    Author     : VijayKalyan
--%>

<%@page import="com.helpers.LoginHelper"%>
<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Products | Sfdc CPQ Test</title>
        <link rel="stylesheet" href="css/products.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
        <script src="JS/products.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header navbar-center">
                    <a class="navbar-brand center"><%= AuthenticateAuthorizeTest.accountName%></a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a id="quoteHead" class="dropdown-toggle" style="background-color: green" data-toggle="dropdown" href="#">Quote</a>
                        <ul id="cartItems" class="dropdown-menu">
                            <li>
                                <table id="tblCartItems"></table>
                            </li>
                        </ul>
                    </li>
                    <li><a href="#">&times</a></li>
                </ul>
            </div>
        </nav>


        <div class="container-fluid">
            <h1>&nbsp;</h1>

            <div class="col-xs-1 col-lg-2"></div>
            <div class="col-xs-10 col-lg-8">
                <div class="row" id="cards">
                    <div id="DATA" class="productFamily">
                        <h6>DATA</h6>
                    </div>
                    <div id="HOSTING" class="productFamily">
                        <h6>HOSTING</h6>
                    </div>
                    <div id="VOICE" class="productFamily">
                        <h6>VOICE</h6>
                    </div>
                </div>
            </div>
            <div class="col-xs-1 col-lg-2"></div>
        </div>
    </body>
</html>