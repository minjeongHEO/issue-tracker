import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { DarkModeContext } from '../../context/DarkModeContext';
import DarkLogotypeMedium from '../../assets/DarkLogotypeMedium.svg';
import LightLogotypeMedium from '../../assets/LightLogotypeMedium.svg';
import { getUserId, getUserImg } from '../../utils/userUtils';
import { CustomProfile } from '../../assets/CustomProfile';

export default function Header() {
    const { isDarkMode } = useContext(DarkModeContext);

    return (
        <HeaderContainer>
            <StyledLogo>
                <a href="/">
                    <img src={isDarkMode ? DarkLogotypeMedium : LightLogotypeMedium} className="logo" alt="logo" />
                </a>
            </StyledLogo>
            <StyledProfile>
                <span>{getUserId()} 님</span>
                <CustomProfile src={getUserImg()} className={'profile'} alt={'profile'} size={'medium'} />
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
