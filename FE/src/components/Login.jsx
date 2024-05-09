import React, { useContext, useEffect, useState } from 'react';
import styled from 'styled-components';
import { StyledButton, StyledInput } from '../styles/theme';
import { Link } from 'react-router-dom';
import DarkLogotypeLarge from '../assets/DarkLogotypeLarge.svg';
import LightLogotypeLarge from '../assets/LightLogotypeLarge.svg';
import { DarkModeContext } from '../context/DarkModeContext';

export default function Login() {
    const { isDarkMode } = useContext(DarkModeContext);
    const [idInput, setIdInput] = useState('');
    const [pwInput, setPwInput] = useState('');
    const [isDisabled, setIsDisabled] = useState(true);

    const matchingSetter = {
        membersId: setIdInput,
        membersPw: setPwInput,
    };

    const handleChange = ({ target }) => {
        const { value } = target;
        const type = target.dataset.inputtype;
        const setter = matchingSetter[type];
        if (setter) setter(value);
    };

    const isInputValidation = () => {
        if (idInput.length >= 6 && idInput.length <= 12 && pwInput.length >= 6 && pwInput.length <= 12) return false;
        return true;
    };

    useEffect(() => {
        setIdInput('');
        setPwInput('');
        setIsDisabled(true);
    }, []);

    useEffect(() => {
        if (isInputValidation()) setIsDisabled(false);
    }, [idInput, pwInput]);

    return (
        <MembersContainer>
            <Logo>
                <img src={isDarkMode ? DarkLogotypeLarge : LightLogotypeLarge} className="logo" alt="logo" />
            </Logo>
            <StyledButton $bgcolor="#fff" $textcolor="#007AFF" style={{ marginBottom: '20px' }} disabled>
                GitHub 계정으로 로그인
            </StyledButton>
            <StyledSpan style={{ marginBottom: '20px' }}>or</StyledSpan>

            <InputContainer>
                <StyledInput type="text" onChange={handleChange} value={idInput} data-inputtype="membersId" autoComplete="off" />
                <StyledPlaceHolder>아이디</StyledPlaceHolder>
            </InputContainer>

            <InputContainer>
                <StyledInput type="password" onChange={handleChange} value={pwInput} data-inputtype="membersPw" autoComplete="off" />
                <StyledPlaceHolder>비밀번호</StyledPlaceHolder>
            </InputContainer>

            <StyledButton $bgcolor="#007AFF" $textcolor="#fff" style={{ marginBottom: '30px' }} disabled={isDisabled}>
                아이디로 로그인
            </StyledButton>

            <StyledSpan>
                <Link to="/members/join" style={linkStyle}>
                    회원가입
                </Link>
            </StyledSpan>
        </MembersContainer>
    );
}

const linkStyle = {
    textDecorationLine: 'none',
    fontSize: '16px',
    fontWeight: '500',
    color: '#6e7191',
};

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
    margin-bottom: 60px;
`;
