/*은준 js*/
$(function () {
  // mother child
  const mother = document.querySelector(".oejmother");
  const childs = document.querySelectorAll(".oejchild");
  let btns = document.querySelectorAll(".oejAllbtn");

  function change() {
    for (let j = 0; j < btns.length; j++) {
      btns[j].onclick = function () {
        for (let i = 0; i < btns.length; i++) {
          btns[i].classList.remove("on");
          childs[i].style.zIndex = 0;
        }
        this.classList.add("on");
        childs[j].style.zIndex = 1;
      };
    }
  }

  $(document).ready(function () {
    $(".oejAllbtn.on").trigger('click');
  });

  change();

});
/*/은준 js*/

/*재웅 js*/

// 카드 버튼 아이콘 변경
const cardBtn1 = document.querySelectorAll('.cardBtn1'),
      cardBtn2 = document.querySelectorAll('.cardBtn2'),
      cardBtn3 = document.querySelectorAll('.cardBtn3');
// cardBtn1

let bookMarkOnOff = 0;
for(let i = 0; i < cardBtn1.length; i++){
cardBtn1[i].addEventListener('click', () => {
  if(bookMarkOnOff === 0) {
    cardBtn1[i].innerHTML = '<i class="fa-solid fa-bookmark"></i>';
    bookMarkOnOff++;
  }else if (bookMarkOnOff === 1) {
    cardBtn1[i].innerHTML = '<i class="fa-regular fa-bookmark"></i>';
    bookMarkOnOff--;
  }
})
}

// cardBtn2, cardBtn3
// cardBtn2와 cardBtn3의 length가 무조건 같으므로 같은 for문에 작성
// 마우스 호버시 아이콘 변경
for(let i = 0; i < cardBtn2.length; i++){
  cardBtn2[i].addEventListener('mouseenter', () => {
    cardBtn2[i].innerHTML = '<i class="fa-solid fa-comment"></i>';
    cardBtn2[i].style.cursor = 'pointer';
  })
  cardBtn3[i].addEventListener('mouseenter', () => {
    cardBtn3[i].innerHTML = '<i class="fa-solid fa-share-from-square"></i>';
    cardBtn3[i].style.cursor = 'pointer';
  });
}

// 마우스가 떨어지면 아이콘 변경
for(let i = 0; i < cardBtn2.length; i++){
  cardBtn2[i].addEventListener('mouseleave', () => {
    cardBtn2[i].innerHTML = '<i class="fa-regular fa-comment">';
  })
  cardBtn3[i].addEventListener('mouseleave', () => {
    cardBtn3[i].innerHTML = '<i class="fa-regular fa-share-from-square"></i>';
  });
}
// 카드 버튼 아이콘 변경 끝


// 모달
const modal = document.querySelector('.modal'),
      btnOpenPopup = document.querySelector('.btnOpenPopup'),
      body = document.querySelector('body'),
      modalClose = document.querySelector('.modalCloseArea');

// 모달 on
btnOpenPopup.addEventListener('click', () => {
  modal.classList.toggle('show');
  body.style.overflow = 'hidden';
  btnOpenPopup.style.display = "none";
});
// 모달 off
modalClose.addEventListener('click', () => {
  modal.classList.toggle('show');
  body.style.overflow = 'auto';
  btnOpenPopup.style.display = "block";

});
// 모달 끝

// 모달 버튼 클리 이벤드들
const startBtn = document.querySelector('.startBtn'),
      arriveBtn = document.querySelector('.arriveBtn'),
      saText = document.querySelector('.modalContentLeftArea'),
      dateBtn = document.querySelector('.dateBtn'),
      personNumBtn = document.querySelector('.personNumBtn'),
      mcArea = document.querySelector('.modalContentCenterArea');

      startBtn.addEventListener('click', () => {
        saText.innerHTML = '<h3>출발지를<br>선택해주세요</h3>';
      });
      arriveBtn.addEventListener('click', () => {
        saText.innerHTML = '<h3>도착지를<br>선택해주세요</h3>';
      })
      dateBtn.addEventListener('click', () => {
        saText.innerHTML = '<h3>항공편 날짜를<br>선택해주세요</h3>'
      })
      personNumBtn.addEventListener('click', () => {
        saText.innerHTML = '<h3>탑승인원과 좌석<br>등급을 선택해주세요</h3>'
        mcArea.style.display = 'none';
      })

// 모달 버튼 클리 이벤드들 끝


/*/재웅 js*/