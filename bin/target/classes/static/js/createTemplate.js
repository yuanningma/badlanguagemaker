let count = 1;

$(document).ready(() => {
    $("#newField").on('click', (event) => {
        count++;
        $("#last").before("<div class=\"form-group\"><label for=\"field" + count
        + "\" >Field</label><input id=\"field" + count + "\" type=\"text\" class=\"form-control\" aria-describedby=\"field"
        + count + "\"  placeholder=\"Enter field name\"></div>");
    });

    $("#newForm").on('click', (event) => {

        let commas = checkForCommas();

        if (commas) {
            alert("No commas allowed in field name");
        } else {
            let labels = "";
            for (let i=1; i<count+1; i++) {
                labels += $("#field" + i).val();
                labels += ";";
            }

            const postParameters = {fields: labels};
            // const postParameters = {fields: "field"};



    		$.post("/templates/create", postParameters, responseJSON => {
                // Show message that template was successfully created
                // console.log("posted");
                const responseObject = JSON.parse(responseJSON);
                alert(responseObject.message);
            });
        }
    });

    function checkForCommas() {
        for (let i=1; i<count+1; i++) {
            if ($("#field" + i).val().includes(",")) {
                return true;
            }
        }
        return false;
    }
});
