<#assign content>
    <link rel="stylesheet" href="/css/forms.css">
<h1 class="text-center"> Form </h1>


<!--<#list fields?keys as label>-->
<!--<div style="overflow-x:auto;">-->
<center>
<table  id="formTable" style={position:relative; top:-90px;}>
  <tr class="header" >
    <th>${label}</th>
    
  </tr>
  <tr>
  <td>${fields[label]}<td>
  </tr>
  </table>
  </center>
  </table>
<!--</#list>-->

<div>
<ul id="linklist" style="list-style-type:none">
`
<li><a href="/patients/${id}/forms" class="previous">&#10216; Patient Records</a></li>
</ul>
</div>

</#assign>
<#include "main.ftl">