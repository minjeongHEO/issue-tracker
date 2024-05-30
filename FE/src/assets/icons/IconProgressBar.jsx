import styled from 'styled-components';

export function IconProgressBar({ className, percentage = 0 }) {
    return (
        <ProgressBarBox>
            <ProgressBar $width={percentage * 100} />
        </ProgressBarBox>
    );
}

const ProgressBarBox = styled.div`
    width: 100%;
    height: 8px;
    background-color: #eff0f6;
    border: 1px solid transparent;
    border-radius: 12px;
    overflow: hidden;
`;

const ProgressBar = styled.div`
    width: ${({ $width }) => $width}%;
    height: 100%;
    background-color: #007aff;
`;
