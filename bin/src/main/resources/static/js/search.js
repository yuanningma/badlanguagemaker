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
            var i;
    for (let i = 0, len = patients.length; i < len; i++) {
      $myTable.append(

  // "<tr onclick="+"document.location='/patients/${"+patients[i][3]+"}/profile'"+"><td>"



        "<tr onclick="+"document.location='/patients/"+patients[i][3]+"/profile'"+"><td>"
        + patients[i][0] +
         "</td><td>" + patients[i][1]+
         "</td><td>" +patients[i][2]+
         "</td><td>" +patients[i][3]+
         "</td></tr>"
      )

    }


      //       patients.forEach(patient=>{
      //   $myTable.append(
      //     "<tr><td>" + patient[0] +
      //      "</td><td>" + patient[1]+
      //      "</td><td>" +patient[2]+
      //      "</td><td>" +patient[3]+
      //      "</td></tr>"
      //   )
      // }
  //  );
      })
    }
  })
});
