<%-- 
    Document   : admin
    Created on : 23 Feb 2022, 18:40:41
    Author     : elzbi
--%>

<%@page import="lt.bit.eshop.data.Role"%>
<%@page import="java.util.Set"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vartotojas admin = (Vartotojas) request.getAttribute("admin");
    Set<Vartotojas> list = (Set) request.getAttribute("users");
    List<Vartotojas> allOtherUsers = (List) request.getAttribute("allOtherUsers");
    Role r = (Role) request.getAttribute("role");
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    if (v != null && admin != null && v.equals(admin)) {
        v = admin;
    }
    if (v == null && admin != null) {
        v = admin;
    }
    Boolean naujas = (Boolean) request.getAttribute("naujas");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/9a66ff09af.js" crossorigin="anonymous"></script>
        <style>
            .link {
                text-decoration: none;
            }
            .mygtukas{
                width: 150px;
            }
            .h-100 {
                height: 100px;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <p class="text-end">
                Prisijungęs kaip <%=v.getVardas()%> <a class="mygtukas text-secondary" href="../account"> 
                    <span class="btn btn-outline-secondary">Mano Paskyra</span>
                </a>
            <form action="../logout" method="POST" class="text-end">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input name="submit" type="submit" value="logout" />
            </form> 
        </p> 
        <p class="text-end">  <a href="../myRoles">Visos mano grupės</a></p>
        <%if (admin != null) { %> 
        <p class="text-end">  <a href="../roles">Visų grupių sąrašas</a></p>
         <p class="text-end">  <a href="./stats">Pirkimų statistika</a></p>
        <%}%>         
        <h1>Vartotojų sąrašas grupėje <%=r.getRolesName()%></h1>

        <%if (list == null) {%>
        <h2>Sąrašas tuščias</h2>
        <%} else {%>
        <form class="p-3">
            <div class="row">              
                <div class="mb-3 col-6">
                    <input class="form-control" id="filter" class="form-text" name="filter">
                </div>
                <div class="mb-3 col-2"> <input class="btn btn-secondary text-light" type="submit" value="Ieškoti">
                </div>
            </div>
        </form>
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <th>ID</th>
            <th>Vardas</th>            
            <th>Teisių tvarkymas</th>                
                <%=admin != null ? "<th>Trinti vartotoją</th>" : ""%>
            </thead>
            <tbody>

                <%
                    for (Vartotojas var : list) {%>
                <tr>
                    <td>
                        <%=var.getId()%>
                    </td>
                    <td>
                        <%=(var.getVardas() != null) ? var.getVardas() : ""%>
                    </td>

                    <td><a href="remove?roleId=<%=r.getId()%>&userId=<%=var.getId()%>" class="text-success link"><%=!var.equals(v) && v.equals(admin) ? "Šalinti iš grupės" : (var.equals(v) ? "Atsisakyti grupės teisių" : "")%></a></td>                       
                    <% if (admin != null && !admin.equals(var)) {%><td><a href="./admin/delete?userId=<%=var.getId()%>" class="text-danger"> Trinti vartototoją</a></td><%}%>
                </tr>

                <%
                    }%>
            </tbody>   
        </table>
        <%
            }

        %>
        <a href="../myRoles/vartotojai?roleId=<%=r.getId()%>&naujas=true" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-primary">+ Naujas Vartotojas</div></a>
        <%if (Boolean.TRUE.equals(naujas)) {%>
        <div class="input-group mb-3">
            <form action="../myRoles/addUser" method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="roleId" value="<%=r.getId()%>">
                <label for="userId" class="input-group-text">Iš sąrašo pasirinkite vartotoją, kuriam norite suteikti <%=r.getRolesName()%> teises:</label>

                <select id="userId" name="userId" class="form-select">
                    <%for (Vartotojas allOtherUser : allOtherUsers) {
                    %>
                    <option value="<%=allOtherUser.getId()%>"><%=allOtherUser.getVardas()%></option>
                    <%
                        }
                    %>
                </select>    
                <input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                <a href="./vartotojai?roleId=<%=r.getId()%>" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>

            </form>
        </div>
        <%
            }

        %>  
    </div>
</body>
</html>
