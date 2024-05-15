import { Button, Input, Checkbox, Select } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import IssueList from './IssueList';
import DropDownFilter from './DropDownFilter';
import { useFilterContext } from '../../context/FilterContext';

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

const dispatchTypeByFilterContents = {
    'is:open': 'SET_SELECTED_IS_OPEN_FILTER',
    'is:closed': 'SET_SELECTED_IS_CLOSED_FILTER',
    'assignee:@me': 'SET_SELECTED_AUTHOR_ME_FILTER',
    'mentions:@me': 'SET_SELECTED_ASSIGNEE_ME_FILTER',
    'author:@me': 'SET_SELECTED_MENTIONS_ME_FILTER',
};

const issueFilters = {
    isOpen: 'is:open',
    isClosed: 'is:closed',
    authorMe: 'author:@me',
    assigneeMe: 'assignee:@me',
    mentionsMe: 'mentions:@me',
};

export default function Main() {
    const navigate = useNavigate();
    const { state: selectedFilters, dispatch } = useFilterContext();
    const [inputFilter, setInputFilter] = useState('');

    const filterSelectedLists = (selectedFilters) => {
        const filters = ['is:issue'];

        if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

        // 1. 이슈 필터 처리
        const issues = selectedFilters.issues || {};
        const issueEntries = Object.entries(issues);
        const hasNotNullValue = issueEntries.some(([key, value]) => value !== null);

        if (hasNotNullValue) issueEntries.filter(([key, value]) => value !== null).forEach(([key, value]) => filters.push(issueFilters[key]));
        else filters.push(issueFilters['isOpen']);

        // 2. 나머지 필터 처리
        Object.entries(selectedFilters)
            .filter(([key, value]) => value !== null && key !== 'issues')
            .forEach(([key, value]) => filters.push(`${key}:"${value}"`));

        return filters.join(' ');
    };

    const dispatchIssue = ({ target }) => {
        const attrValue = target.getAttribute('attr-key');
        if (!attrValue) return;
        dispatch({ type: dispatchTypeByFilterContents[attrValue], payload: attrValue });
    };

    const toggleAllCheckBox = () => {};

    useEffect(() => {
        setInputFilter(filterSelectedLists(selectedFilters));
    }, [selectedFilters]);

    return (
        <MainContainer>
            <StyledNav>
                <FlexRow>
                    <FlexRow>
                        <IssueFilter>
                            <DropDownFilter
                                filterTitle={'issue'}
                                filterItems={mainIssueFilters}
                                dispatch={dispatch}
                                dispatchTypeByFilterContents={dispatchTypeByFilterContents}
                            >
                                필터
                            </DropDownFilter>
                        </IssueFilter>
                        <InputFilter value={inputFilter} onChange={(e) => setInputFilter(e.target.value)}></InputFilter>
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
                        <Checkbox onClick={toggleAllCheckBox} />
                        <span
                            className={`issueOption ${selectedFilters.issues.isClosed ? '' : `checked`}`}
                            attr-key="is:open"
                            onClick={dispatchIssue}
                        >
                            열린 이슈()
                        </span>
                        <span
                            className={`issueOption ${selectedFilters.issues.isClosed ? `checked` : ''}`}
                            attr-key="is:closed"
                            onClick={dispatchIssue}
                        >
                            닫힌 이슈()
                        </span>
                    </div>
                    <div className="filter">
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'author'} filterItems={imageTypeItems} dispatch={dispatch}>
                                담당자
                            </DropDownFilter>
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'label'} filterItems={labelTypeItems} dispatch={dispatch}>
                                레이블
                            </DropDownFilter>
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'milestone'} filterItems={milestoneTypeItems} dispatch={dispatch}>
                                마일스톤
                            </DropDownFilter>
                        </span>
                        <span className="filterOption">
                            <DropDownFilter filterTitle={'assignee'} filterItems={imageTypeItems} dispatch={dispatch}>
                                작성자
                            </DropDownFilter>
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

    & .checked {
        font-weight: bold;
    }
`;

const IssueFilter = styled.div`
    /* background-color: yellow; */
    width: 100px;
    height: 35px;
    border-top-left-radius: 6px;
    border-bottom-left-radius: 6px;
    border: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    align-content: center;
`;
const InputFilter = styled.input`
    width: 400px;
    height: 35px;
    border-top-right-radius: 6px;
    border-bottom-right-radius: 6px;
    border: 1px solid;
    border-left: none;
    border-color: ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.listHeaderColor};
    padding-left: 30px;
    color: ${(props) => props.theme.fontColor};

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
    border-color: ${(props) => props.theme.borderColor};
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
