import React, { useContext } from 'react';
import styled from 'styled-components';
import { DarkModeContext } from '../context/DarkModeContext';
import DarkLogotypeMedium from '../assets/DarkLogotypeMedium.svg';
import LightLogotypeMedium from '../assets/LightLogotypeMedium.svg';

export default function Header() {
    const { isDarkMode } = useContext(DarkModeContext);

    return (
        <HeaderContainer>
            <StyledLogo>
                <img src={isDarkMode ? DarkLogotypeMedium : LightLogotypeMedium} className="logo" alt="logo" />
            </StyledLogo>
            <StyledProfile>
                <img src="https://avatars.githubusercontent.com/u/96780693?s=40&v=4" className="profile" alt="profile" />
            </StyledProfile>
        </HeaderContainer>
    );
}

const HeaderContainer = styled.header`
    width: 100%;
    height: 10%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 85px;
    padding-right: 85px;
    min-width: 890px;
`;
const StyledLogo = styled.span`
    margin-left: 85px;
`;
const StyledProfile = styled.span`
    margin-right: 85px;
    & img {
        border-radius: 50%;
    }
`;
