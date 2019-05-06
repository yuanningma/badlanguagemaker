<#assign content>
    <link rel="stylesheet" href="/css/newForm.css">
<h1 class="text-center"> New Form</h1>
<div style="display:none;" id="patientId">${patientId}</div>


<form id="saveForm">


    <center>
        <input id="formName" type="text" class="form-control" aria-describedby="field1" placeholder="Enter form name">
    </center>
<#--<center>
<input id="formName" type="text" class="form-control" aria-describedby="field1" placeholder="Enter form name">
</center>-->

  <#--<div class="form-group">-->
    <#--<label for="field1">Field</label>-->
    <#--<input id="field1" type="text" class="form-control" aria-describedby="field1" placeholder="Enter field name">-->
  <#--</div>-->
  
  <!-- Below list loop will create an input box for each label passed in from newFormHandler -->
  <#list labels as label>
  	<div class="form-group">
  		<label for="${label}">${label}</label>
    	<input id="${label}" type="text" class="form-control" aria-describedby="${label}" placeholder="Enter field value">
    </div>
  </#list>
  <center>
  <button id="saveButton" type="click" class="btn btn-primary">Save Form</button>
  </center>
</form>
<br>
<br>
<br>
<ul id="linklist" style="list-style-type:none">
<li><a href="/patients/${patientId}/profile" class="previous">&#10216; View Profile</a><br></li>
<li><a href="/patients/${patientId}/forms" class="previous">&#10216; Patient Records</a></li>
</ul>

<script src="/js/saveForm.js"></script>
</#assign>
<#include "main.ftl">