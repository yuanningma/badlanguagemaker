let count = 1;

$(document).ready(() => {
    $("#createField").on('click', (event) => {
        count++;
        $("#last").before("<div class=\"form-group\"><label id=\"label" + count + "\" for=\"field" + count
        + "\" >Field</label><input id=\"field" + count + "\" type=\"text\" class=\"form-control\" aria-describedby=\"field"
        + count + "\"  placeholder=\"Enter field name\"></div>");
    });

    $("#delField").on('click', (event) => {
        if (count > 1) {
            document.getElementById("label" + count).remove();
            document.getElementById("field" + count).remove();
            count--;
        }
    });


    $("#saveForm").on('click', (event) => {

        let commas = checkForCommas();
        let blanks = checkForBlanks();

        if (commas) {
            alert("No commas allowed in field name");
        } else if (blanks) {
            alert("No blank fields allowed");
        } else {
            let labels = "";
            for (let i=1; i<count+1; i++) {
                labels += $("#field" + i).val();
                labels += ",";
            }

            const postCheckParameters = {
                name:document.getElementById("formName").value,
                fields: labels};

            const postParameters = {
                name:document.getElementById("formName").value,
                fields: labels};
            // const postParameters = {fields: "field"};

            $.post("/templates/check", postCheckParameters, responseJSON => {
                // Show message that template was successfully created
                // console.log("posted");
                const responseObject = JSON.parse(responseJSON);
                if (responseObject.similar === true) {
                    let toPost = confirm("Similar template exists with fields: " + responseObject.fields + "\nAre you sure you want to save this template?");
                    if (toPost) {
                        createTemplate(postParameters);
                    }
                } else {
                    createTemplate(postParameters);
                }
            });
        }
    });

    function createTemplate(postParameters) {
        $.post("/templates/create", postParameters, responseJSON => {
            // Show message that template was successfully created
            // console.log("posted");
            const responseObject = JSON.parse(responseJSON);
            alert(responseObject.message);
        });
    }

    function checkForCommas() {
        for (let i=1; i<count+1; i++) {
            if ($("#field" + i).val().includes(",")) {
                return true;
            }
        }
        return false;
    }

    function checkForBlanks() {
        for (let i=1; i<count+1; i++) {
            if ($("#field" + i).val() === "") {
                return true;
            }
        }
        return false;
    }
});
