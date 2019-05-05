<#assign content>
    <link rel="stylesheet" href="/css/newForm.css">
<h1 class="text-center"> New Template </h1>
<div id="message"></div>



<form>
    <center>
<input type="text" id="formName" name = "formName"  class="form-control" aria-describedby="field1" placeholder="Enter form name">
    </center>
  <div class="form-group">
    <label id="label1" for="field1">Field</label>
    <input id="field1" type="text" class="form-control" aria-describedby="field1" placeholder="Enter field name">
  </div>

  <div id="last"></div>
  </form>
  
<div class="text-center">
	<div class="btn-group-vertical">
	  <button id="saveForm" type="button" class="btn btn-primary left:0">Create Form</button>
	  <button id="createField" type="button" class="btn btn-primary">New Field</button>
	  <button id="delField" type="button" class="btn btn-primary">Delete Field</button>
	</div>
</div>


<#--<ul id="linklist" style="list-style-type:none">
<li><a href="/patients/${patId}/profile" class="previous">&#10216; View Profile</a><br></li>
<li><a href="/patients/${patId}/forms" class="previous">&#10216; Patient Records</a></li>
</ul>-->

<script src="/js/createTemplate.js"></script>
</#assign>
<#include "main.ftl">