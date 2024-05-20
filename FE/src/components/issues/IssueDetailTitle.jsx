import React from 'react';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconArchive } from '../../assets/icons/IconArchive';
import { Button } from 'antd';
import styled from 'styled-components';
import { StyledInput } from '../../styles/theme';

export default function IssueDetailTitle({ editState, toggleEditState }) {
    return (
        <>
            {editState ? (
                <form>
                    <FlexRow>
                        <PlaceholdText className="placeholdText">제목</PlaceholdText>
                        <ModifyInput type="text" value="이슈 상세" />
                        <HeaderAction>
                            <StyledBtn>
                                <IconEdit />
                                <span>편집 취소</span>
                            </StyledBtn>
                            <StyledBtn>
                                <IconArchive />
                                <span>편집 완료</span>
                            </StyledBtn>
                        </HeaderAction>
                    </FlexRow>
                </form>
            ) : (
                <FlexRow>
                    <h1>
                        이슈 상세<span className="issueId">#41</span>
                    </h1>
                    <HeaderAction>
                        <StyledBtn>
                            <IconEdit />
                            <span onClick={toggleEditState}>제목 편집</span>
                        </StyledBtn>
                        <StyledBtn>
                            <IconArchive />
                            <span>이슈 닫기</span>
                        </StyledBtn>
                    </HeaderAction>
                </FlexRow>
            )}
        </>
    );
}

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
const HeaderAction = styled.div`
    display: flex;
    flex-direction: row;
`;
const FlexRow = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
`;
const StyledBtn = styled(Button)`
    height: 40px;
    background-color: white;
    color: var(--primary-color);
    border-color: var(--primary-color);
    margin-left: 10px;
    & span {
        margin-left: 5px;
    }
`;
