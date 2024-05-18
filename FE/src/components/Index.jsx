import React from 'react';
import Header from './header/Header';
import Main from './issues/Main';
import styled from 'styled-components';
import { IndexContainer } from '../styles/theme';

export default function Index() {
    return (
        <IndexContainer>
            <Header />
            <Main />
        </IndexContainer>
    );
}
