import { Checkbox } from 'antd';
import React from 'react';
import styled from 'styled-components';

export default function IssueList() {
    return (
        <ListContainer>
            <ListTitle>
                <Checkbox />
                <ListBody>
                    <div>
                        <span>! 이슈 제목</span>
                        <span>라벨</span>
                    </div>
                    <div>
                        <span>#이슈번호</span>
                        <span>작성자 및 타임스탬프 정보</span>
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
