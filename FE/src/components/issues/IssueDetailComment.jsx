import { Button } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { FlexCol, FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { calculatePastTime } from '../../utils/dateUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconSmile } from '../../assets/icons/IconSmile';

export default function IssueDetailComment({ detailData }) {
    //TODO: React Query + Suspense
    if (!detailData) return <div>Loading...</div>;

    const { id, title, content, createDate, writer, milestone, file, labels, assignees, comments, isclosed } = detailData;
    const [pastTime, setPastTime] = useState('');

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
        <StyledCommentContainer>
            <CommentNav>
                <CommentData>
                    <StyledProfile
                        src={'https://github.com/codesquad-masters2024-team02/issue-tracker/assets/96780693/d1c7123b-89c9-485b-b9dd-8cc21a1005e0'}
                        alt={'userProfile'}
                        size={'medium'}
                    />
                    <span className="userName">woody</span>
                    <span className="">{pastTime}</span>
                </CommentData>
                <NavBtnContainer>
                    <StyledLabel>작성자</StyledLabel>
                    <div>
                        <IconEdit />
                        편집
                    </div>
                    <div>
                        <IconSmile />
                        반응
                    </div>
                </NavBtnContainer>
            </CommentNav>
        </StyledCommentContainer>
    );
}

const StyledLabel = styled(CustomLabelBadge)`
    font-size: 12px;
`;

const StyledProfile = styled(CustomProfile)`
    margin-left: 10px;
`;
const NavBtnContainer = styled(FlexRow)`
    justify-content: space-around;
    /* background-color: red; */
    width: 190px;
`;

const CommentData = styled(FlexRow)`
    * {
        margin-left: 10px;
    }
`;

const StyledFlexRow = styled(FlexRow)`
    & .commentData {
        /* flex-basis: 70%; */
    }
`;

const CommentNav = styled(FlexRow)`
    width: 100%;
    height: 50px;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
    border-bottom: 1px solid ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.listHeaderColor};
    color: ${(props) => props.theme.fontColor};
`;
const StyledCommentContainer = styled.div`
    /* background-color: antiquewhite; */
    display: flex;
    flex-direction: column;
    width: 90%;
    min-height: 100px;
    margin-top: 15px;
    background-color: ${(props) => props.theme.bgColorBody};
    border-radius: 10px;
    border: 1px solid ${(props) => props.theme.borderColor};
    color: ${(props) => props.theme.fontColor};
`;
