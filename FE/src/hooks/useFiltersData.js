import { useQuery } from '@tanstack/react-query';
import { fetchLabelsData, fetchMembersData, fetchMilestonesData } from '../api/fetchFilterData';

/**
 * 레이블 필터
 */
export const useLabelsFilter = ({ enabled = true }) => {
    return useQuery({
        queryKey: ['filter', 'labels'],
        queryFn: fetchLabelsData,
        enabled: enabled,
        staleTime: 1000 * 60 * 5, // 데이터를 신선하게 유지하는 시간
        cacheTime: 1000 * 60 * 10, // 캐시에서 데이터를 유지하는 시간
    });
};
/**
 * 마일스톤 필터
 */
export const useMilestonesFilter = ({ enabled = true }) => {
    return useQuery({
        queryKey: ['filter', 'milestones'],
        queryFn: () => fetchMilestonesData(false),
        enabled: enabled,
        staleTime: 1000 * 60 * 5,
        cacheTime: 1000 * 60 * 10,
    });
};
/**
 * 닫힌 마일스톤 필터
 */
export const useMilestonesFilterIsClosed = ({ enabled = true }) => {
    return useQuery({
        queryKey: ['filter', 'milestones', 'isClosed'],
        queryFn: () => fetchMilestonesData(true),
        enabled: enabled,
        staleTime: 1000 * 60 * 5,
        cacheTime: 1000 * 60 * 10,
    });
};
/**
 * 멤버 필터
 */
export const useMembersFilter = ({ enabled = true }) => {
    return useQuery({
        queryKey: ['filter', 'members'],
        queryFn: fetchMembersData,
        enabled: enabled,
        staleTime: 1000 * 60 * 5,
        cacheTime: 1000 * 60 * 10,
    });
};
