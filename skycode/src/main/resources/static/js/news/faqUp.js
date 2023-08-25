window.onload = () => {
    // panel-FaqUp-container
    const panelFaqUpContainer = document.querySelectorAll(".nySearch"); // NodeList 객체

    // panel-FaqUp-answer
    let panelFaqUpAnswer = document.querySelectorAll(".nyFaqUpSearchPik");



    // 반복문 순회하면서 해당 FaqUp제목 클릭시 콜백 처리
    for( let i=0; i < panelFaqUpContainer.length; i++ ) {
        panelFaqUpContainer[i].addEventListener('click', function() { // 클릭시 처리할 일
            // FaqUp 제목 클릭시 -> 본문이 보이게끔 -> active 클래스 추가
            panelFaqUpAnswer[i].classList.toggle('active');
        });
    };


}