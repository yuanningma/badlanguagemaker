<#assign content>
    <link rel="stylesheet" href="/css/newForm.css">
<h1 class="text-center"> New Template </h1>
<div id="message"></div>



<form>
<center>
<input id="formName" type="text" class="form-control" aria-describedby="field1" placeholder="Enter form name">
  </center>
  <div class="form-group">
    <label id="label1" for="field1">Field</label>
    <input id="field1" type="text" class="form-control" aria-describedby="field1" placeholder="Enter field name">
  </div>

  <div id="last"></div>

  <button id="newField" type="button" class="btn btn-primary">New Field</button>
  <button id="newForm" type="button" class="btn btn-primary">Create Form</button>
</form>

<#--<ul id="linklist" style="list-style-type:none">
<li><a href="/patients/${patId}/profile" class="previous">&#10216; View Profile</a><br></li>
<li><a href="/patients/${patId}/forms" class="previous">&#10216; Patient Records</a></li>
</ul>-->

<script src="/js/createTemplate.js"></script>
</#assign>
<#include "main.ftl">