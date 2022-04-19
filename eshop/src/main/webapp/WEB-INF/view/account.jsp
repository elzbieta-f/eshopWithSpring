<%-- Document : cart Created on : 15 Mar 2022, 23:57:06 Author : elzbi --%>

<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="lt.bit.eshop.data.KrepselioDetales" %>
<%@page import="lt.bit.eshop.data.Vartotojas" %>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<Krepselis> krepseliaiDone = (List) request.getAttribute("krepseliaiDone");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    Map<Krepselis, BigDecimal> sumos = (Map) request.getAttribute("krepseliuSumos");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String pass = request.getParameter("passChange");
    String passInfo = "";
    if (pass != null) {
        if (pass.equals("success")) {
            passInfo = "passSuccess('Slaptažodis sėkmingai pakeistas')";
        } else if (pass.equals("fail")) {
            passInfo = "passFail('Nepavyko pakeisti slaptažodžio')";
        }
    }

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
         <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="./js/main.js">
        </script>
        <title>Krepšelis</title>
    </head>

    <body onload="<%=pass != null&&(pass.equals("success") || pass.equals("fail")) ? passInfo: ""%>">
        <div class="container mb-3">
            <h4>Sveiki, <%=v.getVardas()%>! Čia galite valdyti savo paskyrą bei pamatyti pirkimų istoriją</h4>
            <p><a href="./">Į parduotuvę</a></p>
            <p><a href="./account/change">Keisti slaptažodį</a></p>
                <% if (krepseliaiDone.isEmpty()) {%>
            <h4>Dar neturite įvykdytų užsakymų. Norėdami įdėti prekių į krepšelį, <a href="./">eikite į mūsų
                    pagrindinį puslapį</a></h4>
                    <%} else {%>
            <h3>Įvykdyti pirkimai</h3>
            <table class="table table-striped table-hover">
                <thead class="table-light">

                <th>Krepšelio ID</th>
                <th>Įvykdytas</th>
                <th>Sukurtas</th>
                <th>Suma</th>
                <th></th>
                <th></th>
                </thead>
                <tbody>

                    <% for (Krepselis k : krepseliaiDone) {%>
                    <tr>

                        <td>
                            <%=(k.getId() != null)
                                    ? k.getId() : ""%>
                        </td>
                        <td>
                            <%=(k.getIvykdytas() != null) ? sdf.format(k.getIvykdytas())
                                    : ""%>
                        </td>
                        <td>
                            <%=(k.getSukurtas() != null)
                                    ? sdf.format(k.getSukurtas()) : ""%>
                        </td>
                        <td>
                            <%=sumos.get(k)%>                            
                        </td>
                        <td>                            
                        </td>
                        <td><a href="./account/showCart?krepselioId=<%=k.getId()%>"
                               class="text-secondary">Išsamiau</a></td>

                    </tr>
                    <%}%>

                </tbody>

            </table>

            <%}%>
        </div>
    </body>

</html>