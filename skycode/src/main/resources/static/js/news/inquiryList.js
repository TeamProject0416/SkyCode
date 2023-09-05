function insertInquiryIntoTable(inquiry) {
    const tableBody = document.getElementById('inquiryTableBody');
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td><span th:text="${inquiry.id}">1</span></td>
        <td><span th:text="${inquiry.type}">회원가입/탈퇴</span></td>
        <td><a th:href="@{'/news/inquiry/show/' + ${inquiry.id}}">
            <span th:text="${inquiry.inquiryTitle}"></span>
        </a></td>
        <td><span th:text="${inquiry.viewCount}">조회수</span></td>
        <td><span th:text="${#temporals.format(inquiry.regTime, 'yyyy.MM.dd')}">2023.08.25</span></td>
        <td th:text="${inquiry.isPrivate ? '비공개' : '공개'}">공개</td>
    `;

    // Insert the new row into the correct sorted position
    let inserted = false;
    const rows = Array.from(tableBody.querySelectorAll('tr'));
    for (let i = 0; i < rows.length; i++) {
        const rowDate = new Date(rows[i].querySelector('td:nth-child(5) span').textContent);
        const inquiryDate = new Date(inquiry.regTime);
        if (inquiryDate > rowDate) {
            tableBody.insertBefore(newRow, rows[i]);
            inserted = true;
            break;
        }
    }

    // If not inserted yet (i.e., it's the oldest), append to the end
    if (!inserted) {
        tableBody.appendChild(newRow);
    }
}

// Example: Call this function whenever a new inquiry is created
const newInquiry = {
    id: 123,
    type: '회원가입/탈퇴',
    inquiryTitle: '새로운 문의',
    viewCount: 0,
    regTime: '2023-08-29T10:00:00', // Replace with the actual timestamp of the new inquiry
    isPrivate: false
};

insertInquiryIntoTable(newInquiry);