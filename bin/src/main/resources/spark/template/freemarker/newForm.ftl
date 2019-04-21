<#assign content>

<h1 class="text-center"> New Form </h1>

<form>
  <div class="form-group">
    <label for="field1">Field</label>
    <input id="field1" type="text" class="form-control" aria-describedby="field1" placeholder="Enter field name">
  </div>
  <div id="last"></div>
  <button id="newField" type="button" class="btn btn-primary">New Field</button>
  <button id="newForm" type="submit" class="btn btn-primary">Create Form</button>
</form>

</#assign>
<#include "main.ftl">