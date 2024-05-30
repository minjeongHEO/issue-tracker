export const calculatePastTime = (createDate) => {
    const registerDate = new Date(createDate);
    const diffDate = Math.abs(new Date() - registerDate);
    const diffMiniutes = Math.floor(diffDate / (1000 * 60));
    const diffHours = Math.floor(diffDate / (1000 * 60 * 60));
    const diffDays = Math.floor(diffDate / (1000 * 60 * 60 * 24));

    if (diffDays >= 1) return `${diffDays}일 전`;
    if (diffHours >= 1) return `${diffHours}시간 전`;
    return diffMiniutes >= 1 ? `${diffMiniutes}분 전` : `방금 전`;
};
