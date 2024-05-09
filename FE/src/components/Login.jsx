import React, { useState } from 'react';
import styled, { css } from 'styled-components';
import { StyledButton } from '../styles/theme';

export default function Login() {
    const [idInput, setIdInput] = useState('');
    const [pwInput, setPwInput] = useState('');

    const handleChange = ({ target }) => {};

    return (
        <MembersContainer>
            <Logo>Issue Tracker</Logo>
            <StyledButton $bgcolor="#fff" $textcolor="#007AFF" style={{ marginBottom: '20px' }}>
                GitHub 계정으로 로그인
            </StyledButton>
            <StyledSpan style={{ marginBottom: '20px' }}>or</StyledSpan>
            <InputContainer>
                <StyledInput type="text" onChange={handleChange} value={idInput} inputType="membersId" />
                <StyledPlaceHolder>아이디</StyledPlaceHolder>
            </InputContainer>
            <InputContainer>
                <StyledInput type="password" onChange={handleChange} value={pwInput} inputType="membersPw" />
                <StyledPlaceHolder>비밀번호</StyledPlaceHolder>
            </InputContainer>
            <StyledButton $bgcolor="#007AFF" $textcolor="#fff" style={{ marginBottom: '30px' }}>
                아이디로 로그인
            </StyledButton>

            <StyledSpan>회원가입</StyledSpan>
        </MembersContainer>
    );
}

const StyledSpan = styled.span`
    font-weight: 500;
    color: #6e7191;
    font-size: 16px;
    cursor: pointer;
`;
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
    box-sizing: border-box;
    width: 320px;
    height: 56px;
    font-size: 16px;
    color: #4e4b66;
    /* background-color: #fefefe; */
    background-color: #eff0f6;
    border: none;
    border-radius: 12px;
    padding: 15px 20px 5px 20px;
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
