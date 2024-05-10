import React from 'react';
import styled from 'styled-components';

export default function Main() {
    return <MainContainer>메인입니다.</MainContainer>;
}

const MainContainer = styled.main`
    background-color: azure;
    height: 90%;
    width: 100%;
`;
