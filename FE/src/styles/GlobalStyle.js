import { styled, createGlobalStyle } from 'styled-components';

export const GlobalStyle = createGlobalStyle`
  :root {
      /* Font */
    font-family: 'Pretendard', sans-serif;
    /* Color */
    --primary-color: #007AFF;
    --secondary-color: #6c757d;
    --success-color: #28a745;
    --error-color: #dc3545;
    --font-color: white;
  }
`;
