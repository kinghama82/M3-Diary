document.addEventListener("DOMContentLoaded", function () {
    const calendarBody = document.getElementById("calendarBody");
    const colorMap = {}; // 공고명 → 색상 저장용
    const fixedColors =  ["rgba(76,175,80,0.5)", "rgba(33,150,243,0.5)", "rgba(255,152,0,0.5)", "rgba(176,39,39,0.5)", "#F44336", "#009688"];
    let colorIndex = 0; /*고정 색상 4개를 두고 1,2,3,4에서 5로 넘어가면 리셋*/


    function getColorForJob(job) {
        if (!colorMap[job.positionTitle]) {
            colorMap[job.positionTitle] = fixedColors[colorIndex % fixedColors.length];
            colorIndex++;
        }
        return colorMap[job.positionTitle];
    }/*컬러가 주어질때마다 ++로 색 변화*/

    function drawCalendar(year, month) {
        calendarBody.innerHTML = "";
        const firstDay = new Date(year, month - 1, 1);
        const lastDay = new Date(year, month, 0);
        const firstWeekday = firstDay.getDay();
        const daysInMonth = lastDay.getDate();

        let day = 1;
        while (day <= daysInMonth) {
            const weekRow = document.createElement("div");
            weekRow.classList.add("week-row");

            for (let i = 0; i < 7; i++) {
                const cell = document.createElement("div");
                cell.classList.add("cell");

                if ((day === 1 && i < firstWeekday) || day > daysInMonth) {
                    weekRow.appendChild(cell);
                } else {
                    cell.innerText = day;
                    const dateStr = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
                    cell.setAttribute("data-date", dateStr);
                    weekRow.appendChild(cell);
                    day++;
                }
            }
            document.getElementById("currentMonthLabel").innerText = `${year}년 ${month}월`;
            calendarBody.appendChild(weekRow);
        }
    }



    function renderJobBars() {
        window.favorites.forEach(job => {
            const startDate = new Date(job.postingDate);
            const endDate = new Date(job.expirationDate);

            /*조건으로 달이 넘어가면서 공고 달력과 관련없으면 초기화된 달력으로*/

            if (
                endDate.getFullYear() < currentYear ||
                (endDate.getFullYear() === currentYear && endDate.getMonth() + 1 < currentMonth) ||
                startDate.getFullYear() > currentYear ||
                (startDate.getFullYear() === currentYear && startDate.getMonth() + 1 > currentMonth)
            ) {
                return;
            }
            // 날짜 범위 순회 복붙함...
            let current = new Date(startDate);
            while (current <= endDate) {
                const yyyy = current.getFullYear();
                const mm = String(current.getMonth() + 1).padStart(2, '0');
                const dd = String(current.getDate()).padStart(2, '0');
                const dateStr = `${yyyy}-${mm}-${dd}`;

                if (yyyy === currentYear && Number(mm) === currentMonth) {
                    const cell = document.querySelector(`[data-date='${dateStr}']`);
                    if (cell) {
                        const bar = document.createElement("div");
                        bar.classList.add("job-bar");
                        bar.style.backgroundColor = getColorForJob(job); // 공고마다 고정된 색 적용
                        bar.title = job.positionTitle;//

                        // 텍스트는 시작 셀에만 출력
                        if (dateStr === job.postingDate) {
                            bar.innerText = job.positionTitle;
                            bar.title = job.positionTitle; //  툴팁 추가
                            bar.style.borderRadius = "10px 0 0 10px";
                        } else if (dateStr === job.expirationDate) {
                            bar.innerText = ".";
                            bar.title = job.positionTitle; //  툴팁 유지
                            bar.style.borderRadius = "0 10px 10px 0";
                            bar.style.color = "transparent";
                        } else {
                            bar.innerText = ".";
                            bar.title = job.positionTitle;
                            bar.style.borderRadius = "0";
                            bar.style.color = "transparent";
                        }
                        /*툴팁이란, 마우스 가져다 대면 전체 내용이 보임 잘 가져다 대야힘*/
                        cell.appendChild(bar);
                    }
                }

                // 다음 날짜로 이동
                current.setDate(current.getDate() + 1);
            }
        });
    }
    /*이전달 - 다음달 */
    const today = new Date();
    let currentYear = today.getFullYear();
    let currentMonth = today.getMonth() + 1;

    drawCalendar(currentYear, currentMonth);
    renderJobBars();

    /*이전달 previousMonth 요이름 html이랑 같게*/
    document.getElementById("previousMonth").addEventListener("click", ()=>{
        currentMonth--;
        if(currentMonth === 0){
            currentMonth =12;
            currentYear--;
        }
        drawCalendar(currentYear, currentMonth);
        renderJobBars();
    });

    /*다음달*/
    document.getElementById("nextMonth").addEventListener("click", ()=>{
        currentMonth++;
        if(currentMonth === 13){
            currentMonth =1;
            currentYear++;
        }
        drawCalendar(currentYear, currentMonth);
        renderJobBars();
    });
});
