import React from 'react';
import styled from 'styled-components';
import { IconPaperClip } from '../assets/icons/IconPaperClip';
import { FlexRow } from '../styles/theme';

export default function CustomTextEditor({ className, $value, $onChange, $onFocus, $onBlur, $fileOnClick = () => {} }) {
    return (
        <>
            <StyledTextArea value={$value} onChange={$onChange} onFocus={$onFocus} onBlur={$onBlur} />
            <StyledNotifiy>띄어쓰기 포함 {$value.length}자</StyledNotifiy>
            <StyledLine />
            <StyledFileBtn onClick={$fileOnClick}>
                <IconPaperClip />
                <div>파일 첨부하기</div>
            </StyledFileBtn>
        </>
    );
}

const StyledTextArea = styled.textarea`
    border: none;
    resize: none;
    width: 100%;
    min-height: 100px;
    padding: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
    caret-color: var(--primary-color);
    position: relative;
    /* background-color: aliceblue; */
`;
const StyledNotifiy = styled.span`
    position: absolute;
    bottom: 70px;
    right: 15px;
    font-size: 13px;
`;
const StyledLine = styled.div`
    width: 100%;
    height: 1px;
    border-bottom: 2px dashed ${(props) => props.theme.borderColor};
`;
const StyledFileBtn = styled(FlexRow)`
    margin: 20px 0 0 15px;
    font-size: 13px;
    justify-content: flex-start;
    align-items: flex-start;
    cursor: pointer;
    * {
        margin-right: 2px;
    }
`;
