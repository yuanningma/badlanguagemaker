$(document).ready(() => {
    const $myTimeline = $("#myTimeline");

  $('#searchTL').on('click', (event)=> {

    const postParameters = {
      timeline: document.getElementById("myTimeline").value
    }

    console.log("ohhithere");
    $.post("/relevance", postParameters, responseJSON => {
        // TODO: Parse the JSON response into a JavaScript object.
        const responseObject = JSON.parse(responseJSON);
        let forms=responseObject.forms;
        let id =responseObject.id;

        var i;
        var side;



        for (let i = 0, len = forms.length; i < len; i++) {

          if(i%2==0){
            side="'container left'";
          }else{
            side="'container right'";
          }
      console.log(forms[i].fields);
      console.log(forms[i].templateId);

          $('#last').before(
            "<div class="+side+">"+
      "<div class="+"content"+">"+
        "<a href='/patients/"+id+"/forms/"+forms[i].templateId+"''>"+forms[i].templateName+"</a>"+
        "</div></div>"
            )
            }
});
})
})
