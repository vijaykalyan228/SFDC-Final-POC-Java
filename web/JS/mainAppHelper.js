/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
window.opportunityID = "";

function updateOppId(id, name) {
    window.opportunityID = id;
    $("#lblOppId").text(id + " - " + name);
    $("#lblOppIdDel").text(id + " - " + name);
    $("#paramOppId").val(id);
    getLocations(id);
}

function getLocations(id) {
    $.get('getLocationServlet', {opportunityID: id},
            function (responseText) {
//            $('#tblLocations').append(responseText);
                console.log(responseText);         
                var locationsTemp = JSON.parse(JSON.stringify(responseText));
                window.locations = JSON.parse(locationsTemp);
                var table = "<table id='tblLocations' class='table table-striped text-center'><tr><th class='text-center'>Location Name</th><th class='text-center'>Address</th><th/></tr>";
                var tableContent = "";
                var cTable = "</table>";
                $("#dummy").html(table + cTable);
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
                var btnSubmit = "<button id='btnLocations' type='submit' class='btn btn-default'>Submit</button>";
                $("#dummy").append(btnSubmit);
            });
}

function getOpportunities(opportunities, url) {
    var tableContent = "";
    for (var i = 0; i < opportunities.totalSize; i++)
    {
        var tr = "<tr>";
        var td1 = "<td class='col-xs-3'>" + opportunities["records"][i]["Id"] + "</td>";
        var td2 = "<td class='col-xs-6'>" + opportunities["records"][i]["Name"] + "</td>";
        var view = "<td class='col-xs-3' id='btnActions'>"
                + "<a target='_blank' id='actions' href='" + url + "/" + opportunities["records"][i]["Id"] + "'"
                + " class='btn btn-default'><span class='glyphicon glyphicon-search'></a>";
        var settings = "<a target='_blank' id='actions' data-toggle='modal' href='#editOpportunity'"
                + " onclick='updateOppId( \"" + opportunities["records"][i]["Id"] + "\",\"" + opportunities["records"][i]["Name"] + "\" );'"
                + " class='btn btn-default'><span class='glyphicon glyphicon-cog'></a>";
        var trash = "<a target='_blank' id='actions' data-toggle='modal' href='#deleteOpportunity'"
                + " class='btn btn-default'><span class='glyphicon glyphicon-trash'></a>"
                + "</td>";
        var ctr = "</tr>";
        tableContent += (tr + td1 + td2 + view + settings + trash + ctr);
    }
    $("#tblTest").append(tableContent);
}

$(document).on('click', '#btnLocations', function () {
    $.get('OpportunityLocationsServelet', {opportunityID: $("#paramOppId").val(), locationID: getLocationIds()},
            function (responseText) {
                console.log(responseText);
            }
    );
});

function getLocationIds() {
    var locs = "";
    var deleteLocs = "***";
    for (var i = 0; i < locations.totalSize; i++) {
        if ($("#location" + i).is(":checked")) {
            locs += (locs === "" ? "" : ",") + locations["records"][i]["Id"];
        } else {
            deleteLocs += (deleteLocs === "***" ? "" : ",") + locations["records"][i]["Id"];
        }
    }
    console.log(locs+deleteLocs);
    return locs+deleteLocs;
}