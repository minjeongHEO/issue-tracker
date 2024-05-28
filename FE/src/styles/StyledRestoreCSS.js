import React from 'react';
import styled from 'styled-components';

export const StyledRestoreCSS = styled.div`
    /* 기본 브라우저 스타일 복원 */
    h1 {
        font-size: 2em;
        margin: 0.67em 0;
    }
    h2 {
        font-size: 1.5em;
        margin: 0.75em 0;
    }
    h3 {
        font-size: 1.17em;
        margin: 0.83em 0;
    }
    h4 {
        font-size: 1em;
        margin: 1.12em 0;
    }
    h5 {
        font-size: 0.83em;
        margin: 1.5em 0;
    }
    h6 {
        font-size: 0.75em;
        margin: 1.67em 0;
    }
    p {
        margin: 1em 0;
    }
    ul,
    ol {
        padding-left: 40px;
        margin: 1em 0;
    }
    li {
        margin: 0.5em 0;
    }
    blockquote {
        margin: 1em 40px;
        padding: 0.5em 10px;
        border-left: 3px solid #ccc;
    }
    pre {
        display: block;
        padding: 8px;
        margin: 1em 0;
        font-size: 0.875em;
        line-height: 1.6;
        background: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 3px;
    }
    a {
        color: -webkit-link;
        cursor: pointer;
        text-decoration: underline;
    }
    code {
        font-family: monospace;
        background: #f5f5f5;
        padding: 0.2em 0.4em;
        border-radius: 3px;
    }
    table {
        border-collapse: collapse;
        margin: 1em 0;
    }
    th,
    td {
        padding: 0.5em;
        border: 1px solid #ccc;
    }
    img {
        width: 100%;
    }
`;
