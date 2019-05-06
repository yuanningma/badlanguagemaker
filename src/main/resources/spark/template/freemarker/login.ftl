<#assign content>
    <link rel="stylesheet" href="/css/login.css">
<html class="login">
<form>
    <center>
<div class = "center-block">

<img id="logo" src="logo.png" 
style="position:relative; z-index: -50;"  width="300" height="300">
<h1> User Login </h1>

    <div class="form-group" style="width: 30rem;">
        <label for="username">Email address</label>
        <input type="username" class="form-control" id="username" name = "username" placeholder="Enter email" size = "1">
    </div>
    <div class="form-group" style="width: 30rem;">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name = "password" placeholder="Password" size = "35">
    </div>

   
    <button id="login" type="button" class="btn btn-primary" onclick="window.location.href = '${path}';">Submit</button>
    <!--<button type="submit" class="btn btn-primary">Submit</button>-->
</div>
    </center>
    ${message}

</form>
</#assign>
<#include "main.ftl">