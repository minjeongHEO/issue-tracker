import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { IconPlus } from '../../assets/icons/IconPlus';
import { CustomProfile } from '../../assets/CustomProfile';
import { IconProgressBar } from '../../assets/icons/IconProgressBar';
import { getProgressPercentage } from '../../utils/issueUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { FlexRow } from '../../styles/theme';
import { useLabelsFilter, useMembersFilter, useMilestonesFilter } from '../../hooks/useFiltersData';
import { useQueryClient } from '@tanstack/react-query';

const initActivePopup = {
    assignee: false,
    label: false,
    milestone: false,
};

export default function OptionSidebar({ filterName, filterData, children }) {
    const openIssueCount = filterData?.openIssueCount ?? 0;
    const closedIssueCount = filterData?.closedIssueCount ?? 0;
    const popupRef = useRef(null);

    const [isActivePopup, setIsActivePopup] = useState(initActivePopup);
    const [visiblePopupType, setVisiblePopupType] = useState('');

    const [isAssigneeFetchPossible, setIsAssigneeFetchPossible] = useState(false);
    const [isLabelFetchPossible, setIsLabelFetchPossible] = useState(false);
    const [isMilestoneFetchPossible, setIsMilestoneFetchPossible] = useState(false);
    const { data: assigneesData, isLoading: assiDataIsLoading } = useMembersFilter({
        enabled: isAssigneeFetchPossible,
    });
    const { data: labelsData, isLoading: labelsDataIsLoading } = useLabelsFilter({
        enabled: isLabelFetchPossible,
    });
    const { data: milestonesData, isLoading: milestonesDataIsLoading } = useMilestonesFilter({
        enabled: isMilestoneFetchPossible,
    });

    const setActivePopup = (type, toggleState) => {
        setIsActivePopup({ ...initActivePopup, [type]: toggleState });
    };

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
        setIsActivePopup(true);
        toggleEnableByType[type]();
    };

    useEffect(() => {
        const activeType = Object.entries(isActivePopup);
        if (activeType.every(([_, value]) => value === false)) setVisiblePopupType('');
        else setVisiblePopupType(activeType.filter(([_, value]) => value === true)[0][0]);
    }, [isActivePopup]);

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
                    filterData.map(({ id, imgUrl }) => (
                        <FilterContentContainer key={id}>
                            <CustomProfile src={imgUrl} alt={'assineeProfile'} />
                            <span className="userName">{id}</span>
                        </FilterContentContainer>
                    ))}

                {filterName === 'label' && (
                    <LabelContentContainer>
                        {filterData.map(({ id, name, description, textColor, bgColor }) => (
                            <StyledLabel key={id} backgroundColor={bgColor} color={textColor}>
                                {name}
                            </StyledLabel>
                        ))}
                    </LabelContentContainer>
                )}

                {filterName === 'milestone' && (
                    <>
                        <FilterContentContainer>
                            <IconProgressBar percentage={getProgressPercentage(openIssueCount, closedIssueCount)} />
                        </FilterContentContainer>
                        <FilterContentContainer>{filterData.name}</FilterContentContainer>
                    </>
                )}

                {visiblePopupType === filterName && <PopupContainer>팝업내용</PopupContainer>}
            </Filter>
            <StyledLine />
        </>
    );
}

const PopupContainer = styled.div`
    position: absolute;
    top: 10px;
    right: 0;
    min-width: 200px;
    min-height: 200px;
    border: 2px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    background-color: antiquewhite;
    z-index: 5;
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
