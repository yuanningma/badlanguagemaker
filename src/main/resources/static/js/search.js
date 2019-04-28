$(document).ready(() => {
console.log("jj");
  $("#myInput").keyup(event => {
    var keycode = (e.keyCode ? e.keyCode : e.which);
    if(keycode=='13'){
console.log("hi");
    }
              // const postParameters = {
              //   actor2:document.getElementById("actorB").value,
              //     database:document.getElementById("databaseID").value,
              // }
              // $.post("/correct3", postParameters, responseJSON => {
              //     // TODO: Parse the JSON response into a JavaScript object.
              //     const responseObject = JSON.parse(responseJSON);
              //     word=responseObject.actor2;
              //
              //   $suggestions2.empty();
              //           var i;
              //   for (let i = 0, len = responseObject.suggestions2.length; i < len; i++) {
              //     $suggestions2.append("<option>"+responseObject.suggestions2[i]+"</option>");
  }
  });
});
}
