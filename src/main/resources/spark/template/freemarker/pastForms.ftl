<#assign content>
    <link rel="stylesheet" href="/css/pastforms.css">
<h1 class="text-center"> Patient Records </h1>

<div id="forms" class="list-group w-50 mx-auto">
  <a href="/patients/1/forms/1" class="list-group-item list-group-item-action">Form 1</a>
  <a href="/patients/1/forms/2" class="list-group-item list-group-item-action">Form 2</a>
  <a href="/imaging" class="list-group-item list-group-item-action">Chest X-Ray</a>
</div>

<div>
<br>
		<center>
    	<button onclick="window.location.href='/forms/new'" role="button">New Form</button>
    </center>
</div>

<div>
<ul id="linklist" style="list-style-type:none">
<li><a href="/patients/:patientId/profile" class="previous">&#10216; View Profile</a></li>
<li><a href="/patients/:patientId/forms" class="previous">&#10216; Patient Records</a></li>
</ul>
</div>
</#assign>
<#include "main.ftl">
