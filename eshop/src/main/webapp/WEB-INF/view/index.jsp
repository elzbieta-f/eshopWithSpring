<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="lt.bit.eshop.data.Role"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<Preke> list = (List) request.getAttribute("list");
    Krepselis k = (Krepselis) session.getAttribute("krepselis");
    Integer prekiuKiekis = (Integer) request.getAttribute("prekiuKiekis");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    String cartComplete = (String) request.getAttribute("cart");
    boolean isAdmin=false;
//    Vartotojas admin=(Vartotojas)request.getAttribute("admin");
    
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

    <body onload="<%=cartComplete != null && !cartComplete.trim().equals("") ? "cart('" + cartComplete + "')" : ""%>">
        <div class="container">
            <p class="text-end"> 
                <%if (v == null) {%>
                <a class="mygtukas text-secondary" href="./login"> 
                    <span class="btn btn-outline-secondary">Prisijungti</span>
                </a> 
                <a class="mygtukas text-secondary" href="./register"> 
                    <span class="btn btn-outline-secondary">Registruotis</span>
                </a>
                <%} else {%> 
                Prisijungęs kaip <%=v.getVardas()%> <a class="mygtukas text-secondary" href="./account"> 
                    <span class="btn btn-outline-secondary">Mano Paskyra</span>
                </a>
            <form action="./logout" method="POST" class="text-end">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input name="submit" type="submit" value="logout" />
            </form> 
                <%if (v.hasRole("Admin")) { %> 
            <p class="text-end"> <a href="./stats">Statistika</a> </p>
             <p class="text-end"> <a href="./admin">Tvarkyti prekes</a> </p>
             <p class="text-end"> <a href="./roles">Tvarkyti grupes</a> </p>
            <%}%>
            <p class="text-end"> <a href="./myRoles">Mano grupės</a> </p>
            <%}%> 
        </p>

        <p class="text-end"> <a class="mygtukas text-secondary" href="./cart"> 
                <span class="fs-2"><i class="fa-solid fa-cart-arrow-down"></i></span> <%=prekiuKiekis != null ? prekiuKiekis : "0"%>
            </a>
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
            <a href="./" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rodyti visas prekes</div></a>               
            <a href="./?sortPrice=desc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti nuo brangiausių</div></a>
            <a href="./?sortPrice=asc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti nuo pigiausių</div></a>

        </div>
        <div class="row">

            <%for (Preke p : list) {%>
            <div class="card col-3 m-2">
                <img src="./IMG/<%=p.getPaveiksl()%>" class="card-img-top" alt="pav">
                <div class="card-body">
                    <h5 class="card-title">
                        <%=p.getPavadinimas()%>
                    </h5>
                    <p class="card-text">
                        <%=p.getAprasymas()%>
                    </p>
                    <p class="card-text text-end fw-bold fs-4">
                        <%=p.getKaina()%> €
                    </p>
                </div>
                <div class="card-footer text-end align-bottom">
                    <%if (p.getKiekis() == 0) {%>
                    <p> Išparduota </p> <%} else {%>
                    <form action="./toCart" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" name="prekeId" value="<%=p.getId()%>">                            
                        <div class="input-group mb-3"><span class="input-group-text" id="kiekis">Kiekis:</span> 
                            <input type="number" name="kiekis" class="form-control" value="1" min="1"></div>
                        <input class="btn btn-primary mb-3" type="submit" value="Į krepšelį">
                    </form>
                    <%}%>
                </div>
            </div>
            <%}%>
        </div>

    </div>
</body>

</html>