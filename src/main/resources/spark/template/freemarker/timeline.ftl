<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<#assign content>
      <link rel="stylesheet" href="/css/timeline.css">

</head>

<body>
   <h4>Patient Timeline</text></h4>
<div class="timeline" id="myTimeline" name="timeline">
  <div id="last"></div>
  </div>
  
</div>

<form id="checklist">
<p> Filter by</p>
   <input id="searchTimeline" type="text" placeholder="Search">
   <br><br>
   <p>Condition Type:</p>
   <input type="checkbox" name="Cardiovascular" value="Cardiovascular">
           <label for="Cardiovascular">Cardiovascular</label>
   <br>
  <input type="checkbox" name="Respiratory" value="Respiratory">
           <label for="Respiratory">Respiratory</label>
   <br>
     <input type="checkbox" name="Neurology" value="Neurology">
           <label for="Neurology">Neurology</label>
   <br>
     <input type="checkbox" name="Endocrine" value="Endocrine">
           <label for="Endocrine">Endocrine</label>
   <br>
   <input type="checkbox" name="Renal" value="Renal">
           <label for="Renal">Renal</label>
   <br>
   <input type="checkbox" name="Hepato / GI" value="Hepato / GI">
           <label for="Hepato / GI">Hepato / GI</label>
           <br>
   <input type="checkbox" name="Pyschiatric" value="Pyschiatric">
           <label for="Pyschiatric">Pyschiatric</label>
   <br>
    <input type="checkbox" name="Orthopedic" value="Orthopedic">
           <label for="Orthopedic">Orthopedic</label>
   <br>
     <input type="checkbox" name="Reproductive" value="Reproductive">
           <label for="Reproductive">Reproductive</label>
   <br>
   <br><br>
    <p>Time Span:</p>
    <input type="text" id="date1" placeholder="mm/dd/yy"><br>
    <label for="date1">to</label><br>
 <input type="text" id="date2" placeholder="mm/dd/yy"><br><br>
     <p>Medical History:</p>
     <input type="checkbox" name="Hospitilizations" value="Hospitilizations">
           <label for="Hospitilizations">Hospitilizations</label>
   <br>
     <input type="checkbox" name="Exams" value="Exams">
           <label for="Exams">Exams</label>
   <br>
   <input type="checkbox" name="Additional Treatments" value="Additional Treatments">
           <label for="Additional Treatments">Additional Treatments</label>
   <br>
         <input type="checkbox" name="Physical Therapy" value="Physical Therapy">
           <label for="Physical Therapy">Physical Therapy</label>
   <br>
      <input type="checkbox" name="Medications" value="Medications">
           <label for="Medications">Medications</label>
   <br><br>
      <br><br>
         <br><br>
<button id="searchTL" type="button" class="btn btn-primary">Search</button>
   <script src="/js/timeline.js"></script>
</form>
<br>
<br>
<br>
<br>
<ul id="xraylinklist" style="list-style-type:none">
<li><a href="/patients/${id1}/profile" class="previous">&#10216; View Profile</a><br></li>
<li><a href="/patients/${id1}/forms" class="previous">&#10216; Patient Records</a></li>
</ul>
</body>
</#assign>
<#include "main.ftl">
</html>

