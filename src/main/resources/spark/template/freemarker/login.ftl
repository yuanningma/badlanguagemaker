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
    <#--TODO FILL IN CLIENT ID GETTING THIS AFTER WE GET WEB ADDRESS-->
<#--<script src="https://apis.google.com/js/platform.js" async defer></script>-->
    <#--<meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">-->
    <#--<div class="g-signin2" data-onsuccess="onSignIn"></div>-->
    <#--<script>-->
        <#--function onSignIn(googleUser) {-->
            <#--var profile = googleUser.getBasicProfile();-->
            <#--console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.-->
            <#--console.log('Name: ' + profile.getName());-->
            <#--console.log('Image URL: ' + profile.getImageUrl());-->
            <#--console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.-->
    <#--TODO: Post request to the back end-->
        <#--}-->
    <#--</script>-->

    <#--TODO: THE CODE BELOW DEMONSTRATES A SIGN OUT BUTTON--#>
    <#--<a href="#" onclick="signOut();">Sign out</a>-->
    <#--<script>-->
        <#--function signOut() {-->
            <#--var auth2 = gapi.auth2.getAuthInstance();-->
            <#--auth2.signOut().then(function () {-->
                <#--console.log('User signed out.');-->
            <#--});-->
        <#--}-->
    <#--</script>-->
</#assign>
<#include "main.ftl">