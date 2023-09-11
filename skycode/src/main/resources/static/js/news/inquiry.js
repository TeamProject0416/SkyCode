function checkOnlyOne(element) {

    const checkboxes
        = document.getElementsByName("view");

    checkboxes.forEach((vw) => {
        vw.checked = false;
    })

    element.checked = true;
}
