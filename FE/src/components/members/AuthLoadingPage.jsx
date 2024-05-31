import React, { useEffect } from 'react';
import { fetchGithubLogin } from '../../api/fetchMembers';
import { useNavigate } from 'react-router-dom';
import ClipLoader from 'react-spinners/ClipLoader';
import styled from 'styled-components';

export default function AuthLoadingPage() {
    const navigate = useNavigate();

    useEffect(() => {
        const url = new URL(window.location.href);
        const authorizationCode = url.searchParams.get('code');

        const githubLogin = async () => {
            try {
                const loginResult = await fetchGithubLogin(authorizationCode);
                if (loginResult.result) {
                    localStorage.setItem('storeUserData', JSON.stringify(loginResult.data));
                    navigate('/');
                }
            } catch (error) {
                console.error('Login Failed:', error);
            }
        };

        if (authorizationCode) githubLogin();
    }, []);

    return (
        <StyledDiv>
            <ClipLoader color="#007AFF" />
        </StyledDiv>
    );
}

const StyledDiv = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
`;
