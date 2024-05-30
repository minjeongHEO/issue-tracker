import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { fetchCreateNewLabel, fetchDeleteLabel, fetchLabelDetailData, fetchLabelMilestoneCountData, fetchModifyLabel } from '../api/fetchLabelData';

/**
 * 레이블, 마일스톤 - 개수 조회
 * @returns
 */
export const useLabelMilestoneCountData = () => {
    return useQuery({
        queryKey: ['label', 'milestone', 'count'],
        queryFn: async () => await fetchLabelMilestoneCountData(),
        staleTime: 1000 * 60 * 5, // 데이터를 신선하게 유지하는 시간
        cacheTime: 1000 * 60 * 10, // 캐시에서 데이터를 유지하는 시간
    });
};
/**
 * 레이블 - 조회
 * @returns
 */
export const useLabelDetailData = () => {
    return useQuery({
        queryKey: ['labelDetail'],
        queryFn: async () => await fetchLabelDetailData(),
        staleTime: 1000 * 60 * 5, // 데이터를 신선하게 유지하는 시간
        cacheTime: 1000 * 60 * 10, // 캐시에서 데이터를 유지하는 시간
    });
};

/**
 * 새로운 레이블 - 생성
 * @param {*fn} onSuccessCallback - 성공 시 로직
 * @returns
 * key는 무조건 String으로 통일
 */
export const useCreateNewLabel = ({ onSuccessCallback }) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ name, description, textColor, bgColor }) => await fetchCreateNewLabel(name, description, textColor, bgColor),
        onSuccess: (res) => {
            if (onSuccessCallback) onSuccessCallback();
            queryClient.invalidateQueries({ queryKey: ['labelDetail'], refetchType: 'active' });
            queryClient.invalidateQueries({ queryKey: ['label', 'milestone', 'count'], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};

/**
 * 레이블 - 수정
 * @param {*fn} onSuccessCallback - 성공 시 로직
 * @param {*fn} onErrorCallback - 실패 시 로직
 * @returns
 * key는 무조건 String으로 통일
 */
export const useModifyLabel = ({ onSuccessCallback, onErrorCallback }) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ name, description, textColor, bgColor, labelId }) =>
            await fetchModifyLabel(name, description, textColor, bgColor, labelId),
        onSuccess: (res) => {
            if (onSuccessCallback) onSuccessCallback();
            queryClient.invalidateQueries({ queryKey: ['labelDetail'], refetchType: 'active' });
            queryClient.invalidateQueries({ queryKey: ['label', 'milestone', 'count'], refetchType: 'active' });
        },
        onError: (e) => {
            onErrorCallback();
        },
    });
};

/**
 * 레이블 - 삭제
 * @param {*fn} onSuccessCallback - 성공 시 로직
 * @param {*fn} onErrorCallback - 실패 시 로직
 * @returns
 * key는 무조건 String으로 통일
 */
export const useDeleteLabel = ({ onSuccessCallback, onErrorCallback }) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ labelId }) => await fetchDeleteLabel(labelId),
        onSuccess: (res) => {
            if ((res.status === 200 || res.status === 201) && onSuccessCallback) onSuccessCallback();
            queryClient.invalidateQueries({ queryKey: ['labelDetail'], refetchType: 'active' });
            queryClient.invalidateQueries({ queryKey: ['label', 'milestone', 'count'], refetchType: 'active' });
        },
        onError: (e) => {
            onErrorCallback();
        },
    });
};
