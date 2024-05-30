import React from 'react';
import styled from 'styled-components';

export default function CustomNoProfile({ className, size = 'small' }) {
    return <NoProfile className={className} $size={size} />;
}

const NoProfile = styled.div`
    display: inline-block;
    border-radius: 50%;
    width: 25px;
    height: 25px;
    border: 1px solid ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.noProfileColor};
`;
