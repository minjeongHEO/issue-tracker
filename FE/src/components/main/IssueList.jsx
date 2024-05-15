import { Checkbox } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

export default function IssueList({ isEntireChecked, toggleEntireCheckBox, listData }) {
    const [isChecked, setIsChecked] = useState(false);
    const { title, labels, id, createDate, memberId, milestoneName } = listData;
    const [pastTime, setPastTime] = useState('');

    const toggleCheckBox = () => {
        if (isEntireChecked) toggleEntireCheckBox();
        setIsChecked((checked) => !checked);
    };

    const calculatePastTime = () => {
        const registerDate = new Date(createDate);
        const diffDate = Math.abs(new Date() - registerDate);
        const diffMiniutes = Math.floor(diffDate / (1000 * 60));
        const diffHours = Math.floor(diffDate / (1000 * 60 * 60));
        const diffDays = Math.floor(diffDate / (1000 * 60 * 60 * 24));

        if (diffDays >= 1) return `${diffDays}일 전`;
        if (diffHours >= 1) return `${diffHours}시간 전`;
        return diffMiniutes >= 1 ? `${diffMiniutes}분 전` : `방금 전`;
    };

    useEffect(() => {
        setPastTime(calculatePastTime());

        const intervalPerTime = () => {
            setInterval(() => {
                setPastTime(calculatePastTime());
            }, 1000 * 60);
        };

        return () => clearInterval(intervalPerTime);
    }, [createDate]);

    return (
        <ListContainer>
            <ListTitle>
                <Checkbox onClick={toggleCheckBox} checked={isChecked} />
                <ListBody>
                    <div>
                        <span>! {title}</span>
                        {labels &&
                            labels.map(({ name, bgColor }) => (
                                <StyledLabel key={name} style={{ backgroundColor: bgColor }}>
                                    {name}
                                </StyledLabel>
                            ))}
                    </div>
                    <div>
                        <span>#{id}</span>
                        <span>
                            이 이슈가 {pastTime}, {memberId || ''}님에 의해 작성되었습니다.
                        </span>
                        <span>마일스톤</span>
                    </div>
                </ListBody>
            </ListTitle>

            <StyledProfile>
                <img src="https://avatars.githubusercontent.com/u/96780693?s=40&v=4" className="profile" alt="profile"></img>
            </StyledProfile>
        </ListContainer>
    );
}

const ListBody = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    :first-child {
        margin-bottom: 20px;
    }

    & span {
        margin-left: 10px;
    }
`;

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
`;

const StyledProfile = styled.span`
    margin-right: 30px;
    & img {
        border-radius: 50%;
        width: 25px;
    }
`;

const StyledLabel = styled.span`
    margin-left: 10px;
    min-width: 20px;
    height: 30px;
    padding: 2px 10px;
    border-radius: 25px;
    border: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    font-size: 15px;
`;