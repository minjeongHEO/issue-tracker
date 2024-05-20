import { Button } from 'antd';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { FlexCol, FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { calculatePastTime } from '../../utils/dateUtils';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconEdit } from '../../assets/icons/IconEdit';
import { IconSmile } from '../../assets/icons/IconSmile';

export default function IssueDetailComment({ detailCommentData }) {
    //TODO: React Query + Suspense
    if (!detailCommentData) return <div>Loading...</div>;

    const { id, content, createDate, writer, file, isWriter } = detailCommentData;
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
                    <StyledProfile src={writer.imgUrl} alt={'userProfile'} size={'medium'} />
                    <span className="userName">{writer.id}</span>
                    <span className="">{pastTime}</span>
                </CommentData>
                <NavBtnContainer>
                    <StyledLabel visibility={isWriter ? 'visible' : 'hidden'}>작성자</StyledLabel>
                    <NavBtn>
                        <IconEdit />
                        편집
                    </NavBtn>
                    <NavBtn>
                        <IconSmile />
                        반응
                    </NavBtn>
                </NavBtnContainer>
            </CommentNav>
            <CommentMain>
                <Content>{content}</Content>
            </CommentMain>
        </StyledCommentContainer>
    );
}

const Content = styled.div`
    /* background-color: aliceblue; */
    width: 95%;
    margin: 15px 0px;
    word-wrap: break-word;
    text-align: justify;
`;
const CommentMain = styled(FlexRow)`
    justify-content: center;
    width: 100%;
    /* height: 50px; */
    border-bottom-right-radius: 10px;
    border-bottom-left-radius: 10px;
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
`;

const StyledLabel = styled(CustomLabelBadge)`
    font-size: 12px;
`;

const StyledProfile = styled(CustomProfile)`
    margin-left: 10px;
`;
const NavBtn = styled(FlexRow)`
    font-size: 12px;
    * {
        margin-right: 2px;
    }
    cursor: pointer;
`;
const NavBtnContainer = styled(FlexRow)`
    justify-content: space-around;
    /* background-color: red; */
    width: 190px;
    margin-right: 10px;
`;

const CommentData = styled(FlexRow)`
    * {
        margin-left: 10px;
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
