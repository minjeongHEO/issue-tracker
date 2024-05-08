import { useEffect, useState } from 'react';
import { AppRoutes } from './router/routes';
import styled, { ThemeProvider } from 'styled-components';
import { lightTheme, darkTheme } from './styles/theme.js';

function App() {
    const userTheme = useState('lightTheme');
    useEffect(() => {}, [userTheme]);

    return (
        <ThemeProvider theme={lightTheme}>
            <Root>
                <AppRoutes />
            </Root>
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

export default App;
