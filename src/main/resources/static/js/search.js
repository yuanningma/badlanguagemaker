$(document).ready(() => {

  $('#myInput').on('keypress', (event)=> {
          if(event.which === 13){
              console.log("hi");

              const postParameters = {
                input:document.getElementById("myInput").value
              }

              $.post("/correct", postParameters, responseJSON => {
                  // TODO: Parse the JSON response into a JavaScript object.
                  const responseObject = JSON.parse(responseJSON);
                  word=responseObject.input;

                $suggestions.empty();

                        var i;
                for (let i = 0, len = responseObject.suggestions.length; i < len; i++) {
                  $suggestions.append("<option>"+responseObject.suggestions[i]+"</option>");
  }
  });
          }
  });
});
