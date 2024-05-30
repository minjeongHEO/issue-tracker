const ID_LOGIN_API_URI = '/api/login';

/**
 * 아이디로 로그인
 * @param {Object} - {id, password}
 * @returns {Object} - { result:boolean, data:{email, id, nickname} };
    - 성공: 200
    - 데이터 바인딩 실패: 400
    - 로그인 실패 : 401
 */
export const fetchLogin = async ({ id, password }) => {
    try {
        const response = await fetch(`${import.meta.env.VITE_TEAM_SERVER}${ID_LOGIN_API_URI}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id, password }),
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        if (response.status === 200) {
            const result = await response.json();
            return { result: true, data: result };
        }
    } catch (error) {
        throw error;
    }
};
/**
 * 깃허브 로그인
 * @param {Object} - {id, password}
 * @returns {Object} - { result:boolean, data:{email, id, nickname} };
    - 성공: 200
    - 데이터 바인딩 실패: 400
    - 로그인 실패 : 401
 */
export const fetchGithubLogin = async () => {
    try {
        const CLIENT_ID = 'Ov23liTzJL66RbPZt3fg';
        const REDIRECT_URI = `${import.meta.env.VITE_TEAM_CLIENT}/members/callback`;
        const githubAuthUrl = `https://github.com/login/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}`;

        // https://github.com/login/oauth/authorize?client_id=Ov23liTzJL66RbPZt3fg&redirect_uri=https://api.issue-tracker.site/api/oauth/github/callback
        // https://github.com/login/oauth/authorize?client_id=Ov23liTzJL66RbPZt3fg&redirect_uri={server 주소}/api/oauth/github/callback

        const response = await fetch(githubAuthUrl, {
            method: 'GET',
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        const result = await response.json();
        return { result: true, data: result };
    } catch (error) {
        throw error;
    }
};
