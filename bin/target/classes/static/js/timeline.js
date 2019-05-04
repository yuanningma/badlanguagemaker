$(document).ready(() => {
    const $myTimeline = $("#myTimeline");


  $('#searchTL').on('click', (event)=> {

//	  let arr = [document.getElementById("Cardiovascular"), document.getElementById("Respiratory")];
    const postParameters = {
      timeline: document.getElementById("myTimeline").value,
      search: document.getElementById("searchTimeline").value,
      cardio: document.getElementById("Cardiovascular").checked,
      respiro: document.getElementById("Respiratory").checked,
      neuro: document.getElementById("Neurology").checked,
      endo: document.getElementById("Endocrine").checked,
      reno: document.getElementById("Renal").checked,
      hepato: document.getElementById("Hepato").checked,
      psycho: document.getElementById("Psychiatric").checked,
      ortho: document.getElementById("Orthopedic").checked,
      repro: document.getElementById("Reproductive").checked
//      array: arr

    }
    $myTimeline.children("div").not(':last').remove();
    console.log("ohhithere");
    $.post("/relevance", postParameters, responseJSON => {

        // TODO: Parse the JSON response into a JavaScript object.
        const responseObject = JSON.parse(responseJSON);
        let forms=responseObject.forms;
        let id =responseObject.id;
        let vals = responseObject.vals;

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
      let rel = vals[i];
      let colly = "content";
      if (rel > 70) {
          colly="redcontent";
      } else if (rel > 30) {
          colly="yellowcontent";
      } else {
          colly="greencontent";
      }
      let ray = forms[i].timeForFront;

          $('#last').before(
            "<div class="+side+">"+
      "<div class="+colly+">"+
        "<a href='/patients/"+id+"/forms/"+forms[i].templateId+"''>"+forms[i].templateName+"<br>"+vals[i]+"%<br>"+ray+"<br></a>"+"</div></div>"
            )
            }

//        $myTimeline.empty();
});
})
})
