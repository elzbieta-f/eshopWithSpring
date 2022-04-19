<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="lt.bit.eshop.data.Role"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% 
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

    <body >
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
             <p class="text-end"> <a href="./admin">Tvarkyti prekes</a> </p>
             <p class="text-end"> <a href="./roles">Tvarkyti grupes</a> </p>
            <%}%>
            <p class="text-end"> <a href="./myRoles">Mano grupės</a> </p>
            <%}%> 
        </p>
        



        <div class="row">
                      
            <a href="/eshop/stats/carts" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Krepšelių statistika</div></a>
            <a href="/eshop/stats/products" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Prekių statistika</div></a>

        </div>
      
    </div>
</body>

</html>