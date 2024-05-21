import { Button } from 'antd';
import React, { useEffect, useState } from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { dark } from 'react-syntax-highlighter/dist/esm/styles/prism';
import styled from 'styled-components';
import { FlexCol, FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { calculatePastTime } from '../../utils/dateUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconSmile } from '../../assets/icons/IconSmile';
import { IconPaperClip } from '../../assets/icons/IconPaperClip';
import { CustomButton } from '../../assets/CustomButton';
import { IconXsquare } from '../../assets/icons/IconXsquare';
import { StyledRestoreCSS } from '../../styles/StyledRestoreCSS';

export default function IssueDetailComment({ detailCommentData }) {
    //TODO: React Query + Suspense
    if (!detailCommentData) return <div>Loading...</div>;

    const { id, content, createDate, writer, file, isWriter } = detailCommentData;
    const [contentArea, setContentArea] = useState(content);
    const [pastTime, setPastTime] = useState('');
    const [editState, SetEditState] = useState(false);
    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => setIsFocused(true);
    const handleBlur = () => setIsFocused(false);
    const toggleEditState = () => SetEditState((prev) => !prev);

    const handleChange = ({ target }) => {
        const { value } = target;
        setContentArea(value);
    };

    useEffect(() => {
        setPastTime(calculatePastTime(createDate));

        const intervalPerTime = () => {
            setInterval(() => {
                setPastTime(calculatePastTime(createDate));
            }, 1000 * 60);
        };

        return () => clearInterval(intervalPerTime);
    }, [createDate]);

    return (
        <>
            <StyledCommentContainer $isfocused={isFocused}>
                <CommentNav>
                    <CommentData>
                        <StyledProfile src={writer.imgUrl} alt={'userProfile'} size={'medium'} />
                        <span className="userName">{writer.id}</span>
                        <span className="">{pastTime}</span>
                    </CommentData>
                    <NavBtnContainer>
                        <StyledLabel visibility={isWriter ? 'visible' : 'hidden'}>작성자</StyledLabel>
                        <NavBtn visibility={isWriter ? 'visible' : 'hidden'} onClick={toggleEditState}>
                            <IconEdit />
                            편집
                        </NavBtn>
                        <NavBtn>
                            <IconSmile />
                            반응
                        </NavBtn>
                    </NavBtnContainer>
                </CommentNav>
                <CommentMain>
                    {editState ? (
                        <Content>
                            <StyledTextArea value={contentArea} onChange={handleChange} onFocus={handleFocus} onBlur={handleBlur}></StyledTextArea>
                            <StyledNotifiy>띄어쓰기 포함 {contentArea.length}자</StyledNotifiy>
                            <StyledLine />
                            <StyledFileBtn>
                                <IconPaperClip />
                                <div>파일 첨부하기</div>
                            </StyledFileBtn>
                        </Content>
                    ) : (
                        <Content>
                            <StyledRestoreCSS>
                                <StyledReactMarkdown
                                    children={content}
                                    remarkPlugins={[remarkGfm]}
                                    components={{
                                        code({ node, inline, className, children, ...props }) {
                                            const match = /language-(\w+)/.exec(className || '');
                                            return !inline && match ? (
                                                <SyntaxHighlighter
                                                    children={String(children).replace(/\n$/, '')}
                                                    style={dark}
                                                    language={match[1]}
                                                    PreTag="div"
                                                    {...props}
                                                />
                                            ) : (
                                                <code className={className} {...props}>
                                                    {children}
                                                </code>
                                            );
                                        },
                                    }}
                                />
                            </StyledRestoreCSS>
                        </Content>
                    )}
                </CommentMain>
            </StyledCommentContainer>
            {editState && (
                <MainBtnContainer>
                    <StyledBtn size={'medium'} type={'outline'} onClick={toggleEditState}>
                        <IconXsquare />
                        편집 취소
                    </StyledBtn>
                    <StyledBtn size={'medium'}>
                        <IconEdit />
                        편집 완료
                    </StyledBtn>
                </MainBtnContainer>
            )}
        </>
    );
}
const StyledReactMarkdown = styled(ReactMarkdown)`
    padding: 15px;
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
const StyledLine = styled.div`
    width: 100%;
    height: 1px;
    border-bottom: 2px dashed ${(props) => props.theme.borderColor};
`;

const StyledNotifiy = styled.span`
    position: absolute;
    bottom: 70px;
    right: 15px;
    font-size: 13px;
`;
const StyledTextArea = styled.textarea`
    /* background-color: aliceblue; */
    border: none;
    resize: none;
    width: 100%;
    min-height: 100px;
    padding: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
    caret-color: var(--primary-color);
    position: relative;
`;

const Content = styled.div`
    /* background-color: red; */
    width: 100%;
    height: 100%;
    /* padding: 15px; */
    /* margin: 15px 0px; */
    word-wrap: break-word;
    text-align: justify;
    position: relative;

    & .defaultContent {
        width: 100%;
        height: 100%;
        padding: 15px;
    }
`;

const CommentMain = styled(FlexCol)`
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    border-radius: 0 0 10px 10px;
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
`;

const StyledLabel = styled(CustomLabelBadge)`
    font-size: 12px;
`;

const StyledProfile = styled(CustomProfile)`
    margin-left: 10px;
`;
const NavBtn = styled(FlexRow)`
    visibility: ${(props) => props.visibility};
    font-size: 12px;
    * {
        margin-right: 2px;
    }
    cursor: pointer;
`;
const NavBtnContainer = styled(FlexRow)`
    justify-content: space-around;
    /* background-color: red; */
    width: 190px;
    margin-right: 10px;
`;
const MainBtnContainer = styled(FlexRow)`
    justify-content: end;
    width: 100%;
    margin-bottom: 20px;
`;
const StyledBtn = styled(CustomButton)`
    margin-left: 10px;
`;

const CommentData = styled(FlexRow)`
    * {
        margin-left: 10px;
    }
`;

const CommentNav = styled(FlexRow)`
    width: 100%;
    height: 50px;
    border-radius: 10px 10px 0 0;
    border-bottom: 1px solid ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.listHeaderColor};
    color: ${(props) => props.theme.fontColor};
`;

const StyledCommentContainer = styled.div`
    /* background-color: antiquewhite; */
    display: flex;
    flex-direction: column;
    width: 100%;
    min-height: 200px;
    margin-bottom: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    border-radius: 10px;
    border: 1px solid;
    border-color: ${(props) => (props.$isfocused ? 'var(--primary-color)' : props.theme.borderColor)};
    color: ${(props) => props.theme.fontColor};
`;
