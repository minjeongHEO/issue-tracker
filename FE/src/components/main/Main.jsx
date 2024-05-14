import { Button, Input, Checkbox, Select } from 'antd';
import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import IssueList from './IssueList';
import DropDownFilter from './DropDownFilter';

// TODO: fetch 데이터
const labelTypeItems = [
    { labelColor: '#F910AC', labelName: '🖥️ BE' },
    { labelColor: '#F9D0F0', labelName: '🌐 FE' },
];
const imageTypeItems = [
    { avatarSrc: 'https://avatars.githubusercontent.com/u/96780693?s=40&v=4', userName: 'woody' },
    { avatarSrc: 'https://avatars.githubusercontent.com/u/103445254?s=40&v=4', userName: 'zzawang' },
];
const milestoneTypeItems = [{ title: '⚙️ Etc' }, { title: '💄 Style' }, { title: '🧑🏻 User' }, { title: '🎯 Issue' }];

const mainIssueFilters = [
    { title: '열린 이슈', value: 'is:open' },
    { title: '내가 작성한 이슈', value: 'author:@me' },
    { title: '나에게 할당된 이슈', value: 'assignee:@me' },
    { title: '내가 댓글을 남긴 이슈', value: 'mentions:@me' },
    { title: '닫힌 이슈', value: 'is:closed' },
];

export default function Main() {
    const navigate = useNavigate();

    return (
        <MainContainer>
            <StyledNav>
                <FlexRow>
                    <FlexRow>
                        <IssueFilter>
                            <DropDownFilter filterTitle={'필터'} filterItems={mainIssueFilters} />
                        </IssueFilter>
                        <InputFilter placeholder="is:issue is:open"></InputFilter>
                    </FlexRow>

                    <div>
                        <StyledBtn onClick={() => navigate('/labels')}>레이블()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/milestones')}>마일스톤()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/issues')}>+ 이슈작성</StyledBtn>
                    </div>
                </FlexRow>
            </StyledNav>

            <StyledBox>
                <StyledBoxHeader>
                    <div className="issue">
                        <Checkbox />
                        <span className="issueOption">열린 이슈()</span>
                        <span className="issueOption">닫힌 이슈()</span>
                    </div>
                    <div className="filter">
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'담당자'} filterItems={imageTypeItems} />
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'레이블'} filterItems={labelTypeItems} />
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'마일스톤'} filterItems={milestoneTypeItems} />
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'작성자'} filterItems={imageTypeItems} />
                        </span>
                    </div>
                </StyledBoxHeader>
                <StyledBoxBody>
                    <IssueList></IssueList>
                    <IssueList></IssueList>
                    <IssueList></IssueList>
                </StyledBoxBody>
            </StyledBox>
        </MainContainer>
    );
}

const StyledBoxBody = styled.div`
    min-height: 80px;
    /* background-color: skyblue; */
`;

const StyledBoxHeader = styled.div`
    background-color: ${(props) => props.theme.listHeaderColor};
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    color: ${(props) => props.theme.fontColor};
    border-top-left-radius: 6px;
    border-top-right-radius: 6px;

    & .issue {
        margin-left: 30px;
    }
    & .issue .issueOption {
        margin-left: 10px;
    }
    & .filter .filterOption {
        margin-right: 20px;
    }
`;

const IssueFilter = styled.div`
    /* background-color: yellow; */
    border-top-left-radius: 6px;
    border-bottom-left-radius: 6px;
    border: 1px solid;
    width: 100px;
    height: 35px;
    align-content: center;
`;
const InputFilter = styled.input`
    border-top-right-radius: 6px;
    border-bottom-right-radius: 6px;
    border: 1px solid;
    border-left: none;
    width: 400px;
    height: 35px;
    background-color: ${(props) => props.theme.listHeaderColor};
    padding-left: 30px;

    &::placeholder {
        color: ${(props) => props.theme.fontColor};
    }
`;

const StyledBox = styled.div`
    margin-top: 20px;
    width: 100%;
    min-height: 160px;
    border-radius: 6px;
    border: 1px solid;
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
`;

const StyledSelect = styled(Select)`
    width: 200px;
`;
const StyledInput = styled(Input)`
    width: 600px;
`;
const StyledBtn = styled(Button)`
    margin-left: 10px;
`;

const MainContainer = styled.main`
    /* background-color: azure; */
    height: 90%;
    width: 100%;
    min-width: 890px;
    padding-left: 85px;
    padding-right: 85px;
`;

const FlexRow = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
`;

const StyledNav = styled.nav`
    margin-top: 30px;
`;
