import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { fetchCreateNewLabel, fetchLabelDetailData } from '../api/fetchLabelData';

/**
 * 레이블 - 조회
 * @returns
 */
export const useLabelDetailData = () => {
    return useQuery({
        queryKey: ['labelDetail'],
        queryFn: () => fetchLabelDetailData(),
        staleTime: 1000 * 60 * 5, // 데이터를 신선하게 유지하는 시간
        cacheTime: 1000 * 60 * 10, // 캐시에서 데이터를 유지하는 시간
    });
};

/**
 * 새로운 레이블 - 생성
 * @param {*fn} onSuccessCallBack - 성공 시 로직
 * @returns
 * key는 무조건 String으로 통일
 */
export const useCreateNewLabel = (onSuccessCallBack) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({ name, description, textColor, bgColor }) => await fetchCreateNewLabel(name, description, textColor, bgColor),
        onSuccess: (res) => {
            if (onSuccessCallBack) onSuccessCallBack();
            queryClient.invalidateQueries({ queryKey: ['labelDetail'], refetchType: 'active' });
        },
        // onError: () => {
        // },
    });
};
