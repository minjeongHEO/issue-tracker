import React from 'react';
import Header from './header/Header';
import Main from './main/Main';
import styled from 'styled-components';

export default function Index() {
    return (
        <IndexContainer>
            <Header />
            <Main />
        </IndexContainer>
    );
}

const IndexContainer = styled.div`
    height: 100vh;
    width: 100vw;
    min-width: 890px;
    min-height: 890px;
`;
