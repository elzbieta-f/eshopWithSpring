<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<Preke> list = (List) request.getAttribute("list");
    Vartotojas v = (Vartotojas) request.getAttribute("admin");
    Preke preke = (Preke) request.getAttribute("preke");
    String edit = request.getParameter("edit");
    String editInfo = "";
    if (edit != null) {
        if (edit.equals("fail")) {
            editInfo = "editFail('Nepavyko išsaugoti prekės')";
        } else if (edit.equals("ok")) {
            editInfo = "editSuccess('Prekė išsaugota')";
        }
    }
    Boolean nauja = (Boolean) request.getAttribute("nauja");
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

    <body onload="<%=edit != null ? editInfo : ""%>">
        <div class="container">
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
            <a href="./admin?nauja=true" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-primary">+NAUJA PREKĖ</div></a>
            <a href="./" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rodyti visas prekes</div></a>               
            <a href="./admin?sortPrice=desc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti nuo brangiausių</div></a>
            <a href="./admin?sortPrice=asc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti nuo pigiausių</div></a>

        </div>
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <th>Preke</th>
            <th>Aprašymas</th>
            <th>Kaina vnt</th>
            <th>Kiekis</th>                
            <th></th>
            <th></th>
            </thead>
            <tbody>

                <%for (Preke p : list) {
                        if (p.equals(preke)) {%>

                <tr> 
            <form method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <input type="hidden" name="prekeId" value="<%=p.getId()%>">


                <td>
                    <input type="text" name="pavadinimas" class="form-control" value="<%=(p.getPavadinimas() != null) ? p.getPavadinimas() : ""%>">
                </td>
                <td>
                    <input type="text" name="aprasymas" class="form-control" value="<%=(p.getAprasymas() != null) ? p.getAprasymas() : ""%>">
                </td>
                <td>
                    <input type="number" name="kaina" class="form-control" value="<%=(p.getKaina() != null) ? p.getKaina() : ""%>" step=".01">
                </td>
                <td>
                    <input type="number" name="kiekis" class="form-control" value="<%=p.getKiekis()%>">
                </td>
                <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                <td><a href="./admin" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                <td></td>
            </form>
            </tr>
            <%   } else {%>
            <tr>
                <td> <%= p.getPavadinimas()%>
                </td>
                <td>
                    <%=p.getAprasymas()%>
                </td>
                <td>
                    <%=p.getKaina()%> €
                </td>
                <td>
                    <%=p.getKiekis()%>
                </td>
                <td>
                    <button onclick="editPreke('./admin?prekeId=<%=p.getId()%>')" class="text-info" > <i class="fas fa-edit"></i></button>

                </td>

                <td>
                    <button onclick="deletePreke('./admin/deletePreke?prekeId=<%=p.getId()%>')" class="text-danger" ><i class="fas fa-trash-alt"></i></button>
                </td>

            </tr>
            <%}%>
            <%}
                if (Boolean.TRUE.equals(nauja)) {%>
            <tr> 
            <form method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <td>       
                    <input type="text" name="pavadinimas" class="form-control">
                </td>
                <td>
                    <input type="text" name="aprasymas" class="form-control">
                </td>
                <td>
                    <input type="number" name="kaina" class="form-control" step=".01">
                </td>
                <td>
                    <input type="number" name="kiekis" class="form-control">
                </td>
                <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                <td><a href="./admin" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                <td></td>
            </form>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</body>

</html>