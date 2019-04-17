<#assign content>

<h1 class="text-center"> New Form </h1>

<form>
  <div class="form-group">
    <label for="field1">Field 1</label>
    <input type="text" class="form-control" id="field1" aria-describedby="field1" placeholder="Enter field name">
  </div>
  <div id="last"></div>
  <button id="newField" type="submit" class="btn btn-primary">New Field</button>
  <button type="submit" class="btn btn-primary">Create Form</button>
</form>

</#assign>
<#include "main.ftl">