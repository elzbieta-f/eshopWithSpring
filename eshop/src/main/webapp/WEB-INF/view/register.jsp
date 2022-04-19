<%-- 
    Document   : register
    Created on : 7 Mar 2022, 22:10:33
    Author     : elzbi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>

<head>
    <title>Naujas vartotojas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
    <div class="container">
        <div class="p-3 text-secondary">Prisistatyk</div>
        <form action="./register" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <div class="input-group mb-3"><span class="input-group-text" id="vardas">Vardas:</span> <input type="text"
                    name="username" class="form-control"></div>
            <div class="input-group mb-3"> <span class="input-group-text" id="slaptazodis">Slaptažodis: </span><input
                    type="password" name="password" class="form-control"></div>
            <div class="input-group mb-3"> <span class="input-group-text" id="slaptazodis">Pakartok slaptažodį:
                </span><input type="password" name="password2" class="form-control"></div>

            <input class="btn btn-primary mb-3" type="submit" value="Register">
            <a href="./">Cancel</a>
        </form>
    </div>
</body>

</html>