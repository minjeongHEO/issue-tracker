import { useMutation, useQueries, useQuery, useQueryClient } from '@tanstack/react-query';
import {
    fetchCreateIssueComment,
    fetchIssueDetailData,
    fetchIssueStateToggle,
    fetchModifyIssueComment,
    fetchModifyIssueContent,
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
