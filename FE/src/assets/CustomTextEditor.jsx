import React from 'react';
import styled from 'styled-components';
import { IconPaperClip } from '../assets/icons/IconPaperClip';
import { FlexRow } from '../styles/theme';
import themes from '../styles/theme';

export default function CustomTextEditor({ className, $value, $onChange, $onFocus, $onBlur, $fileOnClick = () => {} }) {
    return (
        <>
            <StyledTextArea value={$value} onChange={$onChange} onFocus={$onFocus} onBlur={$onBlur} />
            <StyledNotifiy>띄어쓰기 포함 {$value?.length ?? 0}자</StyledNotifiy>
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
    position: relative;
    resize: none;
    width: 100%;
    min-height: 100px;
    padding: 15px;
    background-color: transparent;
    color: ${(props) => props.theme.fontColor};
    caret-color: var(--primary-color);
    border-radius: 10px 10px 0 0;
    margin-bottom: 15px;

    white-space: pre-wrap;
    word-wrap: break-word;
    outline: none;
`;

const StyledNotifiy = styled.span`
    position: absolute;
    bottom: 45px;
    right: 20px;
    font-size: 13px;
`;

const StyledLine = styled.div`
    width: 100%;
    height: 1px;
    border-bottom: 2px dashed ${themes.colorValue.grayscale[300]};
`;
const StyledFileBtn = styled(FlexRow)`
    margin: 5px 0 5px 15px;
    font-size: 12px;
    justify-content: flex-start;
    /* align-items: flex-start; */
    width: 100px;
    height: 30px;
    cursor: pointer;
    * {
        margin-right: 2px;
    }
`;
