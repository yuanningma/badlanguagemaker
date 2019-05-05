$patientId = $("#patientId");
$formName = $("#formName")

$(document).ready(() => {

    $("#saveButton").on('click', (event) => {

        let commas = checkForCommas();

        if (commas) {
            alert("No commas allowed in field value");
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
        $( "#saveForm :input" ).each(function(){
            if ($(this).val().includes(',')) {
                return true;
            }

        });
        return false;
    }
});
