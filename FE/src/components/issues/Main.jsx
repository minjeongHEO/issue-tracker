import { Button, Input, Checkbox, Select } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import IssueList from './IssueList';
import DropDownFilter from './DropDownFilter';
import { useFilterContext } from '../../context/FilterContext';
import mockIssueList from '../../data/issueList.json';
import NavStateType from './NavStateType';
import NavFilterType from './NavFilterType';
import { MainContainer } from '../../styles/theme';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useFiltersData } from '../../hooks/useFiltersData';

// TODO: fetch 데이터
const labelTypeItems = [
    { labelColor: '#F910AC', labelName: '🖥️ BE' },
    { labelColor: '#F9D0F0', labelName: '🌐 FE' },
];
const imageTypeItems = [
    { avatarSrc: 'https://avatars.githubusercontent.com/u/96780693?s=40&v=4', userName: 'woody' },
    { avatarSrc: 'https://avatars.githubusercontent.com/u/103445254?s=40&v=4', userName: 'zzawang' },
];

const stateModifyFilters = [{ title: '선택한 이슈 열기' }, { title: '선택한 이슈 닫기' }];
// TODO: ----------------------------------

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

const initFilterItems = {
    labels: [],
    members: [],
    milestones: [],
};
export default function Main() {
    const navigate = useNavigate();
    const { state: selectedFilters, dispatch } = useFilterContext();
    const [inputFilter, setInputFilter] = useState('');
    const [isClearFilter, setIsClearFilter] = useState(false);
    const [checkedItems, setCheckedItems] = useState([]);

    const [filterItemsByType, setFilterItemsByType] = useState(initFilterItems);

    const filterResults = useFiltersData();
    const [labelsResult, membersResult, milestonesOpenResult, milestonesClosedResult] = filterResults;

    // const { isLoading, error, data: products } = useFiltersData();
    // const client = useQueryClient();
    //     <button onClick={() => client.invalidateQueries({ queryKey: ['label'] })}>update</button>

    const clearFilter = () => dispatch({ type: 'SET_CLEAR_FILTER', payload: '' });

    const isFilterActive = () => {
        const selectedLists = filterSelectedLists(selectedFilters);
        if (selectedLists.length === 2 && selectedLists.includes('is:open')) return false;
        return true;
    };

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

        return filters;
    };

    const toggleEntireCheckBox = () => {
        if (checkedItems.length === mockIssueList.length) setCheckedItems([]);
        else setCheckedItems(mockIssueList.map(({ id }) => id));
    };

    const isSingleChecked = (key) => {
        return checkedItems.includes(key);
    };

    useEffect(() => {
        setInputFilter(filterSelectedLists(selectedFilters).join(' '));
        setIsClearFilter(isFilterActive());
    }, [selectedFilters]);

    useEffect(() => {
        if (filterResults.some((result) => !result.data)) return;

        const milestoneOpenItems = milestonesOpenResult.data.milestoneDetailDtos.map(({ name }) => ({
            title: name,
        }));
        const milestoneClosedItems = milestonesClosedResult.data.milestoneDetailDtos.map(({ name }) => ({
            title: name,
        }));

        setFilterItemsByType((prev) => ({ ...prev, milestones: [...milestoneOpenItems, ...milestoneClosedItems] }));
    }, [labelsResult.data, membersResult.data, milestonesOpenResult.data, milestonesClosedResult.data]);

    return (
        <MainContainer>
            <StyledNav>
                <FlexRow>
                    <FlexRow className="inputFilter">
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

                    <NavBtnContainer>
                        <StyledBtn onClick={() => navigate('/labels')}>레이블()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/milestones')}>마일스톤()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/issues/new')} type="primary">
                            + 이슈작성
                        </StyledBtn>
                    </NavBtnContainer>
                </FlexRow>

                <div className={`clearBtn ${isClearFilter ? 'visible' : 'hidden'}`} style={{ visibility: isClearFilter ? 'visible' : 'hidden' }}>
                    <span onClick={clearFilter}>🧹 현재의 검색 필터 및 정렬 지우기</span>
                </div>
            </StyledNav>

            <StyledBox>
                <StyledBoxHeader>
                    <Checkbox onClick={() => toggleEntireCheckBox()} checked={checkedItems.length === mockIssueList.length} className="checkbox" />
                    {checkedItems.length > 0 ? (
                        <NavStateType
                            checkedItemsCount={checkedItems.length}
                            stateModifyFilters={stateModifyFilters}
                            dispatch={dispatch}
                        ></NavStateType>
                    ) : (
                        <NavFilterType
                            dispatchTypeByFilterContents={dispatchTypeByFilterContents}
                            imageTypeItems={imageTypeItems}
                            labelTypeItems={labelTypeItems}
                            milestoneTypeItems={filterItemsByType.milestones}
                            dispatch={dispatch}
                            ischecked={selectedFilters.issues.isClosed}
                        ></NavFilterType>
                    )}
                </StyledBoxHeader>

                <StyledBoxBody>
                    {mockIssueList.map((list) => (
                        <IssueList
                            key={list.id}
                            isSingleChecked={isSingleChecked(list.id)}
                            setCheckedItems={setCheckedItems}
                            toggleEntireCheckBox={toggleEntireCheckBox}
                            listData={list}
                        />
                    ))}
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
    /* justify-content: space-between; */
    align-items: center;
    height: 60px;
    color: ${(props) => props.theme.fontColor};
    border-top-left-radius: 6px;
    border-top-right-radius: 6px;

    & .click {
        cursor: pointer;
    }

    & .checkbox {
        margin-left: 30px;
    }

    & .issue {
        width: 200px;
    }
    & .issue.state {
        width: 145px;
    }
    & .filter {
        width: 385px;
    }
    & .filter.state {
        width: 135px;
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
    width: 100%;
    height: 35px;
    border-top-right-radius: 6px;
    border-bottom-right-radius: 6px;
    border: 1px solid;
    border-left: none;
    border-color: ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.listHeaderColor};
    padding-left: 30px;
    padding-right: 15px;
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

const StyledBtn = styled(Button)`
    margin-left: 10px;
`;

const FlexRow = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    & .inputFilter {
        flex-basis: 70%;
    }
`;

const StyledNav = styled.nav`
    margin-top: 30px;

    & .clearBtn {
        text-align: left;
        margin-top: 15px;
        font-size: 12px;
        opacity: 0;
        visibility: hidden;
        transition: opacity 0.5s ease, visibility 0.5s ease;
    }
    & .clearBtn.visible {
        opacity: 1;
        visibility: visible;
    }

    & .clearBtn span {
        cursor: pointer;
    }
`;

const NavBtnContainer = styled.div`
    width: 380px;
`;
