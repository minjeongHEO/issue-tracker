const ISSUE_DEFAULT_API_URI = '/api/issues/';
const ISSUE_DETAIL_API_URI = '/api/issues/';
const ISSUE_STATE_CLOSE_API_URI = '/api/issues/close';
const ISSUE_STATE_OPEN_API_URI = '/api/issues/open';

/**
 * 이슈 상세 조회
 * @param {*String} issueId
 * @returns {jsonObject}
 */
export const fetchIssueDetailData = async (issueId) => {
    const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

    try {
        // await delay(2000);
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DEFAULT_API_URI}${issueId}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
/**
 * 이슈 열고 닫기
 * @param {*Boolean} toIssueState - 닫기:true, 열기:false
 * @param {*Array} issueIds - 이슈 id들
 * @returns {jsonObject}
 */
export const fetchIssueStateToggle = async (toIssueState, issueIds) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DEFAULT_API_URI}${toIssueState ? 'close' : 'open'}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ issueIds }),
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        return {
            status: response.status,
            statusText: response.statusText,
        };
    } catch (error) {
        throw error;
    }
};

/**
 * 이슈 제목 수정
 * @param {*String} title - 수정하는 제목
 * @param {*Number} issueId - 이슈 id
 * @returns 
 *  - 성공: 200
    - 아이디 미존재시 : 404
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 */
export const fetchModifyIssueTitle = async (title, issueId) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DEFAULT_API_URI}${issueId}/title`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title }),
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        return {
            status: response.status,
            statusText: response.statusText,
        };
    } catch (error) {
        throw error;
    }
};
