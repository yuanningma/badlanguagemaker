$(document).ready(() => {

    $("#login").on('click', (event) => {

        // console.log($formName.val());
        // formElts = formElts.substring(0, formElts.length-1);
        const postParameters = {
          username:  document.getElementById("username").value,
          password: document.getElementById("password").value,
        //  path: document.getElementById("path").value
        };

console.log("hi1");
        // const postParameters = {fields: "field"};
        $.post("/login", postParameters, responseJSON=> {
                    //console.log("hi");
                    console.log(JSON.parse(responseJSON));
              const responseObject = JSON.parse(responseJSON);


        });
    });
});
