import { Button, Input, Menu, Select } from 'antd';
import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

export default function Main() {
    const navigate = useNavigate();

    const selectBefore = (
        <StyledSelect defaultValue="필터">
            <Option value="is:open">열린 이슈</Option>
            <Option value="is:write">내가 작성한 이슈</Option>
            <Option value="is:assigned">나에게 할당된 이슈</Option>
            <Option value="is:comment">내가 댓글을 남긴 이슈</Option>
            <Option value="is:closed">닫힌 이슈</Option>
        </StyledSelect>
    );

    return (
        <MainContainer>
            <StyledNav>
                <FlexRow>
                    <StyledInput addonBefore={selectBefore} defaultValue="is:issue is:open" />
                    <div>
                        <StyledBtn onClick={() => navigate('/labels')}>레이블()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/milestones')}>마일스톤()</StyledBtn>
                        <StyledBtn onClick={() => navigate('/issues')}>+ 이슈작성</StyledBtn>
                    </div>
                </FlexRow>
            </StyledNav>
        </MainContainer>
    );
}

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
