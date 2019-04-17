$(document).ready(() => {
    // const $dropdown = $("#dropdown");
    // console.log("ready");
    $("#newField").on('click', event => {
        $("#last").before("<div class=\"form-group\"><label for=\"field\">Field</label><input type=\"text\" class=\"form-control\" aria-describedby=\"field\" placeholder=\"Enter field name\"></div>");
    });
});
