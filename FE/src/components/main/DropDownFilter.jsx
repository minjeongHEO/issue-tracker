import React, { useEffect, useState } from 'react';
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Menu, message, Radio, Space } from 'antd';
import styled from 'styled-components';

export default function DropDownFilter({ filterTitle, filterItems }) {
    const [selectedKey, setSelectedKey] = useState(null);
    // const [selectedKey, setSelectedKey] = useState({}); //객체로 설정

    const handleMenuClick = ({ key }) => {
        setSelectedKey(key);
        message.info(`Click on item ${key}`);
    };

    const clearTypeItemTitle = {
        담당자: '담당자가 없는 이슈',
        작성자: '작성자가 없는 이슈',
        레이블: '레이블이 없는 이슈',
        마일스톤: '마일스톤이 없는 이슈',
    };

    const dropBoxTitle = () => {
        if (filterTitle === '필터') return '이슈 필터';
        return `${filterTitle} 필터`;
    };

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
                    <DropTitle>{clearTypeItemTitle[filterTitle]}</DropTitle>
                </div>
                <div className="ItemRadio">
                    <Radio checked={selectedKey === '2'} onChange={() => setSelectedKey('2')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: '2',
    };

    const defaultTypeItems = {
        label: (
            <ItemContainer>
                <div className="itemTitle">
                    <DropTitle>내용내용</DropTitle>
                </div>
                <div className="ItemRadio">
                    <Radio checked={selectedKey === '3'} onChange={() => setSelectedKey('3')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: '3',
    };

    const ImageTypeItems = {
        label: (
            <ItemContainer>
                <div className="itemTitle">
                    <DropTitle>
                        <AvatarImage src="https://avatars.githubusercontent.com/u/96780693?s=40&v=4"></AvatarImage>
                        <UserName>woody</UserName>
                    </DropTitle>
                </div>
                <div className="ItemRadio">
                    <Radio checked={selectedKey === '4'} onChange={() => setSelectedKey('4')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: '4',
    };

    const labelTypeItems = {
        label: (
            <ItemContainer>
                <div className="itemTitle">
                    <DropTitle>
                        <StyledColor style={{ backgroundColor: 'red' }}></StyledColor>
                        <UserName>bug</UserName>
                    </DropTitle>
                </div>
                <div className="ItemRadio">
                    <Radio checked={selectedKey === '3'} onChange={() => setSelectedKey('3')}></Radio>
                </div>
            </ItemContainer>
        ),
        key: '3',
    };

    const itemByType = {
        필터: [titleItem, defaultTypeItems],
        담당자: [titleItem, clearTypeItem, ImageTypeItems],
        작성자: [titleItem, clearTypeItem, ImageTypeItems],
        레이블: [titleItem, clearTypeItem, labelTypeItems],
        마일스톤: [titleItem, clearTypeItem, defaultTypeItems],
    };

    const items = itemByType[filterTitle];

    return (
        <StyledDropdown menu={{ items, onClick: handleMenuClick }} trigger={['click']}>
            <a onClick={(e) => e.preventDefault()}>
                <Space>
                    {filterTitle}
                    <DownOutlined />
                </Space>
            </a>
        </StyledDropdown>
    );
}

//TODO:
//1. 변하는 것들을 props로 넘겨주는거
//2. itmes 배열을 prop에 따라서 각각 만들어주는거?
//3. 다크모드 테마에 따른 색상변경

const StyledColor = styled.div`
    border-radius: 50%;
    width: 20px;
    height: 20px;
`;
const AvatarImage = styled.img`
    border-radius: 50%;
    width: 20px;
`;

const StyledDropdown = styled(Dropdown)``;

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
const DropTitle = styled.span`
    margin-left: 10px;
    margin-right: 10px;
    height: 20px;
    display: flex;
    align-items: center;
`;
