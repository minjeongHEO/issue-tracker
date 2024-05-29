import React from 'react';
import Header from '../header/Header';
import { IndexContainer, MainContainer } from '../../styles/theme';
import styled from 'styled-components';

export default function labels() {
    return (
        <IndexContainer>
            <Header />
            <MainContainer>
                <TitleContainer> 버튼들</TitleContainer>
                <ContentsContainer>라벨 리스트</ContentsContainer>
            </MainContainer>
        </IndexContainer>
    );
}
const TitleContainer = styled.div`
    width: 100%;
    height: 70px;
    background-color: antiquewhite;
`;
const ContentsContainer = styled.div`
    width: 100%;
    height: 70px;
    background-color: azure;
`;
