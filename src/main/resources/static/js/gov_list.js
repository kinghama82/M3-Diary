//정부정책 불러오는 스크립트
function loadPage(page = 1, zipCd = "") {
	const url = zipCd ? `/api/gov/list?page=${page}&zipCd=${zipCd}` : `/api/gov/list?page=${page}`;
	axios.get(url)
		.then(function(response) {
			const items = response.data.result.youthPolicyList;
			const totCount = response.data.result.pagging.totCount;
			const pageSize = 10;
			const totalPages = Math.ceil(totCount / pageSize);

			const tbody = document.querySelector("tbody");
			tbody.innerHTML = "";

			items.forEach((item, index) => {
				const tr = document.createElement("tr");
				tr.classList.add("text-center");
				tr.innerHTML = `
					<td class="border border-black">${(page - 1) * 10 + index + 1}</td>
					<td class="border border-black">
					   <a href="https://www.youthcenter.go.kr/youthPolicy/ythPlcyTotalSearch/ythPlcyDetail/${item.plcyNo}" 
						  class="text-decoration-none" target="_blank">
						  ${item.plcyNm}
					   </a>
					</td>
					<td class="border border-black">${item.aplyYmd || '-'}</td>
					<td class="border border-black">${item.rgtrHghrkInstCdNm || '-'}</td>
					<td class="border border-black">
						<button class="bookmark-btn"
							data-plcyno="${item.plcyNo}"
							data-plcynm="${item.plcyNm}"
							data-aplyymd="${item.aplyYmd}">
							⭐
						</button>
					</td>
				`;
				tbody.appendChild(tr);
			});

			// 페이지네이션
			const pagination = document.getElementById("pagination");
			pagination.innerHTML = "";

			const groupSize = 10;
			const currentGroup = Math.floor((page - 1) / groupSize);
			const start = currentGroup * groupSize + 1;
			const end = Math.min(start + groupSize - 1, totalPages);

			const nav = document.createElement("nav");
			const ul = document.createElement("ul");
			ul.className = "pagination justify-content-center flex-wrap";

			// 이전
			if (start > 1) {
				const li = document.createElement("li");
				li.className = "page-item";
				li.innerHTML = `<a class="page-link" href="#">«</a>`;
				li.addEventListener("click", () => loadPage(start - 1, zipCd));
				ul.appendChild(li);
			}

			// 페이지 번호
			for (let i = start; i <= end; i++) {
				const li = document.createElement("li");
				li.className = "page-item" + (i === page ? " active" : "");
				li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
				li.addEventListener("click", () => loadPage(i, zipCd));
				ul.appendChild(li);
			}

			// 다음
			if (end < totalPages) {
				const li = document.createElement("li");
				li.className = "page-item";
				li.innerHTML = `<a class="page-link" href="#">»</a>`;
				li.addEventListener("click", () => loadPage(end + 1, zipCd));
				ul.appendChild(li);
			}

			nav.appendChild(ul);
			pagination.appendChild(nav);
		})
		.catch(function(error) {
			console.error("정책 리스트 불러오기 실패:", error);
		});
}

document.addEventListener("DOMContentLoaded", function() {
	loadPage(1); // 첫 페이지 로딩

	// 드롭다운 변경 시
	const regionSelect = document.getElementById("regionSelect");
	regionSelect.addEventListener("change", () => {
		const selectedZipCd = regionSelect.value;
		loadPage(1, selectedZipCd); // 지역 변경 시 1페이지부터 다시
	});
});

// 즐겨찾기 버튼 클릭 이벤트 처리
document.addEventListener("click", function(e) {
	if (e.target.classList.contains("bookmark-btn")) {
		const btn = e.target;
		const data = {
			plcyNo: btn.dataset.plcyno,
			plcyNm: btn.dataset.plcynm,
			aplyYmd: btn.dataset.aplyymd
		};

		axios.post("/api/gov/bookmark", data)
			.then((res) => {
				if (res.data === "저장됨") {
					alert("즐겨찾기에 추가되었습니다.");
					btn.textContent = "저장됨";
					btn.classList.add("btn-secondary");
				} else {
					alert("⭐ 즐겨찾기에서 제거되었습니다.");
					btn.textContent = "⭐";
					btn.classList.remove("btn-secondary");
				}
			})
			.catch(() => {
				alert("즐겨찾기 요청 실패");
			});
	}
});
