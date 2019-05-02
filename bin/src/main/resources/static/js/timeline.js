$(document).ready(() => {
    const $myTimeline = $("#myTimeline");

  $('#searchTL').on('click', (event)=> {


    const postParameters = {
      timeline: document.getElementById("myTimeline").value
    }


    $.post("/relevance", postParameters, responseJSON => {
        // TODO: Parse the JSON response into a JavaScript object.

        const responseObject = JSON.parse(responseJSON);
        let forms=responseObject.forms;

        var i;
        var side;



        for (let i = 0, len = forms.length; i < len; i++) {

          if(i%2==0){
            side="'container left'";
          }else{
            side="'container right'";
          }

      console.log(forms[i]);

          $('#last').before(
            "<div class="+side+">"+
      "<div class="+"content"+">"+
        "<h2>2011</h2>"+
        "<p>graduated</p>"+
        "</div></div>"
            )
            }
});
})
})
