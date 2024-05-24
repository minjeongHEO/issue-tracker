import { Checkbox } from 'antd';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { IconMilestone } from '../../assets/icons/IconMilestone';
import { calculatePastTime } from '../../utils/dateUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { CustomProfile } from '../../assets/CustomProfile';
import { IconAlertCircle } from '../../assets/icons/IconAlertCircle';

export default function IssueList({ isSingleChecked, setCheckedItems, listData, isNoList = false }) {
    const { id, title, createDate, milestoneName, author, assignees, labels } = listData;

    const [pastTime, setPastTime] = useState('');
    const navigate = useNavigate();

    const toggleCheckBox = () => {
        if (isSingleChecked) setCheckedItems((prev) => prev.filter((item) => item !== id));
        else setCheckedItems((prev) => [...prev, id]);
    };

    useEffect(() => {
        setPastTime(calculatePastTime(createDate));

        const intervalPerTime = () => {
            setInterval(() => {
                setPastTime(calculatePastTime(createDate));
            }, 1000 * 60);
        };

        return () => clearInterval(intervalPerTime);
    }, [createDate]);

    return (
        <>
            {isNoList ? (
                <ListContainer>
                    <div className="noList">등록된 이슈가 없습니다.</div>
                </ListContainer>
            ) : (
                <ListContainer id={id}>
                    <ListTitle>
                        <Checkbox onClick={toggleCheckBox} checked={isSingleChecked} />
                        <ListBody>
                            <div className="titleContainer">
                                <StyledAlertIcon />
                                <span className="title" onClick={() => navigate(`/issues/${id}`)}>
                                    {title}
                                </span>
                                {labels &&
                                    labels.map(({ name, bgColor, textColor }) => (
                                        <StyledLabel key={name} backgroundColor={bgColor} color={textColor}>
                                            {name}
                                        </StyledLabel>
                                    ))}
                            </div>
                            <div className="subsContainer">
                                <span>#{id}</span>
                                <span>
                                    이 이슈가 {pastTime}, <BoldSpan>{author.id || ''}</BoldSpan>님에 의해 작성되었습니다.
                                </span>

                                <StyledMilestone>
                                    <StyledMilestoneIcon>
                                        <IconMilestone />
                                    </StyledMilestoneIcon>
                                    {milestoneName || ''}
                                </StyledMilestone>
                            </div>
                        </ListBody>
                    </ListTitle>

                    <StyledProfile>
                        {assignees &&
                            assignees.map(({ id, imgUrl }) => <CustomProfile key={id} src={imgUrl} size={'medium'} alt="assigneeProfile" />)}
                    </StyledProfile>
                </ListContainer>
            )}
        </>
    );
}
const BoldSpan = styled.span`
    font-weight: bold;
`;
const StyledAlertIcon = styled(IconAlertCircle)`
    color: var(--primary-color);
`;
const StyledMilestone = styled.span`
    display: flex;
`;
const StyledMilestoneIcon = styled.span`
    align-content: center;
    margin-right: 10px;
`;

const ListBody = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-left: 20px;

    & .subsContainer {
        display: flex;
        color: ${(props) => props.theme.fontColor};
    }
    & .titleContainer {
        display: flex;
        align-items: center;
        margin-bottom: 20px;
    }

    & .title {
        font-weight: bold;
        cursor: pointer;
    }
    & span {
        margin-left: 10px;
    }
`;

const StyledIconMilestone = styled(IconMilestone)``;
const ListTitle = styled.div`
    display: flex;
    flex-direction: row;
    margin-left: 30px;
    align-items: baseline;
`;
const ListContainer = styled.div`
    height: 80px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;

    border-top: 1px solid;
    border-color: ${(props) => props.theme.borderColor};

    & .noList {
        width: 100%;
        height: 100%;
        align-content: center;
        margin-top: 20px;
    }
`;

const StyledProfile = styled.span`
    margin-right: 30px;

    & img {
        border-radius: 50%;
        width: 25px;
        height: 25px;
        object-fit: cover;
    }
`;

const StyledLabel = styled(CustomLabelBadge)`
    margin-left: 10px;
`;
