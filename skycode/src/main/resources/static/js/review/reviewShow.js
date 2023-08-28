const leftBtn = document.querySelector('.leftBtn'),
      rightBtn = document.querySelector('.rightBtn'),
      sliders = document.querySelector('.sliders'),
      slideImg = document.querySelectorAll('.sliders li'),  // 슬라이드 개수를 찾기 위한 선택
      slideCount = slideImg.length, // 슬라이드 개수
      slideWidth = 600;
    let currentIdx = 0; // 현재 슬라이드

    sliders.style.width = slideWidth * slideCount + "px";

    function moveSlide(num) {
      sliders.style.left = -num * 100 + '%';
      currentIdx = num;
    }

    leftBtn.addEventListener('click', function () {
      /*첫 번째 슬라이드로 표시 됐을때는
      이전 버튼 눌러도 아무런 반응 없게 하기 위해
      currentIdx !==0일때만 moveSlide 함수 불러옴 */

      if (currentIdx !== 0) moveSlide(currentIdx - 1);
    });

    rightBtn.addEventListener('click', function () {
      /* 마지막 슬라이드로 표시 됐을때는
      다음 버튼 눌러도 아무런 반응 없게 하기 위해
      currentIdx !==slideCount - 1 일때만
      moveSlide 함수 불러옴 */
      if (currentIdx !== slideCount - 1) {
        moveSlide(currentIdx + 1);
      }
    });