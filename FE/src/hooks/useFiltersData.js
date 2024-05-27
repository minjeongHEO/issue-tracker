import { useQuery } from '@tanstack/react-query';
import { fetchLabelsData, fetchMembersData, fetchMilestonesData } from '../api/fetchFilterData';

/**
 * 레이블 필터
 */
export const useLabelsFilter = () => {
    return useQuery({
        queryKey: ['filter', 'labels'],
        queryFn: fetchLabelsData,
        // staleTime: 1000 * 60 * 5,
        Suspense: true,
    });
};
/**
 * 마일스톤 필터
 */
export const useMilestonesFilter = () => {
    return useQuery({
        queryKey: ['filter', 'milestones'],
        queryFn: () => fetchMilestonesData(false),
        // staleTime: 1000 * 60 * 5,
        Suspense: true,
    });
};
/**
 * 멤버 필터
 */
export const useMembersFilter = () => {
    return useQuery({
        queryKey: ['filter', 'members'],
        queryFn: fetchMembersData,
        // staleTime: 1000 * 60 * 5,
        Suspense: true,
    });
};
