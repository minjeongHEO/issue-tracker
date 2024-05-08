import React from 'react';
import styled, { css } from 'styled-components';

export default function Login() {
    return (
        <MembersContainer>
            <Logo>Issue Tracker</Logo>

            <StyledButton style={{ marginBottom: '20px' }}>GitHub 계정으로 로그인</StyledButton>
            <span style={{ marginBottom: '20px' }}>or</span>
            <InputContainer>
                <StyledInput type="text" />
                <StyledPlaceHolder>아이디</StyledPlaceHolder>
            </InputContainer>
            <InputContainer>
                <StyledInput type="password" />
                <StyledPlaceHolder>비밀번호</StyledPlaceHolder>
            </InputContainer>
            <StyledButton style={{ marginBottom: '30px' }}>아이디로 로그인</StyledButton>
            <span>회원가입</span>
        </MembersContainer>
    );
}
const InputContainer = styled.div`
    position: relative;
    margin-bottom: 20px;
`;

const StyledPlaceHolder = styled.span`
    color: #6e7191;

    position: absolute;

    top: 10px;
    left: 10px;
    font-size: 12px;

    //TODO: 타이핑 시 변경
    /* top: 17px;
    left: 10px;
    font-size: 24px; */
`;

const StyledInput = styled.input`
    width: 320px;
    height: 56px;
    font-size: 16px;
    color: #4e4b66;
    /* background-color: #fefefe; */
    background-color: #eff0f6;
    border: none;
    border-radius: 12px;
    padding: 15px 20px 5px 20px;
    box-sizing: border-box;
`;

const StyledButton = styled.button`
    width: 320px;
    height: 56px;
    font-size: 16px;
    padding: 10px 20px;
    border: 1px;
    border-color: 'var(--primary-color)';
    border-radius: 12px;
    background-color: 'var(--primary-color)';
    color: ${(props) => props.color || 'white'};
    cursor: pointer;

    transition: opacity 0.3s;
    &:hover {
        opacity: 80%;
    }
    &:active {
        opacity: 64%;
    }
    &:disabled {
        opacity: 32%;
    }
    &:focus {
        filter: brightness(110%);
    }
`;

const MembersContainer = styled.div`
    width: 400px;
    height: 700px;
    display: flex;
    flex-direction: column;
    align-items: center; /* 가로 방향으로 중앙 정렬 */
    justify-content: center;
`;
const Logo = styled.div`
    width: 342px;
    height: 72px;
    font-family: 'Montserrat';
    font-style: italic;
    font-weight: 400;
    font-size: 56px;
    line-height: 72px;
    letter-spacing: -0.04em;
    color: #14142b;
    margin-bottom: 60px;
`;
