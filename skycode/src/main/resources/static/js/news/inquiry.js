// function checkOnlyOne(element) {
//
//     const checkboxes
//         = document.getElementsByName("view");
//
//     checkboxes.forEach((vw) => {
//         vw.checked = false;
//     })
//
//     element.checked = true;
// }

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementsByClassName("inquiry");
    const inquiryList = document.getElementsByClassName("inquiry-list");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const type = document.getElementById("type").value;
        const isPrivate = document.getElementById("private").checked;
        const title = document.getElementsByClassName("title").value;
        const content = document.getElementsByClassName("content").value;

        const listItem = document.createElement("li");
        listItem.innerHTML = `<strong>${type}</strong> - ${title} (${isPrivate ? "나만보기" : "전체공개"})<p>${content}</p>`;

        inquiryList.appendChild(listItem);

        form.reset();
    });
});
