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
      repro: document.getElementById("Reproductive").checked,
//      array: arr
<<<<<<< HEAD
      startDate: document.getElementById("date1").value,
      endDate: document.getElementById("date2").value
=======
>>>>>>> 2ff2514de710d64b1e11d8a7a64dd57d47bfcbd5

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
<<<<<<< HEAD
      let todispl = false;
      if (rel > 70) {
          colly="redcontent";
          todispl = true;
      } else if (rel > 30) {
          colly="yellowcontent";
          todispl = true;
      } else if (rel >= 0){
          colly="greencontent";
          todispl = true;
      }
      let ray = forms[i].timeForFront;
      console.log("VALS I IS: " + vals[i]);
      if (todispl) {
          $('#last').before(
            "<div class="+side+">"+
      "<div class="+colly+">"+
        "<a href='/patients/"+id+"/forms/"+forms[i].templateId+"''>"+forms[i].templateName+"<br>"+vals[i]+"%<br>"+ray+"<br></a>"+"</div></div>"
            )
      } else {
          $('#last').before(
            "<div class="+side+">"+
      "<div class="+colly+">"+
        "<a href='/patients/"+id+"/forms/"+forms[i].templateId+"''>"+forms[i].templateName+"<br>"+ray+"<br></a>"+"</div></div>"
=======
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
>>>>>>> 2ff2514de710d64b1e11d8a7a64dd57d47bfcbd5
            )
      }
    //       $('#last').before(
    //         "<div class="+side+">"+
    //   "<div class="+colly+">"+
    //     "<a href='/patients/"+id+"/forms/"+forms[i].templateId+"''>"+forms[i].templateName+"<br>"+vals[i]+"%<br>"+ray+"<br></a>"+"</div></div>"
    //         )
            }

//        $myTimeline.empty();
});
})
})
