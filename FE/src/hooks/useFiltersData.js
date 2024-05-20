import { useQueries, useQuery } from '@tanstack/react-query';
import { fetchIssueListData, fetchLabelsData, fetchMembersData, fetchMilestonesData } from '../api/fetchFilterData';
import { useFilterContext } from '../context/FilterContext';
import labels from '../components/labels/Labels';

const filterTypeIsClosed = (selectedFilters) => {
    if (!selectedFilters || Object.keys(selectedFilters).length === 0 || !selectedFilters.issues) return;

    const issues = selectedFilters.issues || {};
    if (issues.isOpen === null && issues.isClosed === null) return false;
    else return Object.entries(issues).filter(([key, value]) => (key === 'isOpen' || key === 'isClosed') && value !== null)[0][0] === 'isOpen';
};

const filterTypeAuthorId = (selectedFilters, userId) => {
    if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

    const authorObj = Object.entries(selectedFilters).filter(([key, value]) => key === 'author' && value !== null);
    if (authorObj.length) return authorObj[0][1];

    const issuesObj = Object.entries(selectedFilters.issues).filter(([key, value]) => key === 'authorMe' && value !== null);
    if (issuesObj.length) return userId;

    return '';
};

const filterTypeAssigneeId = (selectedFilters, userId) => {
    if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

    const assigneeObj = Object.entries(selectedFilters).filter(([key, value]) => key === 'assignee' && value !== null);
    if (assigneeObj.length) return assigneeObj[0][1];

    const issuesObj = Object.entries(selectedFilters.issues).filter(([key, value]) => key === 'assigneeMe' && value !== null);
    if (issuesObj.length) return userId;

    return '';
};

//TODO: 선택안한 값들 필터링
const filterTypeNoValues = (selectedFilters) => {
    if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

    return '';
};

export const useFiltersData = () => {
    const { state: selectedFilters } = useFilterContext();
    const userId = localStorage.getItem('storeUserData') ? JSON.parse(localStorage.getItem('storeUserData')).id : '';

    const isClosedParam = filterTypeIsClosed(selectedFilters) || '';
    const authorIdParam = filterTypeAuthorId(selectedFilters) || '';
    const assigneeIdParam = filterTypeAssigneeId(selectedFilters, userId) || '';
    const noValuesParam = filterTypeNoValues(selectedFilters) || '';

    const queries = [
        { key: 'labels', fn: fetchLabelsData },
        { key: 'members', fn: fetchMembersData },
        { key: 'milestones_open', fn: () => fetchMilestonesData(false) },
        { key: 'milestones_closed', fn: () => fetchMilestonesData(true) },
        { key: 'issue_list', fn: () => fetchIssueListData(isClosedParam, authorIdParam, assigneeIdParam, noValuesParam, userId) },
    ];

    return useQueries({
        queries: queries.map(({ key, fn }) => ({
            queryKey: ['filter', key],
            queryFn: fn,
            staleTime: 1000 * 60 * 5,
            Suspense: true,
        })),
    });
};
