// 오늘 날짜 기준 정보
const today = new Date(); //오늘기준
const year = today.getFullYear();
const month = today.getMonth(); // 0부터 시작
const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];



// 상단 년, 달 표시
const currentMonthTitle = document.getElementById('currentMonth');
currentMonthTitle.innerText = `${year}년 ${monthNames[month]}`;

// 달력
const calendarBody = document.querySelector('.calendar-body');
calendarBody.innerHTML = "";

const firstDay = new Date(year, month, 1).getDay(); // 1일의 요일
const lastDate = new Date(year, month + 1, 0).getDate(); // 마지막 날짜

let date = 1;
/*반복문으로 칸을 만듬ㄹ*/
for (let i = 0; i < 6; i++) {
    for (let j = 0; j < 7; j++) {
        const cell = document.createElement("div");
        cell.classList.add("cell");

        /*요일 색상 처리. css에 색상이름 만들어주고*/
        if (j === 0) cell.classList.add("sunday");
        if (j === 6) cell.classList.add("saturday");

        if (i === 0 && j < firstDay) {
            calendarBody.appendChild(cell);
        } else if (date > lastDate) {
            calendarBody.appendChild(cell);
        } else {
            const dateDiv = document.createElement("div");
            dateDiv.classList.add("date");
            dateDiv.innerText = date;

            cell.appendChild(dateDiv);
            calendarBody.appendChild(cell);
            date++;
        }
    }
}
