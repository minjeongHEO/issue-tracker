import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { DarkModeContext } from '../../context/DarkModeContext';
import DarkLogotypeMedium from '../../assets/DarkLogotypeMedium.svg';
import LightLogotypeMedium from '../../assets/LightLogotypeMedium.svg';

export default function Header() {
    const { isDarkMode } = useContext(DarkModeContext);
    const [userId, setUserId] = useState('');
    const getUserId = () => {
        const userId = localStorage.getItem('storeUserData') ? JSON.parse(localStorage.getItem('storeUserData')).id : '';
        setUserId(userId);
    };

    useEffect(() => {
        getUserId();
    }, []);

    return (
        <HeaderContainer>
            <StyledLogo>
                <a href="/">
                    <img src={isDarkMode ? DarkLogotypeMedium : LightLogotypeMedium} className="logo" alt="logo" />
                </a>
            </StyledLogo>
            <StyledProfile>
                <span>{userId} 님</span>
                <img
                    src="https://github.com/codesquad-masters2024-team02/issue-tracker/assets/96780693/d1c7123b-89c9-485b-b9dd-8cc21a1005e0"
                    className="profile"
                    alt="profile"
                />
            </StyledProfile>
        </HeaderContainer>
    );
}

const HeaderContainer = styled.header`
    width: 100%;
    height: 100px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    min-width: 890px;
`;
const StyledLogo = styled.span`
    margin-left: 85px;
    cursor: pointer;
`;
const StyledProfile = styled.span`
    margin-right: 85px;
    display: flex;
    align-items: center;
    & span {
        margin-right: 10px;
    }
    & img {
        border-radius: 50%;
        width: 40px;
        height: 40px;
        object-fit: cover;
    }
`;
