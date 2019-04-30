$(document).ready(() => {

  $('#myInput').on('keypress', (event)=> {
          if(event.which === 13){
              console.log("hi");

              const postParameters = {
                search:document.getElementById("myInput").value
              }

              $.post("/searchDD", postParameters, responseJSON => {
                  // TODO: Parse the JSON response into a JavaScript object.
    console.log("hi2");
              console.log(postParameters);

              const responseObject = JSON.parse(responseJSON);
              patients=responseObject.myInput;

            // $myTable.empty();
            patients.forEach(function (patient) {
        $myTable.append(
          "<tr><td>" + patient[0] +
           "</td><td>" + patient[1]+
           "</td><td>" +patient[2]+
           "</td><td>" +patient[3]+
           "</td></tr>"
        )});
      })
    }
  })
});
