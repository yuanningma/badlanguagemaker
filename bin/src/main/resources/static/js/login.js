$(document).ready(() => {

    $("#login").on('click', (event) => {
        const postParameters = {
          username:  document.getElementById("username").value,
          password: document.getElementById("password").value,
        };
        $.post("/validate", postParameters, responseJSON=> {
              const responseObject = JSON.parse(responseJSON);
              let status = responseObject.status;

              if(status.equals("true")) {
                  console.log("HERE in true terriorty");
                  $.post("/Dashboard/:patientId", postParameters, responseObject => {
                    const successObject = JSON.parse(responseJSON);
                    const staffId = responseObject.staffId;

                  });
              } else {
                  console.log("HERE");
                  $("#message").value = "Login Failure";
                  $.post("/login", postParameters, responseObject => {
                     const failureObject = JSON.parse(responseJSON);
                  });
              }

        });
    });
});
