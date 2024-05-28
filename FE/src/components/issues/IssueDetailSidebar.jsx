import React from 'react';
import styled from 'styled-components';
import { FlexRow } from '../../styles/theme';
import { Popconfirm, message } from 'antd';
import { useDeleteIssue } from '../../hooks/useIssueDetailData';
import { useNavigate } from 'react-router-dom';
import OptionSidebar from './OptionSidebar';
import { IconTrash } from '../../assets/icons/IconTrash';

export default function IssueDetailSidebar({ milestone, assignees, labels, issueId, isEditable = false }) {
    const navigate = useNavigate();
    const onSuccess = () => {
        message.success('삭제되었습니다.');
        navigate('/');
    };
    const { mutate: deleteIssue } = useDeleteIssue(issueId, onSuccess);

    const deleteConfirm = () => deleteIssue();

    return (
        <StyledDiv>
            <SidebarContainer>
                <OptionSidebar filterName={'assignee'} filterData={assignees} issueId={issueId}>
                    담당자
                </OptionSidebar>
                <OptionSidebar filterName={'label'} filterData={labels} issueId={issueId}>
                    레이블
                </OptionSidebar>
                <OptionSidebar filterName={'milestone'} filterData={milestone} issueId={issueId}>
                    마일스톤
                </OptionSidebar>
            </SidebarContainer>

            {isEditable && (
                <DeleteContentContainer>
                    <Popconfirm title="이슈를 삭제하시겠습니까?" onConfirm={deleteConfirm} okText="Yes" cancelText="No">
                        <div>
                            <IconTrash />
                            <span>이슈 삭제</span>
                        </div>
                    </Popconfirm>
                </DeleteContentContainer>
            )}
        </StyledDiv>
    );
}

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
