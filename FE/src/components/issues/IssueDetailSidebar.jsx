import React from 'react';
import styled from 'styled-components';
import { IconPlus } from '../../assets/icons/IconPlus';
import { FlexRow } from '../../styles/theme';
import { CustomProfile } from '../../assets/CustomProfile';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { IconProgressBar } from '../../assets/icons/IconProgressBar';
import { IconTrash } from '../../assets/icons/IconTrash';

export default function IssueDetailSidebar() {
    const calulatePercentage = (openCount, closedCount) => (closedCount / (openCount + closedCount)).toFixed(2);

    return (
        <StyledDiv>
            <SidebarContainer>
                <Filter>
                    <FilterTitle>
                        <div>담당자</div>
                        <IconPlus />
                    </FilterTitle>
                    <FilterContentContainer>
                        <CustomProfile src={'https://avatars.githubusercontent.com/u/96780693?v=4'} alt={'assineeProfile'} />
                        <span className="userName">woody</span>
                    </FilterContentContainer>
                    <FilterContentContainer>
                        <CustomProfile src={'https://avatars.githubusercontent.com/u/96780693?v=4'} alt={'assineeProfile'} />
                        <span className="userName">sangchu</span>
                    </FilterContentContainer>
                </Filter>
                <StyledLine />
                <Filter>
                    <FilterTitle>
                        <div>레이블</div>
                        <IconPlus />
                    </FilterTitle>

                    <LabelContentContainer>
                        <StyledLabel backgroundColor={'black'} color={'white'}>
                            레이블이름
                        </StyledLabel>
                    </LabelContentContainer>
                </Filter>
                <StyledLine />
                <Filter>
                    <FilterTitle>
                        <div>마일스톤</div>
                        <IconPlus />
                    </FilterTitle>
                    <FilterContentContainer>
                        <IconProgressBar percentage={calulatePercentage(2, 2)} />
                    </FilterContentContainer>
                    <FilterContentContainer>그룹프로젝트:이슈트래커</FilterContentContainer>
                </Filter>
            </SidebarContainer>
            <DeleteContentContainer>
                <IconTrash />
                이슈삭제
            </DeleteContentContainer>
        </StyledDiv>
    );
}

const StyledLabel = styled(CustomLabelBadge)`
    height: 30px;
    margin-right: 10px;
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

const DeleteContentContainer = styled(FlexRow)`
    width:100%
    margin: 20px 0 10px 0;
    justify-content: right;

`;
const FilterContentContainer = styled(FlexRow)`
    flex-basis: 30%;
    margin: 20px 0 10px 0;
    justify-content: left;

    & .userName {
        margin-left: 10px;
    }
`;

const FilterTitle = styled(FlexRow)`
    font-size: 15px;
    svg {
        width: 20px;
    }
`;
const Filter = styled.div`
    /* width: 100%; */
    min-height: 100px;
    margin: 35px 30px;
    /* background-color: beige; */
`;

const SidebarContainer = styled.div`
    flex-basis: 30%;
    min-width: 200px;
    min-height: 500px;
    border: 2px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    /* background-color: red; */
`;

const StyledDiv = styled.div`
    flex-basis: 30%;
`;
