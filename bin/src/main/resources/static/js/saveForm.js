let count = 1;

$(document).ready(() => {

    $("#saveButton").on('click', (event) => {

        let formElts = "";

        $( "#saveForm :input" ).each(function(){
            formElts += $(this).attr('id');
            formElts += ";";
            formElts += $(this).val();
            formElts += ";";
        });
        formElts = formElts.substring(0, formElts.length-1);
        console.log(formElts);

        const postParameters = {fields: formElts};
        // const postParameters = {fields: "field"};



		$.post("/forms/save", postParameters, responseJSON => {
            // Show message that form was successfully created
            console.log("posted");
        });
    });
});
