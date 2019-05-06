$(document).ready(() => {

    const $myTable = $("#myTable");

  $('#myInput').on('keypress', (event)=> {
          if(event.which === 13){


              const postParameters = {
                search: document.getElementById("myInput").value
              }

              $.post("/searchDD", postParameters, responseJSON => {
                  // TODO: Parse the JSON response into a JavaScript object.

              const responseObject = JSON.parse(responseJSON);
              let patients=responseObject.patients;


    $myTable.find("tr:gt(0)").remove();
<<<<<<< HEAD

=======
<<<<<<< HEAD

=======
    
>>>>>>> 8c80bc4710675195311fd07d9a5a3b70f94a3bd2
>>>>>>> 02c73570142fab65f40119e58682ef270708675e
    var i;
    for (let i = 0, len = patients.length; i < len; i++) {
      $myTable.append(
        "<tr onclick="+"document.location='/patients/"+patients[i][3]+"/profile'"+"><td>"
        + patients[i][0] +
         "</td><td>" + patients[i][1]+
         "</td><td>" +patients[i][2]+
         "</td><td>" +patients[i][3]+
         "</td></tr>"
        )
        }
      })
    }
  })
});
