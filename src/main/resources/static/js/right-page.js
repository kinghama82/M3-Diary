window.addEventListener('DOMContentLoaded', () => {
    const today = new Date();
    let selectedYear = today.getFullYear();
    let selectedMonth = today.getMonth() + 1;
    let selectedDay = today.getDate();

    const $dateText = document.getElementById('selected-date');
    const $daySelect = document.getElementById('day-select');
    const $content = document.getElementById('diary-content');
    const $saveBtn = document.getElementById('save-btn');
	const $deleteBtn = document.getElementById('delete-btn');
    const $tabs = document.querySelectorAll('.vertical-tabs .tab'); // ← 반드시 여기 안에 있어야 함!

    const updateDateText = () => {
      $dateText.innerText = `${selectedYear}년 ${selectedMonth}월 ${selectedDay}일`;
    };

    const getDaysInMonth = (year, month) => new Date(year, month, 0).getDate();

    const updateDayDropdown = async () => {
      const totalDays = getDaysInMonth(selectedYear, selectedMonth);
      let writtenDays = [];

      try {
        const res = await axios.get(`/api/diary/list/${selectedYear}/${selectedMonth}`);
        writtenDays = res.data;
      } catch {
        writtenDays = [];
      }

      $daySelect.innerHTML = '';
      for (let d = 1; d <= totalDays; d++) {
        const option = document.createElement('option');
        option.value = d;
        option.textContent = writtenDays.includes(d) ? `${d}일 📝` : `${d}일`;
        $daySelect.appendChild(option);
      }

      $daySelect.value = selectedDay;
    };

    const loadContent = async () => {
      updateDateText();
      try {
        const res = await axios.get(`/api/diary/${selectedYear}/${selectedMonth}/${selectedDay}`);
        $content.value = res.data.content || '';
      } catch {
        $content.value = '';
      }
    };

    $daySelect.addEventListener('change', (e) => {
      selectedDay = parseInt(e.target.value);
      updateDateText();
      loadContent();
    });

    $tabs.forEach(tab => {
      tab.addEventListener('click', (e) => {
		const selectedTab = e.currentTarget;
        selectedMonth = parseInt(tab.dataset.month);
        $tabs.forEach(t => t.classList.remove('active'));
        tab.classList.add('active');
        selectedDay = 1;
        updateDayDropdown().then(loadContent);
      });
    });
	//저장버튼
    $saveBtn.addEventListener('click', () => {
      axios.post('/api/diary/save', {
        year: selectedYear,
        month: selectedMonth,
        day: selectedDay,
        content: $content.value
      }).then(() => {
        alert('저장 완료');
        updateDayDropdown();
      }).catch(err => {
        if (err.response && err.response.status === 409) {
          alert('이미 작성된 메모입니다. 수정만 가능합니다.');
        } else {
          alert('오류가 발생했습니다.');
        }
      });
    });
	//삭제버튼
	$deleteBtn.addEventListener('click', () => {
	  if (!confirm(`${selectedYear}년 ${selectedMonth}월 ${selectedDay}일의 메모를 삭제하시겠습니까?`)) return;

	  axios.delete(`/api/diary/${selectedYear}/${selectedMonth}/${selectedDay}`)
	    .then(() => {
	      alert('삭제 완료');
	      $content.value = '';
	      updateDayDropdown();
	    })
	    .catch(err => {
	      if (err.response && err.response.status === 403) {
	        alert('본인만 삭제할 수 있습니다.');
	      } else if (err.response && err.response.status === 404) {
	        alert('삭제할 메모가 없습니다.');
	      } else {
	        alert('삭제 중 오류가 발생했습니다.');
	      }
	    });
	});

    // 초기 렌더링
    updateDateText();
    updateDayDropdown().then(loadContent);
    const defaultTab = document.querySelector(`.tab[data-month='${selectedMonth}']`);
    if (defaultTab) defaultTab.classList.add('active');
  });