export const initFilterState = {
    author: null,
    label: null,
    milestone: null,
    assignee: null,
    issues: {
        isOpen: null,
        isClosed: null,
        authorMe: null,
        assigneeMe: null,
        mentionsMe: null,
    },
};

const resetIssues = {
    isOpen: null,
    isClosed: null,
    authorMe: null,
    assigneeMe: null,
    mentionsMe: null,
};

export const filterReducer = (state, action) => {
    switch (action.type) {
        case 'SET_CLEAR_FILTER': //필터 지우기
            return { ...initFilterState };

        case 'SET_SELECTED_AUTHOR_FILTER': //담당자 필터
            return { ...state, author: action.payload };

        case 'SET_SELECTED_LABEL_FILTER': //레이블 필터
            return { ...state, label: action.payload };

        case 'SET_SELECTED_MILESTONE_FILTER': //마일스톤 필터
            return { ...state, milestone: action.payload };

        case 'SET_SELECTED_ASSIGNEE_FILTER': //작성자 필터
            return { ...state, assignee: action.payload };

        case 'SET_SELECTED_IS_OPEN_FILTER': //열린 이슈
            return { ...state, issues: { ...resetIssues, isOpen: action.payload } };

        case 'SET_SELECTED_IS_CLOSED_FILTER': //닫힌 이슈
            return { ...state, issues: { ...resetIssues, isClosed: action.payload } };

        case 'SET_SELECTED_AUTHOR_ME_FILTER': //내가 작성한 이슈
            return { ...state, issues: { ...resetIssues, authorMe: action.payload } };

        case 'SET_SELECTED_ASSIGNEE_ME_FILTER': //나에게 할당된 이슈
            return { ...state, issues: { ...resetIssues, assigneeMe: action.payload } };

        case 'SET_SELECTED_MENTIONS_ME_FILTER': //내가 댓글을 남긴 이슈
            return { ...state, issues: { ...resetIssues, mentionsMe: action.payload } };

        default:
            return state;
    }
};
