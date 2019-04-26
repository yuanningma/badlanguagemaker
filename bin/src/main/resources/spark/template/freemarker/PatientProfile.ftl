<#assign content>

    <link rel="stylesheet" href="/css/PatientProfile.css">
    
    <center>
    <h2>Patient Profile</h2>
    </center>
<p>
Patient: Patient Name<br>
Age: 5432 <br>
Address: 1234 1234 Lane<br>
Patient ID: 1234<br></p>



<ul id="linklist" style="list-style-type:none">
<li><a href="/timeline" class="previous">&#10217; Patient Timeline</a></li>
<li><a href="/patients/${id}/forms" class="previous">&#10217; Patient Records</a></li>
</ul>


</#assign>
<#include "main.ftl">



<!-- DO NOT TOUCH BELOW HERE -->