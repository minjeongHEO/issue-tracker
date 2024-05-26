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
import OptionSidebar from './OptionSidebar';

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
                <OptionSidebar filterName={'assignee'} filterData={assignees}>
                    담당자
                </OptionSidebar>
                <OptionSidebar filterName={'label'} filterData={labels}>
                    레이블
                </OptionSidebar>
                <OptionSidebar filterName={'milestone'} filterData={milestone}>
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
