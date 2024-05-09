import { styled } from 'styled-components';

export const lightTheme = {
    bgColorBody: '#FFF',
    fontColor: '#363537',
    dropshadowColor: '#14142B, 4%, Blur 8',
};

export const darkTheme = {
    bgColorBody: '#363537',
    fontColor: '#FAFAFA',
    dropshadowColor: '#14142B, 80%, Blur 16',
};

export const StyledButton = styled.button`
    width: 320px;
    height: 56px;
    font-size: 16px;
    padding: 10px 20px;
    /* border: 1px solid var(--primary-color); */
    border: 1px solid #007aff;
    border-radius: 12px;
    background-color: ${(props) => props.$bgcolor || 'var(--primary-color)'};
    color: ${(props) => props.$textcolor || 'var(--font-color, white)'};
    cursor: pointer;
    transition: opacity 0.3s;

    &:hover {
        opacity: 0.8;
    }
    &:active {
        opacity: 0.6;
    }
    &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }
    &:focus {
        filter: brightness(110%);
    }
`;
