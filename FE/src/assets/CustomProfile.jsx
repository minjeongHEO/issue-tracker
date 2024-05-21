import styled from 'styled-components';
import theme from '../styles/theme';

export function CustomProfile({ className, src = '', alt = '', size = 'small' }) {
    return <StyledImg className={className} src={src} $size={size} alt={alt} />;
}

const StyledImg = styled.img`
    border-radius: 50%;
    width: ${(props) => theme.imgSize[props.$size]};
    height: ${(props) => theme.imgSize[props.$size]};
    border: 1px solid ${(props) => props.theme.borderColor};
    object-fit: cover;
`;
