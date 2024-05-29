import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { IconPlus } from '../../assets/icons/IconPlus';
import { CustomProfile } from '../../assets/CustomProfile';
import { IconProgressBar } from '../../assets/icons/IconProgressBar';
import { getProgressPercentage } from '../../utils/issueUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { FlexRow } from '../../styles/theme';
import { useLabelsFilter, useMembersFilter, useMilestonesFilter, useMilestonesFilterIsClosed } from '../../hooks/useFiltersData';
import OptionSidebarContents from './OptionSidebarContents';
import CustomNoProfile from '../../assets/CustomNoProfile';

const initActivePopup = {
    assignee: false,
    label: false,
    milestone: false,
};
const initCheckedData = {
    assignee: [],
    label: [],
    milestone: '',
};

export default function OptionSidebar({ filterName, filterData, issueId, children, isNew = false }) {
    const openIssueCount = filterData?.openIssueCount ?? 0;
    const closedIssueCount = filterData?.closedIssueCount ?? 0;
    const popupRef = useRef(null);

    const [checkedDatas, setCheckedDatas] = useState(initCheckedData);

    const [isActivePopup, setIsActivePopup] = useState(initActivePopup);
    const [isAssigneeFetchPossible, setIsAssigneeFetchPossible] = useState(false);
    const [isLabelFetchPossible, setIsLabelFetchPossible] = useState(false);
    const [isMilestoneFetchPossible, setIsMilestoneFetchPossible] = useState(false);
    const { data: assigneesData } = useMembersFilter({
        enabled: isAssigneeFetchPossible,
    });
    const { data: labelsData } = useLabelsFilter({
        enabled: isLabelFetchPossible,
    });
    const { data: openMilestonesData } = useMilestonesFilter({
        enabled: isMilestoneFetchPossible,
    });
    const { data: closedMilestonesData } = useMilestonesFilterIsClosed({
        enabled: isMilestoneFetchPossible,
    });

    const getPopupTitle = {
        assignee: '담당자 필터',
        label: '레이블 필터',
        milestone: '마일스톤 필터',
    };
    const setActivePopup = (type, toggleState) => setIsActivePopup({ ...initActivePopup, [type]: toggleState });

    const toggleDropDown = (type) => {
        const toggleState = !isActivePopup[type];
        setActivePopup(type, toggleState);
    };

    const toggleEnableByType = {
        assignee: () => setIsAssigneeFetchPossible(true), //담당자
        label: () => setIsLabelFetchPossible(true),
        milestone: () => setIsMilestoneFetchPossible(true),
    };

    const handleMouseEnter = (type) => {
        // setIsActivePopup(true);
        toggleEnableByType[type]();
    };

    const getActivePopupType = (isActivePopup) => {
        if (!isActivePopup) return;
        return Object.entries(isActivePopup).find(([key, value]) => value === true)?.[0];
    };

    const setCheckDatasByType = (type, checkedDatas) => {
        setCheckedDatas((prev) => ({ ...prev, [type]: checkedDatas }));
    };

    const getCorrectAssignee = (checkedId) => assigneesData.filter(({ id }) => id === checkedId)?.[0];
    const getCorrectLabel = (checkedId) => labelsData.labels.filter(({ id }) => String(id) === checkedId)?.[0];
    const getCorrectMilestone = (checkedId) =>
        [...openMilestonesData.milestoneDetailDtos, ...closedMilestonesData.milestoneDetailDtos].filter(({ id }) => String(id) === checkedId)?.[0];

    useEffect(() => {
        if (!filterData) return;

        if (filterName === 'milestone') setCheckDatasByType('milestone', String(filterData.id));
        else
            setCheckDatasByType(
                filterName,
                filterData?.map(({ id }) => String(id))
            );
    }, [filterData]);

    useEffect(() => {
        const handleClickOutside = ({ target }) => {
            if (popupRef.current && !popupRef.current.contains(target)) {
                setIsActivePopup({ ...initActivePopup });
            }
        };
        document.addEventListener('mousedown', handleClickOutside);

        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, [popupRef]);

    return (
        <>
            <Filter>
                <FilterTitle
                    onClick={() => toggleDropDown(filterName)}
                    onMouseEnter={() => {
                        handleMouseEnter(filterName);
                    }}
                >
                    <div>{children}</div>

                    <IconPlus />
                </FilterTitle>

                {filterName === 'assignee' &&
                    isNew &&
                    checkedDatas.assignee.map((id) => {
                        const correctAssignee = getCorrectAssignee(id);
                        return (
                            <FilterContentContainer key={id}>
                                {correctAssignee?.imgUrl ? <CustomProfile src={correctAssignee?.imgUrl} /> : <CustomNoProfile />}
                                <span className="userName">{correctAssignee?.id}</span>
                            </FilterContentContainer>
                        );
                    })}
                {filterName === 'assignee' &&
                    !isNew &&
                    filterData?.map(({ id, imgUrl }) => (
                        <FilterContentContainer key={id}>
                            {imgUrl ? <CustomProfile src={imgUrl} /> : <CustomNoProfile />}
                            <span className="userName">{id}</span>
                        </FilterContentContainer>
                    ))}

                {filterName === 'label' && isNew && (
                    <LabelContentContainer>
                        {checkedDatas.label.map((id) => {
                            const correctLabel = getCorrectLabel(id);
                            return (
                                <StyledLabel key={id} backgroundColor={correctLabel.bgColor} color={correctLabel.textColor}>
                                    {correctLabel.name}
                                </StyledLabel>
                            );
                        })}
                    </LabelContentContainer>
                )}
                {filterName === 'label' && !isNew && (
                    <LabelContentContainer>
                        {filterData?.map(({ id, name, description, textColor, bgColor }) => (
                            <StyledLabel key={id} backgroundColor={bgColor} color={textColor}>
                                {name}
                            </StyledLabel>
                        ))}
                    </LabelContentContainer>
                )}

                {filterName === 'milestone' && isNew && openMilestonesData && closedMilestonesData && (
                    <>
                        <FilterContentContainer>
                            <IconProgressBar percentage={getProgressPercentage(openIssueCount, closedIssueCount)} />
                        </FilterContentContainer>
                        <FilterContentContainer>{getCorrectMilestone(checkedDatas.milestone)?.name}</FilterContentContainer>
                    </>
                )}
                {filterName === 'milestone' && !isNew && (
                    <>
                        <FilterContentContainer>
                            <IconProgressBar percentage={getProgressPercentage(openIssueCount, closedIssueCount)} />
                        </FilterContentContainer>
                        <FilterContentContainer>{filterData?.name}</FilterContentContainer>
                    </>
                )}

                {getActivePopupType(isActivePopup) === filterName && (
                    <PopupContainer ref={popupRef}>
                        <ul>
                            <StyledList className="title">{getPopupTitle[filterName]}</StyledList>
                            {filterName === 'assignee' && assigneesData && (
                                <OptionSidebarContents
                                    contents={assigneesData}
                                    filterName={filterName}
                                    filterData={filterData}
                                    checkedDatas={checkedDatas?.assignee}
                                    setCheckedDatas={setCheckedDatas}
                                    issueId={issueId}
                                    isNew={true}
                                />
                            )}
                            {filterName === 'label' && labelsData && (
                                <OptionSidebarContents
                                    contents={labelsData.labels}
                                    filterName={filterName}
                                    filterData={filterData}
                                    checkedDatas={checkedDatas?.label}
                                    setCheckedDatas={setCheckedDatas}
                                    issueId={issueId}
                                    isNew={true}
                                />
                            )}
                            {filterName === 'milestone' && openMilestonesData && closedMilestonesData && (
                                <OptionSidebarContents
                                    contents={[...openMilestonesData.milestoneDetailDtos, ...closedMilestonesData.milestoneDetailDtos]}
                                    filterName={filterName}
                                    filterData={filterData}
                                    checkedDatas={checkedDatas?.milestone}
                                    setCheckedDatas={setCheckedDatas}
                                    issueId={issueId}
                                    isNew={true}
                                />
                            )}
                        </ul>
                    </PopupContainer>
                )}
            </Filter>
            <StyledLine />
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
`;
const PopupContainer = styled.div`
    position: absolute;
    top: 20px;
    right: 0;
    min-width: 240px;
    min-height: 65px;
    border: 2px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    /* background-color: antiquewhite; */
    background-color: ${(props) => props.theme.bgColorBody};
    opacity: 90%;
    z-index: 5;
    & .title {
        border-radius: 20px 20px 0 0;
        background-color: ${(props) => props.theme.listHeaderColor};
    }
`;

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

const FilterContentContainer = styled(FlexRow)`
    flex-basis: 30%;
    margin: 10px 0 10px 0;
    justify-content: left;

    & .userName {
        margin-left: 10px;
    }
`;

const FilterTitle = styled(FlexRow)`
    cursor: pointer;
    font-size: 15px;
    margin-bottom: 15px;
    svg {
        width: 20px;
    }
`;
const Filter = styled.div`
    position: relative;
    min-height: 100px;
    margin: 35px 30px;
`;
