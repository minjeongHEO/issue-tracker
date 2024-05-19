const LABELS_API_URI = '/api/labels';
const MEMBERS_API_URI = '/api/members';
const MILESTONES_API_URI = '/api/milestones?isClosed=';

/**
 * 레이블 리스트 조회
 * @returns {jsonObject}
 */
export const fetchLabelsData = async () => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${LABELS_API_URI}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
/**
 * 유저 리스트 조회
 * @returns {jsonObject}
 */
export const fetchMembersData = async () => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${MEMBERS_API_URI}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
/**
 * 마일스톤 리스트 조회
 * @returns {jsonObject}
 */
export const fetchMilestonesData = async (isClosed) => {
    const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));
    try {
        await delay(5000);

        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${MILESTONES_API_URI}${isClosed}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
