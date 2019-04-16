<#assign content>

<h1 class="text-center"> Form </h1>

<#list fields?keys as label>
	<div class="text-center">
		<h2 class=“display: inline-block”>${label}</br></h2>
		<h5 class=“display: inline-block”>${fields[label]}</br></h5>
	</div>
</#list>

</#assign>
<#include "main.ftl">