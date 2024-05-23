const ISSUE_DEFAULT_API_URI = '/api/issues/';
const ISSUE_COMMENTS_DEFAULT_API_URI = '/api/comments';

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
 * @returns {}
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
 * 이슈 삭제
 * @param {*Number} issueId - 이슈 아이디
 * @returns {}
 *  - 성공: 200
    - 아이디 미존재시 : 404
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 
 */
export const fetchDeleteIssue = async (issueId) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DEFAULT_API_URI}${issueId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
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

/**
 * 이슈 본문 수정
 * @param {*String} content - 수정하는 제목
 * @param {*String} fileId - 파일아이디(없을 시 null)
 * @returns 
 *  - 성공: 200
    - 아이디 미존재시 : 404
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 */
export const fetchModifyIssueContent = async (content, fileId, issueId) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DEFAULT_API_URI}${issueId}/body`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ content, fileId }),
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
 * 이슈 댓글 수정
 * @param {*String} content - 수정하는 댓글내용
 * @param {*String} fileId - 파일아이디(없을 시 null)
 * @returns 
 *  - 성공: 200
    - 아이디 미존재시 : 404
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 */
export const fetchModifyIssueComment = async (content, fileId, commentId) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_COMMENTS_DEFAULT_API_URI}/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ content, fileId }),
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
 * 이슈 댓글 생성
 * @param {*String} writerId - 작성자 아이디
 * @param {*String} content - 댓글내용
 * @param {*Number} issueId - 이슈 아이디
 * @param {*Number} fileId - 파일아이디(없을 시 null)
 * @returns 
 *  - 성공: 201
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 */
export const fetchCreateIssueComment = async (writerId, content, issueId, fileId) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_COMMENTS_DEFAULT_API_URI}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ writerId, content, issueId, fileId }),
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
