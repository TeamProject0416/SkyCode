
 window.onload = () => {
     const panelFaqContainer = document.querySelectorAll(".nyPanelFaqContainer");
     const panelFaqAnswer = document.querySelectorAll(".nyPanelFaqAnswer");

     for(let i = 0; i < panelFaqContainer.length; i++) {
         panelFaqContainer[i].addEventListener('click', function() {
             for(let j = 0; j < panelFaqAnswer.length; j++) {
                 if(i !== j) {  // 현재 FAQ를 제외한 나머지 FAQ를 닫음
                     panelFaqAnswer[j].classList.remove('activeTwo');
                 }
             }
             panelFaqAnswer[i].classList.toggle('activeTwo');
         });
     }
 }
