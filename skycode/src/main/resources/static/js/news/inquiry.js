window.onload = () => {
    // panel-faq-container
    const panelFaqContainer1 = document.querySelectorAll(".nySearch1"); // NodeList 객체
    const panelFaqContainer2 = document.querySelectorAll(".nySearch2"); // NodeList 객체

    // panel-faq-answer
    let panelFaqAnswer1 = document.querySelectorAll(".nyInquirySearchPik1");
    let panelFaqAnswer2 = document.querySelectorAll(".nyInquirySearchPik2");


    // 반복문 순회하면서 해당 FAQ제목 클릭시 콜백 처리
    for( let i=0; i < panelFaqContainer1.length; i++ ) {
        panelFaqContainer1[i].addEventListener('click', function() { // 클릭시 처리할 일
            // FAQ 제목 클릭시 -> 본문이 보이게끔 -> active 클래스 추가
            panelFaqAnswer1[i].classList.toggle('active1');
        });
    };
    // 반복문 순회하면서 해당 FAQ제목 클릭시 콜백 처리
    for( let i=0; i < panelFaqContainer2.length; i++ ) {
        panelFaqContainer2[i].addEventListener('click', function() { // 클릭시 처리할 일
            // FAQ 제목 클릭시 -> 본문이 보이게끔 -> active 클래스 추가
            panelFaqAnswer2[i].classList.toggle('active2');
        });
    };

}