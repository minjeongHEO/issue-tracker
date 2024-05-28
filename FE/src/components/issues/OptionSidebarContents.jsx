import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { CustomProfile } from '../../assets/CustomProfile';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { FlexRow } from '../../styles/theme';
import { Checkbox, Radio } from 'antd';
import { useModifyIssueAssignees, useModifyIssueLabels, useModifyIssueMilestone } from '../../hooks/useIssueDetailData';
import CustomNoProfile from '../../assets/CustomNoProfile';

export default function OptionSidebarContents({ contents, filterName, filterData, checkedDatas, setCheckedDatas, issueId }) {
    const { mutate: modifyIssueLabels } = useModifyIssueLabels(String(issueId)); //labelIds
    const { mutate: modifyIssueAssignees } = useModifyIssueAssignees(String(issueId)); //assigneeIds
    const { mutate: modifyIssueMilestone } = useModifyIssueMilestone(String(issueId)); //milestoneId

    const handleCheckBox = (event) => {
        const id = event.currentTarget.dataset.id ?? '';

        if (filterName === 'milestone') {
            setCheckedDatas((prev) => {
                return { ...prev, [filterName]: id };
            });
        } else {
            setCheckedDatas((prev) => {
                if (!prev) return;
                const prevCheckedIds = prev[filterName] || [];
                const targetIdx = prevCheckedIds.indexOf(id);
                if (targetIdx === -1) return { ...prev, [filterName]: [...prevCheckedIds, id] };
                else return { ...prev, [filterName]: prevCheckedIds.filter((item) => item !== id) };
            });
        }
    };

    const isChecked = (checkedId) => {
        if (filterName === 'milestone') return checkedDatas === checkedId;
        else return checkedDatas?.includes(checkedId);
    };

    const isModifyed = (previousData, currentData) => {
        if (filterName === 'milestone') {
            return String(previousData?.id) !== currentData;
        } else {
            const previousIds = new Set(filterData.map(({ id }) => id));
            const currnetIds = new Set(currentData);

            if (previousIds.size !== currnetIds.size) return true;

            previousIds.forEach(({ id }) => {
                if (!currnetIds.has(id)) return true;
            });

            return false;
        }
    };

    const modifyActionByType = {
        assignee: () => modifyIssueAssignees({ assigneeIds: checkedDatas }),
        label: () => modifyIssueLabels({ labelIds: checkedDatas }),
        milestone: () => modifyIssueMilestone({ milestoneId: checkedDatas }),
    };

    useEffect(() => {
        return () => {
            if (isModifyed(filterData, checkedDatas)) modifyActionByType[filterName]();
        };
    }, [checkedDatas]);

    return (
        <>
            {contents.map((content) => (
                <StyledList key={content.id}>
                    <FlexRow className="itemTitle">
                        {filterName === 'assignee' && (
                            <>
                                {content.imgUrl ? <CustomProfile src={content.imgUrl} /> : <CustomNoProfile />}
                                <span className="titleName">{content.id}</span>
                            </>
                        )}
                        {filterName === 'label' && (
                            <StyledLabel backgroundColor={content.bgColor} color={content.textColor}>
                                {content.name}
                            </StyledLabel>
                        )}
                        {filterName === 'milestone' && (
                            <span className="titleName">
                                {content.name}
                                {content.isClosed && <span className="isClosed">닫힘</span>}
                            </span>
                        )}
                    </FlexRow>
                    <FlexRow className="itemRadio">
                        <Checkbox onClick={handleCheckBox} data-id={String(content.id)} checked={isChecked(String(content.id))} />
                    </FlexRow>
                </StyledList>
            ))}
        </>
    );
}

const StyledList = styled.li`
    display: flex;
    justify-content: space-between;
    padding: 5px 12px;
    width: 100%;

    & .itemTitle {
        /* width: 80px; */
    }
    & .titleName {
        margin-left: 10px;
    }
    & .isClosed {
        margin-left: 10px;
        font-size: 10px;
        font-style: italic;
    }
`;

const StyledLabel = styled(CustomLabelBadge)`
    /* height: 30px; */
    margin-right: 10px;
    font-size: 13px;
`;
