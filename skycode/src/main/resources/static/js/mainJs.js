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
const cardBtn2 = document.querySelectorAll('.cardBtn2');

// cardBtn2
// cardBtn2와 cardBtn3의 length가 무조건 같으므로 같은 for문에 작성
// 마우스 호버시 아이콘 변경
for(let i = 0; i < cardBtn2.length; i++){
  cardBtn2[i].addEventListener('mouseenter', () => {
    cardBtn2[i].innerHTML = '<i class="fa-solid fa-comment"></i>';
    cardBtn2[i].style.cursor = 'pointer';
  })
}

// 마우스가 떨어지면 아이콘 변경
for(let i = 0; i < cardBtn2.length; i++){
  cardBtn2[i].addEventListener('mouseleave', () => {
    cardBtn2[i].innerHTML = '<i class="fa-regular fa-comment">';
  })
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

// 모달 버튼 클릭 이벤드들
const startBtn = document.querySelector('.startBtn'),
    arriveBtn = document.querySelector('.arriveBtn'),
    leftAreaU = document.querySelector('.modalContentLeftArea'),
    leftAreaD = document.querySelector('.modalContentLeftAreaD'),
    personNumBtn = document.querySelector('.personNumBtn'),

    mcArea = document.querySelector('.modalContentCenterArea');

startBtn.addEventListener('click', () => {
  leftAreaU.style.display = 'block';
});
arriveBtn.addEventListener('click', () => {
  leftAreaD.style.display = 'block';
})

personNumBtn.addEventListener('click', () => {
  saText.innerHTML = '<h3>탑승인원과 좌석<br>등급을 선택해주세요</h3>'

})

// 출발지 라벨과 체크박스를 가져옵니다.
const startCityRadios = document.querySelectorAll('.startCity');
const arriveCityRadios = document.querySelectorAll('.arriveCity');

// modalAirplaneStart와 modalAirplaneArrive 레이블을 가져옵니다.
const modalAirplaneStartLabel = document.querySelector('.modalAirplaneStart label');
const modalAirplaneArriveLabel = document.querySelector('.modalAirplaneArrive label');
const startValueInput = document.querySelector('input[name="startValue"]');
const arriveValueInput = document.querySelector('input[name="arriveValue"]');

// 각 라디오 버튼에 클릭 이벤트 리스너를 추가합니다.
// 출발지
startCityRadios.forEach(radio => {
  radio.addEventListener('click', function() {
    // 클릭된 라디오 버튼의 레이블 값을 가져와 modalAirplaneStart 레이블에 설정합니다.
    // 선택된 input 의 다음 태그의 텍스트를 label로 설정
    modalAirplaneStartLabel.textContent = this.nextElementSibling.textContent;
    modalAirplaneStartLabel.style.cssText = 'color: #000; font-weight: bold;';
    startValueInput.value = this.value; // 숨겨진 input 필드에 value값 저장합니다.
  });
});

// 도착지
arriveCityRadios.forEach(radio => {
  radio.addEventListener('click', function() {
    // 클릭된 라디오 버튼의 레이블 값을 가져와 modalAirplaneArrive 레이블에 설정합니다.
    // 선택된 input 의 다음 태그의 텍스트를 label로 설정
    modalAirplaneArriveLabel.textContent = this.nextElementSibling.textContent;
    modalAirplaneArriveLabel.style.cssText = 'color: #000; font-weight: bold;';
    arriveValueInput.value = this.value; // 숨겨진 input 필드에 value값 저장합니다.
  });
});
// 모달 버튼 클리 이벤드들 끝


// calendar
// 출국일과 귀국일 입력 필드 가져오기
const startTravelInput = document.getElementById("startTravel");
const endTravelInput = document.getElementById("endTravel");

// 탑승일 라벨 가져오기
const dateLabel = document.querySelector('label[for="date_value"]');

// 출국일과 귀국일 입력 필드 값이 변경될 때 이벤트 리스너 추가
startTravelInput.addEventListener("change", updateDateLabel);
endTravelInput.addEventListener("change", updateDateLabel);

// 탑승일 라벨 업데이트 함수
function updateDateLabel() {
  const startDate = startTravelInput.value;
  const endDate = endTravelInput.value;

  // 출국일과 귀국일이 모두 선택되었을 때
  if (startDate && endDate) {
    // 날짜를 Date 객체로 변환
    const startDateObj = new Date(startDate);
    const endDateObj = new Date(endDate);

    // 두 날짜 사이의 일 수 계산
    const timeDiff = endDateObj - startDateObj;
    const dayDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

    // 탑승일 라벨에 표시
    dateLabel.textContent = `탑승일: ${startDate} ~ ${endDate} (${dayDiff}일)`;
  } else {
    // 출국일 또는 귀국일이 선택되지 않았을 때
    dateLabel.textContent = "탑승일을 선택하세요";
  }
}
// /calendar

// personNum
// 각 입력란과 라벨 요소를 가져옵니다.
const adultInput = document.getElementById("adultNum");
const teenagerInput = document.getElementById("teenagerNum");
const childInput = document.getElementById("childNum");
const labelForPNumValue = document.querySelector("label[for='pNum_value']");

// 입력란 값이 변경될 때 이벤트 리스너를 추가합니다.
adultInput.addEventListener("input", personNumUpdateLabel);
teenagerInput.addEventListener("input", personNumUpdateLabel);
childInput.addEventListener("input", personNumUpdateLabel);

// 라벨 업데이트 함수
function personNumUpdateLabel() {
  const adultCount = parseInt(adultInput.value);
  const teenagerCount = parseInt(teenagerInput.value);
  const childCount = parseInt(childInput.value);

  labelForPNumValue.textContent = `성인: ${adultCount}명, 청소년: ${teenagerCount}명, 유아: ${childCount}명`;
}

// /personNum


// seatGrade
// 각 라디오 버튼과 라벨 요소를 가져옵니다.
const economyRadio = document.getElementById("economySeat");
const businessRadio = document.getElementById("businessSeat");
const firstClassRadio = document.getElementById("firstClassSeat");
const labelForGradeValue = document.querySelector("label[for='grade_value']");
const seatGradeInput = document.querySelector('input[name="seatGrade"]');

// 라디오 버튼 값이 변경될 때 이벤트 리스너를 추가합니다.
economyRadio.addEventListener("change", updateLabel);
businessRadio.addEventListener("change", updateLabel);
firstClassRadio.addEventListener("change", updateLabel);

// 라벨 업데이트 함수
function updateLabel() {
  let selectedGrade = "";

  if (economyRadio.checked) {
    selectedGrade = "이코노미석";
    seatGradeInput.value = "economyClass";
  } else if (businessRadio.checked) {
    selectedGrade = "비즈니스석";
    seatGradeInput.value = "businessClass";
  } else if (firstClassRadio.checked) {
    selectedGrade = "일등석";
    seatGradeInput.value = "firstClass";
  }

  labelForGradeValue.textContent = `좌석등급: ${selectedGrade}`;
}
// /seatGrade


/*/재웅 js*/