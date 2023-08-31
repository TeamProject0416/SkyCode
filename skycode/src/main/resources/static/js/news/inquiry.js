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

// document.addEventListener("DOMContentLoaded", function() {
//     const form = document.getElementById("inquiry");
//     const inquiryList = document.getElementById("inquiry-list");
//
//     form.addEventListener("submit", function(event) {
//         event.preventDefault();
//
//         const type = document.getElementById("type").value;
//         const isPrivate = document.getElementById("private").checked;
//         const title = document.getElementById("title").value;
//         const content = document.getElementById("content").value;
//
//         const listItem = document.createElement("li");
//         listItem.innerHTML = `<strong>${type}</strong> - ${title} (${isPrivate ? "나만보기" : "전체공개"})<p>${content}</p>`;
//
//         inquiryList.appendChild(listItem);
//
//         form.reset();
//     });
// });
//
// document.addEventListener("DOMContentLoaded", function() {
//     const form = document.getElementById("inquiry");
//     const submitButton = document.getElementById("submit");
//
//     form.addEventListener("submit", function(event) {
//         event.preventDefault(); // 폼 기본 동작 중단
//
//         // 입력된 값 가져오기
//         const type = document.getElementById("type").value;
//         const isPrivate = document.getElementById("private").checked;
//         const title = document.getElementById("title").value;
//         const content = document.getElementById("content").value;
//
//         // 입력값 유효성 검사
//         if (title === "" || content === "") {
//             alert("제목과 내용을 모두 입력해주세요.");
//             return;
//         }
//
//         // 서버로 데이터 전송 및 처리
//         const data = {
//             type: type,
//             isPrivate: isPrivate,
//             inquiryTitle: title,
//             inquiryContent: content,
//         };
//
//         // 현재 시간 가져오기
//         const currentTime = new Date();
//         const formattedTime = `${currentTime.getFullYear()}-${currentTime.getMonth() + 1}-${currentTime.getDate()} ${currentTime.getHours()}:${currentTime.getMinutes()}:${currentTime.getSeconds()}`;
//
//
//         // 데이터 출력을 위한 HTML 생성
//         const newInquiryItem = document.createElement("li");
//         newInquiryItem.textContent = `${type} - ${title} -${content} -${isPrivate} - (${formattedTime})`;
//
//         const inquiryList = document.getElementById("inquiry-list");
//         console.log(`${type} - ${title} -${content} -${isPrivate} - (${formattedTime})`)
//         inquiryList.appendChild(newInquiryItem);
//
//         // 입력값 초기화
//         form.reset();
//
//         // 여기서는 예시로 메시지 출력
//         alert("등록이 완료되었습니다.");
//
//         // 다른 페이지로 이동
//         // window.location.href = "/news/inquiry/inquiryShow"; // 원하는 페이지의 URL로 변경
//     });
// });