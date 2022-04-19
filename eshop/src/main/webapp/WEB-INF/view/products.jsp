<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="lt.bit.eshop.data.PrekiuStatistika"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<PrekiuStatistika> products = (List) request.getAttribute("products");
    List<PrekiuStatistika> soldProducts = (List) request.getAttribute("soldProducts");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");

%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/9a66ff09af.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
              crossorigin="anonymous">        
        <style>
            .mygtukas {
                text-decoration: none
            }
        </style>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="./js/main.js">
        </script>
        <title>E-parduotuvė</title>
    </head>

    <body>
        <div class="container">
         
                <%if (products != null && !products.isEmpty()) {%>
                <h3>Visų prekių įdėtų į krepšelius statistika</h3>
                <a href="/eshop/stats/sold" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Nupirktų prekių statistika</div></a>
                <%} else if (soldProducts != null && !soldProducts.isEmpty()){%>
                 <h3>Nupirktų prekių statistika</h3>
                <a href="/eshop/stats/products" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Visų idėtų į krepšelį prekių statistika</div></a>
                <%}%>
   <p class="text-end"> 
                Prisijungęs kaip <%=v.getVardas()%> <a class="mygtukas text-secondary" href="./account"> 
                    <span class="btn btn-outline-secondary">Mano Paskyra</span>
                </a>
            <form action="./logout" method="POST" class="text-end">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input name="submit" type="submit" value="logout" />
            </form> 
            <%if (v.getAdmin()) { %> 
            <p class="text-end"> <a href="./roles">Tvarkyti grupes</a> </p>

            <%}%>

        </p>

        <form class="p-3">
            <div class="row">  
                <div class="col-6">
                    <input class="form-control" id="filter" class="form-text" name="filter">
                </div>
                <div class="col-6"> <input class="btn btn-secondary text-light" type="submit" value="Ieškoti">
                    <a href="./" class="text-secondary link mb-3 col-1 h-100 p-3">
                        <span class="btn btn-outline-secondary mb-3">Rodyti visas prekes</span>
                    </a>
                </div> 

            </div>
        </form>
        <div class="row">

            <a href="?filter=" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Valyti filtrus</div></a>               
            <a href="/eshop/stats/carts" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Krepšelių statistika</div></a>

        </div>
        
        <%if (products != null && !products.isEmpty() || soldProducts != null && !soldProducts.isEmpty()) {%> 
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <th>Preke</th>        
            <th>Kiek kartų įdėta į krepšelį <a href="?sort=desc"><i class="fa-solid fa-arrow-down-wide-short"></i></a> <a href="?sort=asc"><i class="fa-solid fa-arrow-up-short-wide"></i></a></th>                
            <th>Suma <a href="?sortSum=desc"><i class="fa-solid fa-arrow-down-wide-short"></i></a> <a href="?sortSum=asc"><i class="fa-solid fa-arrow-up-short-wide"></i></a></th>
            <th></th>
            </thead>
            <tbody>

                <% if (products != null && !products.isEmpty()) {
                        for (PrekiuStatistika p : products) {%>                      
                <tr>
                    <td> <%= p.getPrekesPavadinimas()%>
                    </td>

                    <td>
                        <%=p.getKiekis()%>
                    </td>
                    <td>
                        <%=p.getSuma()%>
                    </td>

                    <td>

                    </td>

                </tr>
                <%}
                } else if (soldProducts != null && !soldProducts.isEmpty()) {
                    for (PrekiuStatistika p : soldProducts) {%>                      
                <tr>
                    <td> <%= p.getPrekesPavadinimas()%>
                    </td>

                    <td>
                        <%=p.getKiekis()%>
                    </td>
                    <td>
                        <%=p.getSuma()%>
                    </td>

                    <td>

                    </td>

                </tr>
                <%}
                    }
                %>

            </tbody>
        </table>
        <%}%> 
    </div>
</body>

</html>