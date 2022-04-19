<%-- Document : cart Created on : 15 Mar 2022, 23:57:06 Author : elzbi --%>

<%@page import="java.math.BigDecimal"%>
<%@page import="lt.bit.eshop.data.KrepselioDetales" %>
<%@page import="lt.bit.eshop.data.Vartotojas" %>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<KrepselioDetales> cart = (List) request.getAttribute("cart");
    Krepselis k = (Krepselis) session.getAttribute("krepselis");
    Integer prekiuKiekis = (Integer) request.getAttribute("prekiuKiekis");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    BigDecimal total = (BigDecimal) request.getAttribute("total");
%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/9a66ff09af.js"
        crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
              crossorigin="anonymous">
        <style>
            .mygtukas {
                text-decoration: none
            }
            .plotis100 {
                width: 100px;
            }
        </style>
        <title>Krepšelis</title>
    </head>

    <body>
        <div class="container mb-3">
            <% if (cart == null || cart.isEmpty()) {%>
            <h3>Krepšelis tuščias. Norėdami įdėti prekių, <a href="./">eikite į mūsų
                    pagrindinį puslapį</a></h3>
                    <%} else {%>
            <h3><%=v != null ? v.getVardas() : ""%>Jūsų krepšelis. Norėdami pateikti užsakymą, spauskite apmokėti. Norėdami pridėti daugiau prekių, <a href="./">eikite į mūsų
                    pagrindinį puslapį</a></h3>
            <table class="table table-striped table-hover">
                <thead class="table-light">

                <th>Preke</th>
                <th>Aprašymas</th>
                <th>Kaina vnt</th>
                <th>Kiekis</th>
                <th>Suma</th>
                <th></th>
                </thead>
                <tbody>

                    <% for (KrepselioDetales kd : cart) {%>
                    <tr>

                        <td>
                            <%=(kd.getPreke().getPavadinimas() != null)
                                    ? kd.getPreke().getPavadinimas() : ""%>
                        </td>
                        <td>
                            <%=(kd.getPreke().getAprasymas() != null) ? kd.getPreke().getAprasymas()
                                    : ""%>
                        </td>
                        <td>
                            <%=(kd.getPreke().getKaina() != null)
                                    ? kd.getPreke().getKaina() : ""%>
                        </td>
                        <td>
                            <form action="./kiekis" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="hidden" name="kdId" value="<%=kd.getId()%>">                           
                                <span><input type="number" name="kiekis" class="form-control plotis100" value="<%=kd.getKiekis()%>"></span><span><input class="btn btn-primary mb-3" type="submit" value="Keisti"></span>   
                            </form>                            
                        </td>
                        <td>

                            <%=kd.getPreke().getKaina().multiply(new BigDecimal(kd.getKiekis()))%>
                        </td>
                        <td><a href="./delete?kdId=<%=kd.getId()%>"
                               class="text-danger"><i
                                    class="fas fa-trash-alt"></i></a></td>

                    </tr>
                    <%}%>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Iš viso</td>
                        <td><%=total%></td>
                    </tr>
                </tbody>

            </table>
            <form action="./buy" method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="krepselioId" value="<%=k.getId()%>">                           
                <input class="btn btn-primary mb-3" type="submit" value="Pirkti">   
            </form>
            <%}%>
        </div>
    </body>

</html>