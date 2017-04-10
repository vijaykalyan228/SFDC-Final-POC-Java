<%-- 
    Document   : index
    Created on : 20 Feb, 2017, 6:22:22 PM
    Author     : user
--%>

<%@page import="com.helpers.ServiceLocationsHelper"%>
<%@page import="com.helpers.getLocationServlet"%>
<%@page import="com.helpers.AuthenticateAuthorizeTest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/toggle.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="JS/mainAppHelper.js"></script>
        <title>Locations | Sfdc CPQ Test</title>
        <script>
//            $(document).ready(function () {
//                getLocations('<%= request.getParameter("opportunityID")%>');
//            });
        </script>
    </head>
    <body>
        <div id="container-fluid">

            <div class="col-sm-2"></div>
            <div class="col-sm-8 center-block">
                <!--<div id="dummy"></div>-->
                <table id='tblLocations' class='table table-striped text-center'>
                    <tr>
                        <th class='text-center'>Location Name</th>
                        <th class='text-center'>Address</th>
                        <th></th>
                    </tr>
                </table>
                <script>

                    function ghostcheck() {
                        $("#tblLocations").append('<p>Oh no! A ghost! What would you like to do?<br><br></p>');
                    }

                    var locationsTemp = JSON.parse(JSON.stringify(<%= ServiceLocationsHelper.getLocations(request.getParameter("opportunityID"))%>));
                    window.locations = locationsTemp;
//                var table = "<table id='tblLocations' class='table table-striped text-center'><tr><th class='text-center'>Location Name</th><th class='text-center'>Address</th><th/></tr>";
                    var tableContent = "";
//                var cTable = "</table>";
//                $("#dummy").replaceWith(table + cTable);
                    for (var i = 0; i < locations.totalSize; i++)
                    {
                        var tr = "<tr>";
                        var td1 = "<td class='col-xs-3'>" + locations["records"][i]["Name"] + "</td>";
                        var td2 = "<td class='col-xs-6'>"
                                + "<address>"
                                + locations["records"][i]["pricesenz__Door_Number__c"] + " "
                                + locations["records"][i]["pricesenz__Street_Name__c"] + "<br/>"
                                + locations["records"][i]["pricesenz__City__c"] + " "
                                + locations["records"][i]["pricesenz__State__c"] + " "
                                + "</address>"
                                + "</td>";
                        var toggle = "<td class='col-xs-3'>"
                                + "<div class='onoffswitch'>"
                                + "<input type='checkbox' name='onoffswitch' class='onoffswitch-checkbox' id='location" + i + "' checked>"
                                + "<label class='onoffswitch-label' for='location" + i + "'>"
                                + "<span class='onoffswitch-inner'></span>"
                                + "<span class='onoffswitch-switch'></span>"
                                + "</label></div>"
                                + "</td>";
                        var ctr = "</tr>";
                        tableContent = (tr + td1 + td2 + toggle + ctr);
                        $("#tblLocations").append(tableContent);
                    }
                </script>
            </div>
            <div class="col-sm-2"></div>
        </div>
    </body>
</html>
