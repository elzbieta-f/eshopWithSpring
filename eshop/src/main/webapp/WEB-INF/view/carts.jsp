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
<% List<Krepselis> krepseliai = (List) request.getAttribute("carts");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    Map<Integer, BigDecimal> sumos = (Map) request.getAttribute("krepseliuSumos");
    BigDecimal total=(BigDecimal)request.getAttribute("total");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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
        <title>Krepšeliai</title>
    </head>

    <body >
        <div class="container mb-3">
            <h4>Sveiki, <%=v.getVardas()%>! Čia peržiūrėsite krepšelių statistiką</h4>
            <p><a href="./">Į pagrindinį puslapį</a></p>
            <p><a href="./account/change">Keisti slaptažodį</a></p>

            <a href="/eshop/stats/products" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Prekių statistika</div></a>
            
            <form>
                <div class="mb-3">
                    <label for="startDate" class="form-label">Nuo</label>
                    <input type="date" class="form-control" id="startDate" name="startDate">    
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">Iki</label>
                    <input type="date" class="form-control" id="endDate" name="endDate">
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="isCompleted" name="isCompleted">
                    <label class="form-check-label" for="isCompleted">Įvykdytas</label>
                </div>
                <button type="submit" class="btn btn-primary">Rodyti krepšelius</button>
            </form>
            <% if (krepseliai.isEmpty()) {%>
            <h4>Nėra užbaigtų krepšelių</h4>
            <%} else {%>
            <h3>Krepšelių sąrašas</h3>
            <h3 class="text-end">Krepšelių kiekis: <%=krepseliai.size()%> <span class="mx-3"></span>Visų krepšelių suma: <%=total.toPlainString()%></h3>
            <table class="table table-striped table-hover">
                <thead class="table-light">

                <th>Krepšelio ID</th>
                <th>Įvykdytas</th>
                <th>Sukurtas</th>
                <th>Suma</th>
                <th>Vartotojas</th>
                <th></th>
                </thead>
                <tbody>

                    <% for (Krepselis k : krepseliai) {%>
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
                            <%=sumos.get(k.getId())%>                            
                        </td>
                        <td>
                            <%=k.getVartotojasId()!=null? k.getVartotojasId().getVardas():"Neprisijungęs vartotojas"%>
                        </td>


                    </tr>
                    <%}%>

                </tbody>

            </table>

            <%}%>
        </div>
    </body>

</html>