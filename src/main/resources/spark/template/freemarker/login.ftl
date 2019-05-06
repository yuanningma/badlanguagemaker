<#assign content>
    <link rel="stylesheet" href="/css/login.css">
<html class="login">
<form method="POST" action="/validate">
    <center>
<div class = "center-block">

<img id="logo" src="logo.png" 
style="position:relative; z-index: -50;"  width="300" height="300">
<h1> User Login </h1>

    <div class="form-group" style="width: 30rem;">
        <label for="username">User Name</label>
        <input type="username" class="form-control" id="username" name = "username"  size = "1">
    </div>
    <div class="form-group" style="width: 30rem;">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name = "password"  size = "35">
    </div>
    ${message}
    <button id="login" type="button" class="btn btn-primary" onclick="window.location.href = 'http://localhost:4567/Dashboard/1';">Submit</button>
    <!--<button type="submit" class="btn btn-primary">Submit</button>-->
</div>
    </center>


</form>

    <script src="/js/login.js"></script>
</#assign>
<#include "main.ftl">