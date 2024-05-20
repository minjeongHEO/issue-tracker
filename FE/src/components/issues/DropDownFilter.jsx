import React, { useEffect, useState } from 'react';
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Menu, message, Radio, Space } from 'antd';
import styled from 'styled-components';
import { DropTitle } from '../../styles/theme.js';
import DropDownTitle from './DropDownTitle.jsx';
import { useFilterContext } from '../../context/FilterContext.jsx';

const filterMapping = {
    issue: '이슈 필터',
    state: '상태 변경',
};

export default function DropDownFilter({ filterTitle, filterItems, dispatchTypeByFilterContents, children }) {
    const [selectedKey, setSelectedKey] = useState(null);
    const { state: selectedFilters, dispatch } = useFilterContext();

    const filterReset = (filterOject, keys) => {
        return Object.entries(filterOject)
            .filter(([key, value]) => !keys.includes(key))
            .every(([key, value]) => value === null);
    };

    const isResetFilters = (selectedFilters) => {
        if (!selectedFilters || Object.keys(selectedFilters).length === 0) return;

        const issues = selectedFilters.issues || {};
        const isResetIssueFilters = filterReset(issues, ['isOpen', 'isClosed']);
        const isResetRestFilters = filterReset(selectedFilters, ['issues']);

        return isResetIssueFilters && isResetRestFilters;
    };

    useEffect(() => {
        if (isResetFilters(selectedFilters)) setSelectedKey(null);
    }, [selectedFilters]);

    const dispatchTypeByFilter = {
        author: 'SET_SELECTED_AUTHOR_FILTER',
        label: 'SET_SELECTED_LABEL_FILTER',
        milestone: 'SET_SELECTED_MILESTONE_FILTER',
        assignee: 'SET_SELECTED_ASSIGNEE_FILTER',
    };

    const handleMenuClick = ({ key }) => {
        setSelectedKey(key);
        message.info(`${key === 'null' ? `${children} 초기화` : key} 필터 선택`);

        if (filterTitle === 'issue') {
            dispatch({ type: dispatchTypeByFilterContents[key], payload: key });
        } else {
            dispatch({ type: dispatchTypeByFilter[filterTitle], payload: key });
        }
    };

    const dropBoxTitle = () => filterMapping[filterTitle] || `${children} 필터`;

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
                    <Radio checked={selectedKey === 'no'} onChange={() => setSelectedKey('no')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: 'no',
    };

    const defaultTypeItems = () => {
        return filterItems
            ? filterItems.reduce((acc, cur) => {
                  acc.push({
                      label: (
                          <ItemContainer>
                              <div className="itemTitle">
                                  <DropTitle>{cur.title}</DropTitle>
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
                                      <AvatarImage src={cur.avatarSrc}></AvatarImage>
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
        <StyledDropdown menu={{ items, onClick: handleMenuClick }} getPopupContainer={(triggerNode) => triggerNode.parentNode} trigger={['click']}>
            <a onClick={(e) => e.preventDefault()}>
                <Space>
                    {children}
                    <DownOutlined />
                </Space>
            </a>
        </StyledDropdown>
    );
}

//TODO:
//3. 다크모드 테마에 따른 색상변경

const StyledColor = styled.div`
    border-radius: 50%;
    width: 20px;
    height: 20px;
`;
const AvatarImage = styled.img`
    border-radius: 50%;
    width: 20px;
    height: 20px;
    border: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.bgColorBody};
    display: block;
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
