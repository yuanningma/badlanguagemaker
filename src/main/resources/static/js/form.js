$(document).ready(() => {
    $.get("/forms/:formId", response => {
        const object = JSON.parse(response);
        console.log(response);
    });
});
