// 파일 업로드 버튼 클릭 이벤트 리스너
const submitButton = document.getElementById('submit');
const fileUpload = document.getElementById('fileUpload');

submitButton.addEventListener('click', function(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append('file', fileUpload.files[0]);

    // 파일 업로드 AJAX 요청
    $.ajax({
        type: 'POST',
        url: '/upload', // 파일 업로드를 처리할 URL 설정
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            // 파일 업로드 성공시 처리할 코드
            console.log('File uploaded successfully:', response);
            // 추가적인 처리 로직을 구현해야 할 수 있습니다.

            // 공지사항 폼 제출
            $('#notion').submit();
        },
        error: function(error) {
            // 파일 업로드 실패시 처리할 코드
            console.error('File upload failed:', error);
            // 추가적인 에러 처리 로직을 구현해야 할 수 있습니다.
        }
    });
});
