<#assign content>

    <link rel="stylesheet" href="/css/DD.css">

  <center>
<h2> My Dashboard<h2>
</center>
<!--<img src="logo.png" 
style="z-index: -50;"  width="200" height="200">-->

<body>
   <center>



   
<input type="text" id="myInput" placeholder="Search";>
<div style="overflow-x:auto;">
<table  id="myTable" style={position:relative; top:-90px;}>

  <tr class="header" >

    <th style="width:20%;">Name</th>
    <th style="width:10%;">Age</th>
        <th style="width:30%;">Address</th>
    <th style="width:10%;">ID</th>
      <#list patientsFN as fn>  
  </tr>
  <tr onclick="document.location='/patients/${fn[1]}/profile'">

    <td>${fn[0]}</td>
        <td>${fn[0]}</td>
            <td>${fn[0]}</td>
                <td>${fn[1]}</td>
     </tr>
                      </#list>



</table>

</div>

</center>
</body
</#assign>
<#include "main.ftl">



<!-- DO NOT TOUCH BELOW HERE -->
