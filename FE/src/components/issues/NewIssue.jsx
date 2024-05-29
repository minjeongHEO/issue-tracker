import React, { useState } from 'react';
import { FlexCol, FlexRow, IndexContainer, MainContainer, StyledInput } from '../../styles/theme';
import Header from '../header/Header';
import styled from 'styled-components';
import CustomTextEditor from '../../assets/CustomTextEditor';
import { CustomButton } from '../../assets/CustomButton';
import { IconPlus } from '../../assets/icons/IconPlus';
import IssueDetailSidebar from './IssueDetailSidebar';
import { CustomProfile } from '../../assets/CustomProfile';
import { getUserId, getUserImg } from '../../utils/userUtils';
import OptionSidebar from './OptionSidebar';

export default function NewIssue() {
    //TODO: Ref로 작업
    const [newTitle, setNewTitle] = useState('');
    const [newCommentArea, setNewCommentArea] = useState('');
    const [isNewCommentFocused, setIsNewCommentFocused] = useState(false);
    const [isNewCommetDisabled, setIsNewCommetDisabled] = useState(true);
    const handleCommentChange = ({ target }) => {
        const { value } = target;
        setNewCommentArea(value);
    };
    const handleTitleChange = ({ target }) => {
        const { value } = target;
        setNewTitle(value);
    };
    const handleFocus = () => setIsNewCommentFocused(true);
    const handleBlur = () => setIsNewCommentFocused(false);

    //TODO: fileId
    const submitNewComment = () => {
        createIssueComment({ writerId: getUserId(), content: newCommentArea });
        setNewCommentArea('');
    };

    return (
        <StyledDetailContainer>
            <Header />

            <StyledMainContainer>
                <TitleContainer className="title">
                    <HeaderShow>
                        <h1>새로운 이슈 작성</h1>
                    </HeaderShow>
                </TitleContainer>

                <ContentsContainer className="contents">
                    <StyledProfile>
                        <CustomProfile src={getUserImg()} size={'medium'}></CustomProfile>
                    </StyledProfile>

                    <StyledComments>
                        <StyledTitle>
                            <PlaceholdText className="placeholdText">제목</PlaceholdText>
                            <ModifyInput type="text" value={newTitle} onChange={handleTitleChange} />
                        </StyledTitle>

                        <Content $isfocused={isNewCommentFocused}>
                            <CustomTextEditor
                                $value={newCommentArea}
                                $onChange={handleCommentChange}
                                $onFocus={handleFocus}
                                $onBlur={handleBlur}
                                $height={'500'}
                            />
                        </Content>
                    </StyledComments>

                    <SidebarContainer>
                        <OptionSidebar filterName={'assignee'} filterData={undefined} isNew={true}>
                            담당자
                        </OptionSidebar>
                        <OptionSidebar filterName={'label'} filterData={undefined} isNew={true}>
                            레이블
                        </OptionSidebar>
                        <OptionSidebar filterName={'milestone'} filterData={undefined} isNew={true}>
                            마일스톤
                        </OptionSidebar>
                    </SidebarContainer>
                </ContentsContainer>

                <MainBtnContainer>
                    <CustomButton size={'medium'} isDisabled={isNewCommetDisabled} onClick={submitNewComment}>
                        완료
                    </CustomButton>
                </MainBtnContainer>
            </StyledMainContainer>
        </StyledDetailContainer>
    );
}
const SidebarContainer = styled.div`
    flex-basis: 25%;
    min-width: 200px;
    min-height: 500px;
    border: 1px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    /* background-color: red; */
`;

const StyledTitle = styled(FlexRow)`
    position: relative;
    margin-bottom: 10px;
    width: 100%;
`;
const MainBtnContainer = styled(FlexRow)`
    justify-content: end;
    width: 100%;
    margin: 20px 0;
`;
const Content = styled.div`
    /* display: flex;
    flex-direction: column; */
    width: 100%;
    /* min-height: 200px; */
    margin-bottom: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    border-radius: 10px;
    border: 1px solid;
    border-color: ${(props) => (props.$isfocused ? 'var(--primary-color)' : props.theme.borderColor)};
    color: ${(props) => props.theme.fontColor};
    background-color: ${(props) => (props.$isfocused ? props.theme.bgColorBody : props.theme.disabledColor)};
    position: relative;
`;

const ModifyInput = styled(StyledInput)`
    width: 100%;
    height: 100%;
    padding: 10px 20px 10px 90px;
    color: var(--secondary-color);
`;
const PlaceholdText = styled.span`
    position: absolute;
    top: 15px;
    left: 15px;
    color: var(--secondary-color);
    font-size: 13px;
`;

const StyledProfile = styled(FlexCol)`
    margin-right: 2cap;
`;

const StyledComments = styled(FlexCol)`
    /* background-color: azure; */
    flex-basis: 65%;
    /* margin-right: 30px; */
    margin-right: 20px;
    min-width: 700px;
    min-height: 200px;
    /* display: flex;
    flex-direction: column; */
    justify-content: flex-start;
    align-items: center;
`;
const ContentsContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 15px;
    width: 100%;
    /* height: 700px; //??? 
    /* background-color: aquamarine; */
`;
const StyledMainContainer = styled(MainContainer)`
    /* background-color: skyblue; */
`;
const HeaderShow = styled.div`
    /* background-color: gold; */
    align-content: center;
    /* height: 50px; */
    & h1 {
        font-size: 32px;
        font-weight: 700;
    }
`;
const StyledDetailContainer = styled(IndexContainer)`
    & .title {
        position: relative;
    }
    & .title::after {
        content: '';
        position: absolute;
        width: 100%;
        height: 1px;
        left: 0;
        bottom: 0;
        background-color: ${(props) => props.theme.borderColor};
    }
    & .contents {
        position: relative;
    }
    & .contents::after {
        content: '';
        position: absolute;
        width: 100%;
        height: 1px;
        left: 0;
        bottom: 0;
        background-color: ${(props) => props.theme.borderColor};
    }
`;

const TitleContainer = styled.div`
    width: 100%;
    height: 70px;
    display: flex;
    align-items: center;
    /* background-color: antiquewhite; */
`;
