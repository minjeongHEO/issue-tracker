const LABEL_DEFAULT_API_URI = '/api/labels';
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

/**
 * 레이블 조회
 * @param {*String} issueId
 * @returns {jsonObject}
 */
export const fetchLabelDetailData = async () => {
    try {
        // await delay(2000);
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${LABEL_DEFAULT_API_URI}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200 || response.status === 201) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};

/**
 * 새로운 레이블 생성
 * @param {*String} name 
 * @param {*String} descriptionParam 없을 경우 null
 * @param {*String} textColor 
 * @param {*String} bgColor 
 * @returns 
 *  - 성공: 201 
    - 데이터 바인딩(즉, 이름이나 배경 색깔이 비어져서 왔을 때) 실패: 400 
    - 유효하지 않은 배경 색깔: 400 
    - 라벨 이름 중복: 409 
 */
export const fetchCreateNewLabel = async (name, descriptionParam, textColor, bgColor) => {
    const description = descriptionParam || null;
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${LABEL_DEFAULT_API_URI}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, description, textColor, bgColor }),
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200 || response.status === 201) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};
