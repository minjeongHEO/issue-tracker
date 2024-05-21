import styled from 'styled-components';
import theme from '../styles/theme';
import { Button } from 'antd';

/**
 *
 * @param {buttonSizes} - small / medium / large
 * @param {buttonType} - container / outline / ghost
 * @returns
 */
export function CustomButton({ className, children, type = 'container', size = 'small' }) {
    return (
        <StyledButton className={className} $buttonType={type} $size={size}>
            {children}
        </StyledButton>
    );
}

const StyledButton = styled(Button)`
    display: block;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    ${(props) => theme.buttonSizes[props.$size]}
    ${(props) => theme.buttonStyles[props.$buttonType]} /* background-color: ${(props) => props.$bgColor || props.theme.bgColorBody}; */
    /*visibility: ${(props) => props.$visibility || 'visible'};*/
    & span {
        margin-left: 5px;
    }
`;
