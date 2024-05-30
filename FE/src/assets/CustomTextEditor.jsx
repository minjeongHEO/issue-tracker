import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';
import { IconPaperClip } from '../assets/icons/IconPaperClip';
import { FlexRow } from '../styles/theme';
import themes from '../styles/theme';

export default function CustomTextEditor({
    className,
    $value,
    $onChange,
    $onFocus,
    $onBlur,
    $fileOnClick = () => {},
    $fileOnChange = () => {},
    $isFileUploaded = false,
    $height = '100',
}) {
    const fileInputRef = useRef(null);

    const handleFileClick = () => {
        if (fileInputRef.current && !$isFileUploaded) fileInputRef.current.click();
    };

    return (
        <>
            <StyledTextArea value={$value} onChange={$onChange} onFocus={$onFocus} onBlur={$onBlur} $height={$height} />
            <StyledNotifiy>띄어쓰기 포함 {$value?.length ?? 0}자</StyledNotifiy>
            <StyledLine />
            <StyledFileBtn onClick={$fileOnClick}>
                <IconPaperClip />
                <input
                    ref={fileInputRef}
                    type="file"
                    name="file"
                    accept="image/*,audio/*,video/mp4,video/x-m4v"
                    onChange={$fileOnChange}
                    style={{ display: 'none' }}
                />
                <AppendButton type="button" onClick={handleFileClick} disabled={$isFileUploaded}>
                    파일 첨부하기
                </AppendButton>
            </StyledFileBtn>
        </>
    );
}

const AppendButton = styled.button`
    border: none;
    cursor: pointer;
    background: none;
    color: ${(props) => props.theme.fontColor};

    &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }
`;

const StyledTextArea = styled.textarea`
    border: none;
    position: relative;
    resize: none;
    width: 100%;
    min-height: ${(props) => props.$height}px;
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
    /* width: 100px; */
    height: 30px;
    * {
        margin-right: 2px;
    }
`;
