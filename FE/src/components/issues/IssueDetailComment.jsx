import { Button } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { FlexCol, FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { calculatePastTime } from '../../utils/dateUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconSmile } from '../../assets/icons/IconSmile';
import { IconPaperClip } from '../../assets/icons/IconPaperClip';

export default function IssueDetailComment({ detailCommentData }) {
    //TODO: React Query + Suspense
    if (!detailCommentData) return <div>Loading...</div>;

    const { id, content, createDate, writer, file, isWriter } = detailCommentData;
    const [contentArea, setContentArea] = useState(content);
    const [pastTime, setPastTime] = useState('');
    const [editState, SetEditState] = useState(false);

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
            <StyledCommentContainer>
                <CommentNav>
                    <CommentData>
                        <StyledProfile src={writer.imgUrl} alt={'userProfile'} size={'medium'} />
                        <span className="userName">{writer.id}</span>
                        <span className="">{pastTime}</span>
                    </CommentData>
                    <NavBtnContainer>
                        <StyledLabel visibility={isWriter ? 'visible' : 'hidden'}>작성자</StyledLabel>
                        <NavBtn onClick={toggleEditState}>
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
                        <>
                            <Content>
                                <StyledTextArea value={contentArea} onChange={handleChange}></StyledTextArea>
                                <StyledNotifiy>띄어쓰기 포함 {contentArea.length}자</StyledNotifiy>
                                <StyledLine />
                                <StyledFileBtn>
                                    <IconPaperClip />
                                    <div>파일 첨부하기</div>
                                </StyledFileBtn>
                            </Content>
                        </>
                    ) : (
                        <Content>
                            <div className="defaultContent">content</div>
                        </Content>
                    )}
                </CommentMain>
            </StyledCommentContainer>
            {editState && (
                <MainBtnContainer>
                    <Button>편집 취소</Button>
                    <Button>편집 완료</Button>
                </MainBtnContainer>
            )}
        </>
    );
}
const StyledFileBtn = styled(FlexRow)`
    margin-top: 20px;
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
    bottom: 60px;
    right: 2px;
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
    position: relative; /* ::after를 위한 상대적 위치 설정 */
`;

const Content = styled.div`
    /* background-color: red; */
    width: 95%;
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
    margin: 10px 0;
    Button {
        margin-left: 10px;
    }
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
    margin-top: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    border-radius: 10px;
    border: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    color: ${(props) => props.theme.fontColor};
`;
