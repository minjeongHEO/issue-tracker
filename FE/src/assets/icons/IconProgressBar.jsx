import styled from 'styled-components';

export function IconProgressBar({ className, percentage = 0 }) {
    return (
        <StyledWrapper className={className}>
            <svg width="224" height="8" viewBox="0 0 224 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M0 4C0 1.79086 1.79086 0 4 0H220C222.209 0 224 1.79086 224 4C224 6.20914 222.209 8 220 8H4C1.79086 8 0 6.20914 0 4Z"
                    fill="url(#paint0_linear_19254_44010)"
                />
                <defs>
                    <linearGradient id="paint0_linear_19254_44010" x1="0" y1="0" x2="224" y2="0" gradientUnits="userSpaceOnUse">
                        <stop stopColor="#007AFF" />
                        <stop offset={percentage} stopColor="#007AFF" />
                        <stop offset={percentage} stopColor="#EFF0F6" />
                        <stop offset="1" stopColor="#EFF0F6" />
                    </linearGradient>
                </defs>
            </svg>
        </StyledWrapper>
    );
}
const StyledWrapper = styled.i`
    padding: 0;
`;
