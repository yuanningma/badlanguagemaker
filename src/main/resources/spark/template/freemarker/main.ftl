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
    <!--<link rel="stylesheet" href="/css/list.css">-->
    <!-- define character set in use -->
    <meta charset="utf-8">

    <!-- Title appears in tab -->
    <title>${title}</title>

</head>

<div class="pos-f-t">
  <div class="collapse" id="navbarToggleExternalContent">
    <div class="bg-light p-4">
      <a class="text-black" href="/home">Home</a>
              <center>
                  <a class="text-black" href="/forms">forms</a>
              </center>
      <br>
            <a class="text-black" href="/DD">DoctorDashboard</a>
                  <center>
                             <a class="text-black" href="/imaging">xray</a>
                             </center>
            <br>
                  <a class="text-black" href="/login">login</a>
            
    </div>
  </div>
  <nav class="navbar navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
  </nav>
</div>

<!-- Body contains the page content -->
<body>
${content}

<p>${message}</p>

		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		<!--<script src="/js/jquery-3.1.1.js"></script>-->
		<#--<script src="js/graph.js"></script>-->
		<!--<script src="/js/xray.js"></script>-->
		<script src="/js/newForm.js"></script>

<!-- Make sure to close all your tags! -->
</html>