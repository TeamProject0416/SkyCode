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
    const form = document.getElementById("inquiry");
    const inquiryList = document.getElementById("inquiry-list");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const type = document.getElementById("type").value;
        const isPrivate = document.getElementById("private").checked;
        const title = document.getElementById("title").value;
        const content = document.getElementById("content").value;

        const listItem = document.createElement("li");
        listItem.innerHTML = `<strong>${type}</strong> - ${title} (${isPrivate ? "나만보기" : "전체공개"})<p>${content}</p>`;

        inquiryList.appendChild(listItem);

        form.reset();
    });
});
