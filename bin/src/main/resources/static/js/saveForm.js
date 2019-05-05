$patientId = $("#patientId");
$formName = $("#formName")

$(document).ready(() => {

    $("#saveButton").on('click', (event) => {

        let commas = checkForCommas();
        let blanks = checkForBlanks();

        if (commas) {
            alert("No commas allowed in field value");
        } else if (blanks) {
            alert("No blank fields allowed");
        } else {
            let formElts = "";

            $( "#saveForm :input" ).each(function(){
                if (!($(this).attr('id') === "saveButton")) {
                    formElts += $(this).attr('id');
                    formElts += ",";
                    formElts += $(this).val();
                    formElts += ",";
                }

            });
            // console.log($formName.val());
            // formElts = formElts.substring(0, formElts.length-1);
            const postParameters = {fields: formElts, patientId: $patientId.html(), formName : $formName.val()};

            // const postParameters = {fields: "field"};
            $.post("/forms/save", postParameters, responseJSON => {
                // Show message that template was successfully created
                const responseObject = JSON.parse(responseJSON);
                alert(responseObject.message);
                // $("#message").html("<div class=\"alert alert-info\" role=\"alert\">" + responseObject.message + "</div>");

            });
        }
    });

    function checkForCommas() {
        let commas = false;
        $( "#saveForm :input" ).each(function(){
            if ($(this).val().includes(",")) {
                commas = true;
                return commas;
            }

        });
        return commas;
    }

    function checkForBlanks() {
        let blanks = false;
        $( "#saveForm :input" ).each(function(){
            if ($(this).val() === "") {
                console.log("here");
                console.log($(this).val());
                blanks = true;
                return blanks;
            }

        });
        return blanks;
    }
});
