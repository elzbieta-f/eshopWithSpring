<%-- Document : index Created on : 14 Mar 2022, 21:03:20 Author : elzbi --%>

<%@page import="lt.bit.eshop.data.Role"%>
<%@page import="lt.bit.eshop.data.Vartotojas"%>
<%@page import="lt.bit.eshop.data.Krepselis" %>
<%@page import="lt.bit.eshop.data.Preke" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% List<Role> list = (List) request.getAttribute("roleList");
    Vartotojas v = (Vartotojas) request.getAttribute("admin");
    Role role = (Role) request.getAttribute("role");
    String edit = request.getParameter("edit");
    String editInfo = "";
    if (edit != null) {
        if (edit.equals("fail")) {
            editInfo = "editFail('Nepavyko išsaugoti rolės')";
        } else if (edit.equals("ok")) {
            editInfo = "editSuccess('Rolė išsaugota')";
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



        </p>
        <p class="text-end">  <a href="./stats">Pirkimų statistika</a></p>
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
        <div class="row">
            <a href="./roles?nauja=true" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-primary">+NAUJA ROLĖ</div></a>
            <a href="./roles" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rodyti visas roles</div></a>               


        </div>
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <th>Rolė</th>
            <th>Aprašymas</th> 
            <th>Vartotojų sąrašas</th>
            <th>Edit</th>
            <th>Delete</th>
            </thead>
            <tbody>

                <%for (Role r : list) {
                        if (r.equals(role)) {%>

                <tr> 
            <form method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <input type="hidden" name="roleId" value="<%=r.getId()%>">


                <td>
                    <input type="text" name="name" class="form-control" value="<%=(r.getRolesName() != null) ? r.getRolesName() : ""%>">
                </td>
                <td>
                    <input type="text" name="desc" class="form-control" value="<%=(r.getDesc() != null) ? r.getDesc() : ""%>">
                </td>

                <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                <td><a href="./roles" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                <td></td>
            </form>
            </tr>
            <%   } else {%>
            <tr>
                <td> <%= r.getRolesName()%>
                </td>
                <td>
                    <%=r.getDesc() != null ? r.getDesc() : ""%>
                </td>

                <td><a href="./myRoles/vartotojai?roleId=<%=r.getId()%>" class="text-primary link mb-3"><div class="btn btn-outline-primary">Vartotojai</div></a></td>

                <td>
                    <button onclick="edit('./roles?roleId=<%=r.getId()%>')" class="text-info" > <i class="fas fa-edit"></i></button>

                </td>

                <td>
                    <button onclick="toDelete('./roles/delete?roleId=<%=r.getId()%>')" class="text-danger" ><i class="fas fa-trash-alt"></i></button>
                </td>

            </tr>
            <%}%>
            <%}
                if (Boolean.TRUE.equals(nauja)) {%>
            <tr> 
            <form method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <td>       
                    <input type="text" name="name" class="form-control">
                </td>
                <td>
                    <input type="text" name="desc" class="form-control">
                </td>                
                <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                <td><a href="./roles" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                <td></td>
            </form>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</body>

</html>