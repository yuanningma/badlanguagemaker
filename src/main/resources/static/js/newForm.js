let count = 1;

$(document).ready(() => {
    $("#newField").on('click', (event) => {
        count++;
        $("#last").before("<div class=\"form-group\"><label for=\"field" + count + "\" >Field</label><input id=\"field" + count + "\" type=\"text\" class=\"form-control\" aria-describedby=\"field" + count + "\"  placeholder=\"Enter field name\"></div>");
    });

    $("#newForm").on('click', (event) => {
<<<<<<< HEAD

        let labels = "";
        for (let i=1; i<count+1; i++) {
            labels += $("#field" + i).val();
            labels += ";";
        }

        const postParameters = {fields: labels};
        // const postParameters = {fields: "field"};
=======

        console.log("herebef");


        // let labels = [];
        // for (let i=1; i<count+1; i++) {
        //     labels.push($("#field" + i).html());
        // }
        // const postParameters = {fields: JSON.stringify(labels)};
        const postParameters = {fields: "field"};
>>>>>>> 5eb31d92a1a06616231f3ee1d793f4f91616d7ab



		$.post("/forms/create", postParameters, responseJSON => {
            // Show message that form was successfully created
            console.log("posted");
        });
    });
});
