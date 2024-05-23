import React from 'react';
import styled from 'styled-components';
import { IconPlus } from '../../assets/icons/IconPlus';
import { FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconProgressBar } from '../../assets/icons/IconProgressBar';
import { IconTrash } from '../../assets/icons/IconTrash';
import { Popconfirm, message } from 'antd';
import { useDeleteIssue } from '../../hooks/useIssueDetailData';
import { Link, useNavigate } from 'react-router-dom';

export default function IssueDetailSidebar({ milestone, assignees, labels, issueId }) {
    const navigate = useNavigate();
    const onSuccess = () => {
        message.success('삭제되었습니다.');
        navigate('/');
    };
    const { mutate: deleteIssue } = useDeleteIssue(issueId, onSuccess);

    const openIssueCount = milestone?.openIssueCount ?? 0;
    const closedIssueCount = milestone?.closedIssueCount ?? 0;

    const calulatePercentage = (openCount, closedCount) => (closedCount / (openCount + closedCount)).toFixed(2);
    const deleteConfirm = () => deleteIssue();

    return (
        <StyledDiv>
            <SidebarContainer>
                <Filter>
                    <FilterTitle>
                        <div>담당자</div>
                        <IconPlus />
                    </FilterTitle>
                    {assignees.map(({ id, imgUrl }) => (
                        <FilterContentContainer key={id}>
                            <CustomProfile src={imgUrl} alt={'assineeProfile'} />
                            <span className="userName">{id}</span>
                        </FilterContentContainer>
                    ))}
                </Filter>
                <StyledLine />
                <Filter>
                    <FilterTitle>
                        <div>레이블</div>
                        <IconPlus />
                    </FilterTitle>
                    <LabelContentContainer>
                        {labels.map(({ id, name, description, textColor, bgColor }) => (
                            <StyledLabel key={id} backgroundColor={bgColor} color={textColor}>
                                {name}
                            </StyledLabel>
                        ))}
                    </LabelContentContainer>
                </Filter>
                <StyledLine />
                <Filter>
                    <FilterTitle>
                        <div>마일스톤</div>
                        <IconPlus />
                    </FilterTitle>
                    {milestone && (
                        <>
                            <FilterContentContainer>
                                <IconProgressBar percentage={calulatePercentage(openIssueCount, closedIssueCount)} />
                            </FilterContentContainer>
                            <FilterContentContainer>{milestone.name}</FilterContentContainer>
                        </>
                    )}
                </Filter>
            </SidebarContainer>
            <DeleteContentContainer>
                <Popconfirm title="이슈를 삭제하시겠습니까?" onConfirm={deleteConfirm} okText="Yes" cancelText="No">
                    <div>
                        <IconTrash />
                        <span>이슈 삭제</span>
                    </div>
                </Popconfirm>
            </DeleteContentContainer>
        </StyledDiv>
    );
}

const StyledLabel = styled(CustomLabelBadge)`
    /* height: 30px; */
    margin-right: 10px;
    font-size: 13px;
`;

const StyledLine = styled.div`
    width: 100%;
    height: 1px;
    border-bottom: 1px solid ${(props) => props.theme.borderColor};
`;

const LabelContentContainer = styled.div`
    display: flex;
    flex-wrap: wrap;
    margin: 20px 0;
`;

const DeleteContentContainer = styled(FlexRow)`
    width: 100%;
    margin: 20px 0 10px 0;
    justify-content: right;
    color: red;
    div {
        cursor: pointer;
    }
    span {
        margin-left: 5px;
    }
`;
const FilterContentContainer = styled(FlexRow)`
    flex-basis: 30%;
    margin: 10px 0 10px 0;
    justify-content: left;

    & .userName {
        margin-left: 10px;
    }
`;

const FilterTitle = styled(FlexRow)`
    font-size: 15px;
    margin-bottom: 15px;
    svg {
        width: 20px;
    }
`;
const Filter = styled.div`
    min-height: 100px;
    margin: 35px 30px;
`;

const SidebarContainer = styled.div`
    flex-basis: 30%;
    min-width: 200px;
    min-height: 500px;
    border: 1px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    /* background-color: red; */
`;

const StyledDiv = styled.div`
    flex-basis: 30%;
`;
