
window.onload = () => {
    // panel-NotionUp-container
    const panelNotionUpContainer = document.querySelectorAll(".nySearch"); // NodeList 객체

    // panel-NotionUp-answer
    let panelNotionUpAnswer = document.querySelectorAll(".nyNotionUpSearchPik");



    // 반복문 순회하면서 해당 NotionUp제목 클릭시 콜백 처리
    for( let i=0; i < panelNotionUpContainer.length; i++ ) {
        panelNotionUpContainer[i].addEventListener('click', function() { // 클릭시 처리할 일
            // NotionUp 제목 클릭시 -> 본문이 보이게끔 -> active 클래스 추가
            panelNotionUpAnswer[i].classList.toggle('active');
        });
    };


}