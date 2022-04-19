<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="java.util.Set"%>
<%@page import="lt.bit.eshop.data.Role"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% Set<Role> list = (Set) request.getAttribute("roleList");
Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
Vartotojas admin = (Vartotojas) request.getAttribute("admin");
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

    <body %>">
        <div class="container">
            <p class="text-end"> 

                Prisijungęs kaip <%=v.getVardas()%> <a class="mygtukas text-secondary" href="./account"> 
                    <span class="btn btn-outline-secondary">Mano Paskyra</span>
                </a>
            <form action="./logout" method="POST" class="text-end">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input name="submit" type="submit" value="logout" />
            </form> 
           
        <%if (admin != null) { %> 
        <p class="text-end">  <a href="./roles">Visų grupių sąrašas</a></p>
         <p class="text-end">  <a href="./stats">Pirkimų statistika</a></p>
        <%}%> 

        </p>

        <form class="p-3">
            <div class="row">  
                <div class="col-6">
                    <input class="form-control" id="filter" class="form-text" name="filter">
                </div>
                <div class="col-6"> <input class="btn btn-secondary text-light" type="submit" value="Ieškoti">
                    <a href="./" class="text-secondary link mb-3 col-1 h-100 p-3">                      
                    </a>
                </div> 

            </div>
        </form>
        
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <th>Rolė</th>
            <th>Aprašymas</th> 
            <th>Vartotojų sąrašas</th>
            <th>Atsisakyti rolės</th>            
            </thead>
            <tbody>

                <%for (Role r : list) {
                        %>
            
            <tr>
                <td> <%= r.getRolesName()%>
                </td>
                <td>
                    <%=r.getDesc()!=null?r.getDesc():""%>
                </td>
                
                <td><a href="myRoles/vartotojai?roleId=<%=r.getId()%>" class="text-primary link mb-3"><div class="btn btn-outline-primary">Vartotojai</div></a></td>
                          

                <td>
                    <button onclick="toDelete('./myRoles/remove?roleId=<%=r.getId()%>&userId=<%=v.getId()%>')" class="text-danger" ><i class="fas fa-trash-alt"></i> Atsisakyti </button>
                </td>

            </tr>
            <%}
               %>

            </tbody>
        </table>
    </div>
</body>

</html>