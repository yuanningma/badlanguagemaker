<#assign content>
    <link rel="stylesheet" href="/css/pastforms.css">
<h1 class="text-center"> Patient Records </h1>

<div id="forms" class="list-group w-50 mx-auto">
    <#list formMap ?keys as formId>
    <a href="/patients/${id}/forms/${formId}" class="list-group-item list-group-item-action">${formMap[formId]}</a>
    </#list>
  <#--<a href="/patients/1/forms/1" class="list-group-item list-group-item-action">Form 1</a>-->
  <#--<a href="/patients/1/forms/2" class="list-group-item list-group-item-action">Form 2</a>-->
  <#--<a href="/imaging" class="list-group-item list-group-item-action">Chest X-Ray</a>-->
</div>

<div>
<br>
		<center>
    	<div class="dropdown">
			  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    New Form from Template
			  </button>
			  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			  	<#list nameToId ?keys as name>
			  	<a class="dropdown-item" href="/patients/${id}/forms/${nameToId[name]}/new">${name}</a>
			    <#--<a class="dropdown-item" href="/patients/1/forms/1/new">Action</a>-->
			    <#--<a class="dropdown-item" href="#">Another action</a>-->
			    <#--<a class="dropdown-item" href="#">Something else here</a>-->
			    </#list>
			  </div>
			  <#--<button onclick="window.location.href='/templates/new'" class="btn btn-primary">Create Template</button>-->
			</div>
	
    	
    </center>
</div>
<br>
<br>
<br>
<div>
<ul id="linklist" style="list-style-type:none">
<li><a href="/patients/${id}/profile" class="previous">&#10216; View Profile</a></li>
</ul>
</div>

</#assign>
<#include "main.ftl">
