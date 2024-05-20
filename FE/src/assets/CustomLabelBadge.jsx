import styled from 'styled-components';
import theme from '../styles/theme';

export function CustomLabelBadge({ className, children, backgroundColor, color }) {
    return (
        <StyledLabel className={className} $bgColor={backgroundColor} $color={color}>
            {children}
        </StyledLabel>
    );
}

const StyledLabel = styled.span`
    display: inline-block;
    align-content: center;
    min-width: 20px;
    height: 23px;
    padding: 2px 10px;
    border-radius: 25px;
    border: 1px solid ${(props) => props.theme.borderColor};
    font-size: 15px;
    background-color: ${(props) => props.$bgColor || props.theme.bgColorBody};
    color: ${(props) => props.$color || props.theme.fontColor};
`;
