<#assign content>
<form>
    <center>
<div class = "center-block">
<h1> User Login </h1>

    <div class="form-group" style="width: 30rem;">
        <label for="username">Email address</label>
        <input type="username" class="form-control" id="username" placeholder="Enter email" size = "1">
    </div>
    <div class="form-group" style="width: 30rem;">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Password" size = "35">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</div>
    </center>
</form>
</#assign>
<#include "main.ftl">