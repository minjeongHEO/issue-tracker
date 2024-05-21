import React from 'react';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconArchive } from '../../assets/icons/IconArchive';
import { Button } from 'antd';
import styled from 'styled-components';
import { StyledInput } from '../../styles/theme';
import { CustomButton } from '../../assets/CustomButton';

export default function IssueDetailTitle({ editState, toggleEditState }) {
    return (
        <>
            {editState ? (
                <form>
                    <FlexRow>
                        <PlaceholdText className="placeholdText">제목</PlaceholdText>
                        <ModifyInput type="text" value="이슈 상세" />
                        <HeaderAction>
                            <StyledBtn size={'medium'} type={'outline'}>
                                <IconEdit />
                                <span onClick={toggleEditState}>편집 취소</span>
                            </StyledBtn>
                            <StyledBtn size={'medium'} type={'outline'}>
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
                        <StyledBtn size={'medium'} type={'outline'}>
                            <IconEdit />
                            <span onClick={toggleEditState}>제목 편집</span>
                        </StyledBtn>
                        <StyledBtn size={'medium'} type={'outline'}>
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

const StyledBtn = styled(CustomButton)`
    height: 40px;
    margin-left: 10px;
`;
