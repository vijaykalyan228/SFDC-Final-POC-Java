<%-- 
    Document   : mainApp
    Created on : Feb 3, 2017, 7:18:30 PM
    Author     : VijayKalyan
    Natuzzi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main App | Sfdc CPQ Test</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/toggle.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="JS/mainAppHelper.js"></script>
    </head>
    <body>
        <div id="container-fluid">
            <%
                if (request.getParameter("opportunityId") != null) {
                    AuthenticateAuthorizeTest.opportunityID = request.getParameter("opportunityId");
                }

                if (request.getParameter("amount") != null) {
                    boolean flag = AuthenticateAuthorizeTest.updateRESTAmount(Double.parseDouble(request.getParameter("amount")));
                    if (flag) {
            %>
            <script>$("#success").show();</script>
            <%
            } else {
            %>
            <script>$("#failure").show();</script>
            <%
                    }
                }
            %>
            <div id="success" class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Success!</strong> Amount updated successfully.
            </div>
            <div id="failure" class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>Error!</strong> A problem occurred while submitting your data.
            </div>
            <div class="row justify-content-md-center">
                <div class="col-sm-2"></div>
                <div class="col-sm-8 center-block">
                    <table id="tblTest" class="table table-striped text-center">
                        <tr>
                            <th class="text-center">Opportunity ID</th>
                            <th class="text-center">Opportunity Name</th>
                            <th/>
                        </tr>
                    </table>
                </div>
                <div class="col-sm-2"></div>
            </div>

            <div id="editOpportunity" class="modal fade" role="dialog">
                <div class="modal-dialog modal-lg">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"><label id="lblOppId"></label></h4>
                            <input type="hidden" id="oppID" name="editOppID" value=""/>
                        </div>
                        <div class="modal-body">
                            <p>Edit Opportunity</p>
                            <!--<form class="form-inline" action="mainApp.jsp" method="get">-->
<!--                                <div class="form-group">
                                    <label for="amount">Amount</label>
                                    <input name="amount" type="text" class="form-control" id="amount" placeholder="5000.0">-->
                                    <input type="hidden" name="opportunityId" id="paramOppId">
<!--                                </div>
                                <button type="submit" class="btn btn-default">Submit</button>-->
                            <!--</form>-->

                            <div class="justify-content-md-center">
                                <div id="dummy"></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <div id="deleteOpportunity" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"><label id="lblOppIdDel"></label></h4>
                        </div>
                        <div class="modal-body">
                            <p>Delete Opportunity?</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <script>
                var url = '<%= AuthenticateAuthorizeTest.loginInstanceUrl%>';
                var opportunities = JSON.parse(JSON.stringify(<%= AuthenticateAuthorizeTest.getOpportunities()%>));
                getOpportunities(opportunities, url);
            </script>
        </div>
    </body>
</html>
