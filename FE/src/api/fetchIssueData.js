const ISSUE_DETAIL_API_URI = '/api/issues/';

/**
 * 이슈 상세 조회
 * @param {*Number} issueId
 * @returns {jsonObject}
 */
export const fetchIssueDetailData = async (issueId) => {
    const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

    try {
        await delay(2000);
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ISSUE_DETAIL_API_URI}${issueId}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
