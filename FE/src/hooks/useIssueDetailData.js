import { useMutation, useQueries, useQuery, useQueryClient } from '@tanstack/react-query';
import {
    fetchCreateIssueComment,
    fetchCreateNewIssue,
    fetchDeleteComment,
    fetchDeleteIssue,
    fetchIssueDetailData,
    fetchIssueStateToggle,
    fetchModifyIssueAssignees,
    fetchModifyIssueComment,
    fetchModifyIssueContent,
    fetchModifyIssueLabels,
    fetchModifyIssueMilestone,
    fetchModifyIssueTitle,
} from '../api/fetchIssueData';

/**
 * 이슈 상세 - 조회
 * @param {*String} issueId
 * @returns
 */
export const useIssueDetailData = (issueId) => {
    return useQuery({
        queryKey: ['issueDetail', String(issueId)],
        queryFn: () => fetchIssueDetailData(issueId),
        // staleTime: 1000 * 60 * 5,
        Suspense: true,
    });
};

/**
 * 이슈 상세 - 이슈 상태 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useIssueStateToggle = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ toIssueState, issueIds }) => await fetchIssueStateToggle(toIssueState, issueIds),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};
/**
 * 이슈 리스트 - 이슈 상태 변경
 */
export const useIssueListStateToggle = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ toIssueState, issueIds }) => await fetchIssueStateToggle(toIssueState, issueIds),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issue_list'], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 삭제
 * @param {*String} issueId - 이슈상세 아이디
 * @param {*fn} onSuccessCallBack - 성공 시 로직
 * @returns 
 * key는 무조건 String으로 통일
 *  - 성공: 200
    - 아이디 미존재시 : 404
    - 바인딩 에러시: 400
    - 서버 내부 오류시: 500
 */
export const useDeleteIssue = (issueId, onSuccessCallBack) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async () => await fetchDeleteIssue(issueId),
        onSuccess: (res) => {
            if (res.status === 200) {
                queryClient.invalidateQueries({ queryKey: ['issue_list'], refetchType: 'active' });
                if (onSuccessCallBack) onSuccessCallBack();
            }
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 제목 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueTitle = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ title }) => await fetchModifyIssueTitle(title, issueId),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 본문 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueContent = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ content, fileId = null }) => await fetchModifyIssueContent(content, fileId, issueId),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 코멘트 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueComment = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ commentId, content, fileId = null }) => await fetchModifyIssueComment(content, fileId, commentId),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 코멘트 생성
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useCreateIssueComment = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ writerId, content, fileId = null }) => await fetchCreateIssueComment(writerId, content, issueId, fileId),
        onSuccess: (res) => {
            if (res.status === 201) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 코멘트 삭제
 * @param {*String} issueId - 이슈상세 아이디
 * @param {*String} commentId - 이슈코멘트 아이디
 * @param {*fn} onSuccessCallBack - 성공 시 로직
 * @returns 
 * key는 무조건 String으로 통일
 *  - 성공: 200
- 아이디 미존재시 : 404
- 바인딩 에러시: 400
- 서버 내부 오류시: 500
*/
export const useDeleteComment = (issueId, onSuccessCallBack) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (commentId) => await fetchDeleteComment(commentId),
        onSuccess: (res) => {
            if (res.status === 200) {
                queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
                if (onSuccessCallBack) onSuccessCallBack();
            }
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 레이블 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueLabels = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ labelIds }) => await fetchModifyIssueLabels(issueId, labelIds),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 담당자 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueAssignees = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ assigneeIds }) => await fetchModifyIssueAssignees(issueId, assigneeIds),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 이슈 상세 - 이슈 마일스톤 변경
 * @param {*String} issueId - 이슈상세 아이디
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyIssueMilestone = (issueId) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ milestoneId }) => await fetchModifyIssueMilestone(issueId, Number(milestoneId)),
        onSuccess: (res) => {
            if (res.status === 200) queryClient.invalidateQueries({ queryKey: ['issueDetail', String(issueId)], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 새로운 이슈  -  생성
 * @param {*fn} onSuccessCallBack - 성공 시 로직
 * @returns
 * key는 무조건 String으로 통일
 */
export const useCreateNewIssue = (onSuccessCallBack) => {
    return useMutation({
        mutationFn: async ({ title, content, authorId, milestoneId, fileId = null, labelIds, assigneeIds }) =>
            await fetchCreateNewIssue(title, content, authorId, milestoneId, fileId, labelIds, assigneeIds),
        onSuccess: (res) => {
            if (onSuccessCallBack) onSuccessCallBack(res.id);
        },
        // onError: () => {
        // },
    });
};
