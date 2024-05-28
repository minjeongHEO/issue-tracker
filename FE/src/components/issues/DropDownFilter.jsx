import React, { useEffect, useState } from 'react';
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Menu, message, Radio, Space } from 'antd';
import styled from 'styled-components';
import { DropTitle } from '../../styles/theme.js';
import DropDownTitle from './DropDownTitle.jsx';
import { useFilterContext } from '../../context/FilterContext.jsx';
import { CustomProfile } from '../../assets/CustomProfile.jsx';
import CustomNoProfile from '../../assets/CustomNoProfile.jsx';
import { useIssueListStateToggle } from '../../hooks/useIssueDetailData.js';

const filterMapping = {
    issue: '이슈 필터',
    state: '상태 변경',
};

const dispatchTypeByFilter = {
    author: 'SET_SELECTED_AUTHOR_FILTER',
    label: 'SET_SELECTED_LABEL_FILTER',
    milestone: 'SET_SELECTED_MILESTONE_FILTER',
    assignee: 'SET_SELECTED_ASSIGNEE_FILTER',
};

export default function DropDownFilter({ filterTitle, filterItems, dispatchTypeByFilterContents, checkedItems, setCheckedItems, children }) {
    const { mutate: toggleIssueState } = useIssueListStateToggle();
    const [selectedKey, setSelectedKey] = useState(null);
    const { state: selectedFilters, dispatch } = useFilterContext();

    const filterReset = (filterOject, keys) => {
        return Object.entries(filterOject)
            .filter(([key, _]) => !keys.includes(key))
            .every(([_, value]) => value === null);
    };

    const isResetFilters = (selectedFilters) => {
        if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

        const issues = selectedFilters.issues || {};
        const isResetIssueFilters = filterReset(issues, ['isOpen', 'isClosed']);
        const isResetRestFilters = filterReset(selectedFilters, ['issues']);

        return isResetIssueFilters && isResetRestFilters;
    };

    const isClosedState = (selectedFilters) => {
        if (!selectedFilters || Object.keys(selectedFilters).length === 0 || !selectedFilters.issues) return;

        const issues = selectedFilters.issues || {};
        if (issues.isOpen === null && issues.isClosed === null) return false;
        else return Object.entries(issues).filter(([key, value]) => (key === 'isOpen' || key === 'isClosed') && value !== null)[0][0] === 'isClosed';
    };

    const getIssueFilterTitle = (key) => filterItems.find(({ value }) => value === key).title;

    const handleStateFilter = (checkedItems) => {
        const isClosed = isClosedState(selectedFilters); //true면 닫힌이슈상태임
        message.info(`선택한 이슈를 ${isClosed ? '열었습니다.' : '닫았습니다.'}`);

        if (isClosed) {
            setCheckedItems([]);
            toggleIssueState({ toIssueState: false, issueIds: checkedItems }); //이슈열기
        } else {
            setCheckedItems([]);
            toggleIssueState({ toIssueState: true, issueIds: checkedItems }); //이슈 닫기
        }
    };

    const handleIssueFilter = (key) => {
        message.info(`${getIssueFilterTitle(key)} 필터 선택`);
        dispatch({ type: dispatchTypeByFilterContents[key], payload: key });
    };

    const handleDefaultFilter = (filterTitle, key) => {
        message.info(`${key === 'null' ? `${children} 초기화` : key} 필터 선택`);
        dispatch({ type: dispatchTypeByFilter[filterTitle], payload: key });
    };

    const handleMenuClick = (key, checkedItems) => {
        setSelectedKey(key);

        switch (filterTitle) {
            case 'state':
                handleStateFilter(checkedItems);
                break;

            case 'issue':
                handleIssueFilter(key);
                break;

            default:
                handleDefaultFilter(filterTitle, key);
                break;
        }
    };

    const dropBoxTitle = () => filterMapping[filterTitle] || `${children} 필터`;

    useEffect(() => {
        if (isResetFilters(selectedFilters)) setSelectedKey(null);
    }, [selectedFilters]);

    const titleItem = {
        label: (
            <ItemContainer>
                <div className="itemTitle">
                    <DropTitle>{dropBoxTitle()}</DropTitle>
                </div>
            </ItemContainer>
        ),
        key: '1',
        disabled: true,
    };

    const clearTypeItem = {
        label: (
            <ItemContainer>
                <div className="itemTitle">
                    <DropDownTitle name={children} filterTitle={filterTitle}>
                        <DropTitle />
                    </DropDownTitle>
                </div>
                <div className="ItemRadio">
                    <Radio checked={selectedKey === 'nonSelected'} onChange={() => setSelectedKey('nonSelected')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: 'nonSelected',
    };

    const defaultTypeItems = () => {
        return filterItems
            ? filterItems.reduce((acc, cur) => {
                  acc.push({
                      label: (
                          <ItemContainer>
                              <div className="itemTitle">
                                  <DropTitle>
                                      {cur.title} {filterTitle === 'state' && isClosedState(selectedFilters) ? '열기' : '닫기'}
                                  </DropTitle>
                              </div>
                              <div className="ItemRadio">
                                  <Radio
                                      checked={selectedKey === (cur.value ?? cur.title)}
                                      onChange={() => setSelectedKey(cur.value ?? cur.title)}
                                  ></Radio>
                              </div>
                          </ItemContainer>
                      ),
                      key: cur.value ?? cur.title,
                  });
                  return acc;
              }, [])
            : [];
    };

    const imageTypeItems = () => {
        return filterItems
            ? filterItems.reduce((acc, cur) => {
                  acc.push({
                      label: (
                          <ItemContainer>
                              <div className="itemTitle">
                                  <DropTitle>
                                      {cur.avatarSrc ? <CustomProfile src={cur.avatarSrc} /> : <CustomNoProfile />}
                                      <UserName>{cur.userName}</UserName>
                                  </DropTitle>
                              </div>
                              <div className="ItemRadio">
                                  <Radio checked={selectedKey === cur.userName} onChange={() => setSelectedKey(cur.userName)}></Radio>
                              </div>
                          </ItemContainer>
                      ),
                      key: cur.userName,
                  });
                  return acc;
              }, [])
            : [];
    };

    const labelTypeItems = () => {
        return filterItems
            ? filterItems.reduce((acc, cur) => {
                  acc.push({
                      label: (
                          <ItemContainer>
                              <div className="itemTitle">
                                  <DropTitle>
                                      <StyledColor style={{ backgroundColor: cur.labelColor, color: cur.textColor }}></StyledColor>
                                      <UserName>{cur.labelName}</UserName>
                                  </DropTitle>
                              </div>
                              <div className="ItemRadio">
                                  <Radio checked={selectedKey === cur.labelName} onChange={() => setSelectedKey(cur.labelName)}></Radio>
                              </div>
                          </ItemContainer>
                      ),
                      key: cur.labelName,
                  });
                  return acc;
              }, [])
            : [];
    };

    const itemByType = {
        issue: [titleItem, ...defaultTypeItems()],
        assignee: [titleItem, clearTypeItem, ...imageTypeItems()],
        label: [titleItem, clearTypeItem, ...labelTypeItems()],
        milestone: [titleItem, clearTypeItem, ...defaultTypeItems()],
        author: [titleItem, ...imageTypeItems()],
        state: [titleItem, , ...defaultTypeItems()],
    };

    const items = itemByType[filterTitle];

    return (
        <StyledDropdown
            menu={{
                items,
                onClick: (e) => handleMenuClick(e.key, checkedItems),
            }}
            getPopupContainer={(triggerNode) => triggerNode.parentNode}
            trigger={['click']}
        >
            <a onClick={(e) => e.preventDefault()}>
                <Space>
                    {children}
                    <DownOutlined />
                </Space>
            </a>
        </StyledDropdown>
    );
}

const StyledColor = styled.div`
    border-radius: 50%;
    width: 20px;
    height: 20px;
`;

const StyledDropdown = styled(Dropdown)`
    cursor: pointer;
`;

const ItemContainer = styled.div`
    display: flex;
    flex-direction: row;

    & .itemTitle {
        width: 180px;
    }
`;
const UserName = styled.span`
    margin: 10px;
`;
