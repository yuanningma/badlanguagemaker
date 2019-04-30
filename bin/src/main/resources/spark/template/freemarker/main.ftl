<!-- DOCTYPE is an instruction to the browser about what version of HTML
the page is written in -->
<!DOCTYPE html>
<html>

<!-- Head contains meta data and imports -->
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/normalize.css">
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/html5bp.css">
    <link rel="stylesheet" href="/css/navbar.css">
    <!--<link rel="stylesheet" href="/css/list.css">-->
    <!-- define character set in use -->
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Title appears in tab -->
    <title>${title}</title>

</head>



<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
<input type="text" id="searchPage" placeholder="Search Page">
  <a class="text-black" href="/home">Login / Logout</a>
<!-- <a class="text-black" href="/patients/1/forms">forms</a>-->
  <a class="text-black" href="/Dashboard/1">My Dashboard</a>
 <!--<a class="text-black" href="/imaging">xray</a>-->
  <!--<a class="text-black" href="/timeline">timeline</a>-->
    <!--<a class="text-black" href="/patients/1/profile">Profile</a>-->
</div>

<span style="font-size:30px;cursor:pointer; color:Grey; z-index:2;" onclick="openNav()">&#9776;</span>

<script>
function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}
</script>




<script src="/js/jquery-3.1.1.js"></script>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
${content}

		<!--<script src="js/graph.js"></script>-->		

	<script src="/js/search.js"></script>		
</div>


</body>



<!-- Body contains the page content -->
		

<!-- Make sure to close all your tags! -->
</html>