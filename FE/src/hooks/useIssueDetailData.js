import { useQueries, useQuery } from '@tanstack/react-query';
import { fetchIssueDetailData } from '../api/fetchIssueData';

export const useIssueDetailData = (issueId) => {
    return useQuery({
        queryKey: ['issueDetail', issueId],
        queryFn: () => fetchIssueDetailData(issueId),
        staleTime: 1000 * 60 * 5,
        Suspense: true,
    });
};
