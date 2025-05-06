// ✅ CSRF 토큰 가져오기
const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

// ✅ 지원 상태 업데이트 함수 (CSRF 포함)
function updateAppliedStatus(saraminId, applied) {
    axios.post('/saramin/apply', null, {
        params: {
            saraminId: saraminId,
            applied: applied
        },
        headers: {
            [header]: token // ex) 'X-CSRF-TOKEN': 'abcdefg...'
        }
    }).then(response => {
        console.log(response.data);
        if (applied) {
            const modal = new bootstrap.Modal(document.getElementById('appliedModal'));
            modal.show();
        } else {
            const modal = new bootstrap.Modal(document.getElementById('cancelModal'));
            modal.show();
        }
    }).catch(error => {
        console.error(error);
        alert('업데이트 실패 (권한 또는 토큰 문제)');
    });
}

// 지원 버튼 이벤트
document.querySelectorAll('.apply-btn').forEach(button => {
    button.addEventListener('click', () => {
        const id = button.getAttribute('data-saramin-id');
        updateAppliedStatus(id, true);
    });
});

// 미지원 버튼 이벤트
document.querySelectorAll('.cancel-btn').forEach(button => {
    button.addEventListener('click', () => {
        const id = button.getAttribute('data-saramin-id');
        updateAppliedStatus(id, false);
    });
});