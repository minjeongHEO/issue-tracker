export const getUserId = () => {
    return localStorage.getItem('storeUserData') ? JSON.parse(localStorage.getItem('storeUserData')).memberProfile.id : '';
};
export const getUserImg = () => {
    return localStorage.getItem('storeUserData') ? JSON.parse(localStorage.getItem('storeUserData')).memberProfile.imgUrl : '';
};
