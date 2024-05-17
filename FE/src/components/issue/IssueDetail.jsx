import React from 'react';
import { useParams } from 'react-router-dom';
import Header from '../header/Header';
import { IndexContainer } from '../../styles/theme';
import styled from 'styled-components';
import { MainContainer } from '../../styles/theme';
import { Button } from 'antd';
import { IconEdit } from '../../assets/icons/IconEdit';
import Archive from '../../assets/archive.svg';

export default function IssueDetail() {
    let { id } = useParams();

    return (
        <IndexContainer>
            <Header />
            <MainContainer>
                <TitleContainer>
                    {/* display:none */}
                    <HeaderShow>
                        <FlexRow>
                            <h1>
                                이슈 상세<span className="issueId">#41</span>
                            </h1>
                            <HeaderAction>
                                <StyledBtn>
                                    <StyledIconEdit>wooody</StyledIconEdit> 이슈 닫기
                                </StyledBtn>
                            </HeaderAction>
                        </FlexRow>
                        <HeaderSummary>
                            <StyledIssueState>
                                {/* <StyledAlertIcon /> */}
                                <span>열린 이슈</span>
                            </StyledIssueState>
                            <div>
                                <span>이 이슈가 3분전에 woody님에 의해서 열렸습니다.</span>💭<span>코멘트 1개</span>
                            </div>
                        </HeaderSummary>
                    </HeaderShow>
                    {/* display:none */}
                    <HeaderEdit>
                        <FlexRow>
                            <h1>
                                이슈 상세<span>#41</span>
                            </h1>
                            <HeaderAction></HeaderAction>
                        </FlexRow>
                    </HeaderEdit>
                </TitleContainer>

                <ContentsContainer>
                    <Comments>
                        <Comment>댓글</Comment>
                        <Comment>댓글</Comment>
                    </Comments>
                    <Filters>
                        <Filter>담당자</Filter>
                        <Filter>레이블</Filter>
                        <Filter>마일스톤</Filter>
                        이슈삭제
                    </Filters>
                </ContentsContainer>
            </MainContainer>
        </IndexContainer>
    );
}

const StyledButton = styled(Button)`
    border-color: var(--primary-color);
`;

const StyledIssueState = styled.div`
    background-color: var(--primary-color);
    color: var(--font-color);
    height: 30px;
    width: 100px;
    border: 1px solid var(--font-color);
    border-radius: 20px;
    align-content: center;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
`;
const StyledBtn = styled(Button)`
    height: 40px;
    background-color: white;
    color: var(--primary-color);
    border-color: var(--primary-color);
`;
const HeaderSummary = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
`;
const HeaderAction = styled.div`
    display: flex;
    flex-direction: row;
`;
const FlexRow = styled.div`
    display: flex;
    flex-direction: row;
`;
const DefaultTitle = styled.div``;
const ModifyTitle = styled.input``;

const HeaderShow = styled.div`
    & h1 {
        font-size: 32px;
        font-weight: 700;
    }
    & .issueId {
        margin-left: 10px;
        color: #6e7191;
    }
`;
const HeaderEdit = styled.div``;

const TitleContainer = styled.div`
    width: 100%;
    height: 100px;
    background-color: antiquewhite;
`;
const ContentsContainer = styled.div`
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 700px;
    background-color: aquamarine;
`;
const Comments = styled.div`
    background-color: azure;
    width: 700px;
    height: 200px;
`;
const Comment = styled.div`
    background-color: #fff;
    width: 90%;
    min-height: 100px;
    margin-bottom: 10px;
`;
const Filters = styled.div`
    background-color: beige;
    width: 200px;
    height: 200px;
`;
const Filter = styled.div`
    background-color: #fff;
    width: 90%;
    min-height: 100px;
    margin-bottom: 10px;
`;
const StyledIconEdit = styled(IconEdit)`
    color: green;
`;
