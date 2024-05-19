import { useQueries, useQuery } from '@tanstack/react-query';
import { fetchLabelsData, fetchMembersData, fetchMilestonesData } from '../api/fetchFilterData';
import { Suspense } from 'react';

const queries = [
    { key: 'labels', fn: fetchLabelsData },
    { key: 'members', fn: fetchMembersData },
    { key: 'milestones_open', fn: () => fetchMilestonesData(false) },
    { key: 'milestones_closed', fn: () => fetchMilestonesData(true) },
];

export const useFiltersData = () => {
    return useQueries({
        queries: queries.map(({ key, fn }) => ({
            queryKey: ['filter', key],
            queryFn: fn,
            staleTime: 1000 * 60 * 5,
            Suspense: true,
        })),
    });
};
