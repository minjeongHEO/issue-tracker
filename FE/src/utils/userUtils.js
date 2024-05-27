export const getUserId = () => {
    return localStorage.getItem('storeUserData') ? JSON.parse(localStorage.getItem('storeUserData')).id : '';
};
