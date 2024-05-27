import React, { useEffect, useState } from 'react';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconArchive } from '../../assets/icons/IconArchive';
import styled from 'styled-components';
import { StyledInput } from '../../styles/theme';
import { CustomButton } from '../../assets/CustomButton';
import { useIssueStateToggle, useModifyIssueTitle } from '../../hooks/useIssueDetailData';

export default function IssueDetailTitle({ editState, toggleEditState, id, title, isClosed }) {
    const { mutate: toggleIssueState } = useIssueStateToggle(String(id));
    const { mutate: modifyIssuetitle } = useModifyIssueTitle(String(id));

    const [modifyTitle, setModifyTitle] = useState(title);
    const [isTitleDisabled, setIsTitleDisabled] = useState(false);

    const handleTitleChange = ({ target }) => {
        const { value } = target;
        setModifyTitle(value);
    };

    const submitModifyTitle = () => {
        modifyIssuetitle({ title: modifyTitle });
        toggleEditState();
    };

    useEffect(() => {
        if (modifyTitle.length > 0) setIsTitleDisabled(false);
        else setIsTitleDisabled(true);
    }, [modifyTitle]);

    return (
        <>
            {editState ? (
                <form>
                    <FlexRow>
                        <PlaceholdText className="placeholdText">제목</PlaceholdText>
                        <ModifyInput type="text" value={modifyTitle} onChange={handleTitleChange} />
                        <HeaderAction>
                            <StyledBtn size={'medium'} type={'outline'} isDisabled={false} onClick={toggleEditState}>
                                <IconEdit />
                                <span>편집 취소</span>
                            </StyledBtn>
                            <StyledBtn size={'medium'} type={'outline'} isDisabled={isTitleDisabled} onClick={submitModifyTitle}>
                                <IconArchive />
                                <span>편집 완료</span>
                            </StyledBtn>
                        </HeaderAction>
                    </FlexRow>
                </form>
            ) : (
                <FlexRow>
                    <h1>
                        {title}
                        <span className="issueId">#{id}</span>
                    </h1>
                    <HeaderAction>
                        <StyledBtn size={'medium'} type={'outline'} isDisabled={false}>
                            <IconEdit />
                            <span onClick={toggleEditState}>제목 편집</span>
                        </StyledBtn>
                        {isClosed ? (
                            <StyledBtn
                                size={'medium'}
                                type={'outline'}
                                isDisabled={false}
                                onClick={() => toggleIssueState({ toIssueState: false, issueIds: [id] })}
                            >
                                <IconArchive />
                                <span>이슈 열기</span>
                            </StyledBtn>
                        ) : (
                            <StyledBtn
                                size={'medium'}
                                type={'outline'}
                                isDisabled={false}
                                onClick={() => toggleIssueState({ toIssueState: true, issueIds: [id] })}
                            >
                                <IconArchive />
                                <span>이슈 닫기</span>
                            </StyledBtn>
                        )}
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
