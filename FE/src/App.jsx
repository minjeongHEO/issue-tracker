import { Suspense, useContext, useEffect, useState } from 'react';
import { AppRoutes } from './router/routes';
import styled, { ThemeProvider } from 'styled-components';
import { GlobalStyle } from './styles/GlobalStyle.js';
import { DarkModeContext } from './context/DarkModeContext.jsx';
import { Button } from 'antd';
import FilterProvider from './context/FilterContext.jsx';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

function App() {
    const { isDarkMode, toggleDarkMode, darkModeTheme } = useContext(DarkModeContext);
    const queryClient = new QueryClient();

    return (
        <ThemeProvider theme={darkModeTheme}>
            <GlobalStyle />
            <DarkThemeBtn onClick={toggleDarkMode}>{isDarkMode ? '🌞' : '🌚'}</DarkThemeBtn>
            <FilterProvider>
                <QueryClientProvider client={queryClient}>
                    <Suspense fallback={<div>Loading...</div>}>
                        <Root>
                            <AppRoutes />
                        </Root>
                    </Suspense>
                    <ReactQueryDevtools initialIsOpen={true} />
                </QueryClientProvider>
            </FilterProvider>
        </ThemeProvider>
    );
}

const Root = styled.div`
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
    place-items: center;
    display: flex;
    flex-direction: column; /* 자식 요소들을 세로 방향으로 정렬 */
    justify-content: center; /* 세로 방향으로 중앙 정렬 */
    align-items: center; /* 가로 방향으로 중앙 정렬 */
    height: 100vh;
    width: 100vw;
    margin: 0 auto;
    text-align: center;
`;

const DarkThemeBtn = styled(Button)`
    position: absolute;
    top: 30px;
    left: 30px;
    background-color: var(--primary-color);
    width: 30px;
    height: 30px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer; /* 마우스 오버 시 커서 변경 */
    outline: none; /* 포커스 아웃라인 제거 */
`;
export default App;
