<#assign content>

    <link rel="stylesheet" href="/css/DD.css">

  <center>
<h2> My Dashboard<h2>
</center>
<!--<img src="logo.png" 
style="z-index: -50;"  width="200" height="200">-->

<body>
   <center>



   
<input type="text" id="myInput" name="search" placeholder="Search";>
<div style="overflow-x:auto;">
<table  id="myTable" style={position:relative; top:-90px;}>

  <tr class="header" >

    <th>First Name</th>
        <th>Middle Name</th>
                <th>Last Name</th>
     <!--<th style="width:10%;">Age</th>
    <th style="width:30%;">Address</th>-->
    <th>ID</th>
      <#list patientsFN as fn>  
  </tr>
  <tr onclick="document.location='/patients/${fn[3]}/profile'">

    <td>${fn[0]}</td>
        <td>${fn[1]}</td>
            <td>${fn[2]}</td>
            <!--<td>add me</td>
                <td>add me</td>-->
                <td>${fn[3]}</td>
     </tr>
                      </#list>



</table>

</div>

</center>
</body


</#assign>
<#include "main.ftl">



<!-- DO NOT TOUCH BELOW HERE -->
