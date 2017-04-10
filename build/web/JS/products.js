window.cart = [];


$(document).ready(function () {
    $.ajax({url: "ProductsServelet",
        context: document.body,
        success: function (responseText) {
//            alert("done");
//            console.log(responseText);         
            var productsTemp = JSON.parse(JSON.stringify(responseText));
            window.products = JSON.parse(productsTemp);

            for (var i = 0; i < products.totalSize; i++) {
                var div = "<div class='col s3'><div class='card black darken-1'>"
                        + "<div class='card-content white-text'>";
                var span = "<span class='card-title'>";
                var title = products.records[i].Name;
                var cSpan = "</span></div>";
                var divActions = "<div class='card-action text-center' style='border-top-color: #57595b'><a id='addProduct' onClick='addProductToQuote(" + i + ")' class='green-text'>Add to Quote";
                var close = "</a></div></div>";
                var cardContent = div + span + title + cSpan + divActions + close;

                var family = products.records[i].Family;
//                $("#cards").append(tableContent);
                switch (family) {
                    case "DATA":
                    {
                        $("#DATA").append(cardContent);
                        break;
                    }
                    case "HOSTING":
                    {
                        $("#HOSTING").append(cardContent);
                        break;
                    }
                    case "VOICE":
                    {
                        $("#VOICE").append(cardContent);
                        break;
                    }
                }
            }
        }
    });
});

function addProductToQuote(index) {
    console.log(index);
    if (!cart.includes(index)) {
        cart.push(index);
        renderCart(index);
    }
}

function renderCart(index) {
    if (cart.length === 0) {
        $("#quoteHead").text("Quote");
    } else {
        $("#quoteHead").text("Quote - " + cart.length);
        /*var li = "<li>";
         var lineItem = "<a>" + products.records[cart.length-1].Name + "</a>";
         var btnConfigure = "<button type='button' class='btn btn-default'>Configure</button>";
         var btnDelete = "<a><span class='glyphicon glyphicon-trash'></span></a>";
         var cli = "</li>";
         $("#cartItems").append(li+div+lineItem+btnConfigure+btnDelete+cdiv+cli);*/

        var tr = "<tr>";
        var td = "<td>";
        var ctd = "</td>";
        var ctr = "</tr>";

        var a = "<a href='#' class='dropdown-button'>";
        var lineItem = "<label>" + products.records[index].Name + "</label>";
        console.log(index);
        console.log(lineItem);
        var btnConfigure = "<button class='btn btn-default btn-xs transparent'>Configure</button>";
        var btnDelete = "<a href='#' class='deco-none'><span class='glyphicon glyphicon-trash'></span></a>";
        var ca = "</a>";

        $("#tblCartItems").append(a + tr + td + lineItem + ctd + td + btnConfigure + ctd + td + btnDelete + ctd + ctr + ca);
    }
}