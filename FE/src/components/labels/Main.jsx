import React, { useState } from 'react';
import Header from '../header/Header';
import { FlexCol, FlexRow, IndexContainer, MainContainer, StyledInput } from '../../styles/theme';
import styled from 'styled-components';
import { CustomButton } from '../../assets/CustomButton';
import { IconMilestone } from '../../assets/icons/IconMilestone';
import { TagsOutlined, PlusOutlined } from '@ant-design/icons';
import LabelList from './LabelList';
import NewLabel from './NewLabel';
import { useNavigate } from 'react-router-dom';
import { useLabelDetailData } from '../../hooks/useLabelData';
import ClipLoader from 'react-spinners/ClipLoader';

export default function LabelMain() {
    const naivgate = useNavigate();
    const clickMileStone = () => naivgate('/milestones');
    const clickLabel = () => naivgate('/labels');
    const { data: labelData, isLoading: labelDataIsLoading } = useLabelDetailData();
    const [isPlusLabelState, setIsPlusLabelState] = useState(false);

    const togglePlusLabelState = () => setIsPlusLabelState((prev) => !prev);

    return (
        <IndexContainer>
            <Header />
            <MainContainer>
                <NavContainer>
                    <NavBtnContainer>
                        <StyledLabelBtn type={'container'} size={'large'} isDisabled={false} onClick={clickLabel}>
                            <TagsOutlined />
                            레이블({labelData?.count || 0})
                        </StyledLabelBtn>
                        <StyledMilestoneBtn type={'outline'} size={'large'} isDisabled={false} onClick={clickMileStone}>
                            <IconMilestone />
                            마일스톤()
                        </StyledMilestoneBtn>
                    </NavBtnContainer>
                    <CustomButton type={'container'} size={'large'} isDisabled={isPlusLabelState} onClick={togglePlusLabelState}>
                        <PlusOutlined />
                        레이블 추가
                    </CustomButton>
                </NavContainer>

                {isPlusLabelState && <NewLabel togglePlusLabelState={togglePlusLabelState} />}

                <ContentsContainer>
                    <StyledBoxHeader>{labelData?.count || 0}개의 레이블</StyledBoxHeader>
                    <StyledBoxBody>
                        {labelDataIsLoading && (
                            <StyledLoader>
                                <ClipLoader color="#007AFF" />
                            </StyledLoader>
                        )}
                        {labelData &&
                            labelData.labels.map(({ id, name, description, textColor, bgColor }) => (
                                <LabelList key={id} name={name} description={description} textColor={textColor} bgColor={bgColor} />
                            ))}
                        {labelData?.labels.length === 0 && <LabelList isNoList={true} />}
                    </StyledBoxBody>
                </ContentsContainer>
            </MainContainer>
        </IndexContainer>
    );
}

const StyledMilestoneBtn = styled(CustomButton)`
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    width: 150px;
`;
const StyledLabelBtn = styled(CustomButton)`
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
    width: 150px;
`;
const NavBtnContainer = styled(FlexRow)`
    justify-content: end;
    & .createBtn {
        margin-left: 10px;
    }
    button {
        font-size: 16px;
    }
`;

const NavContainer = styled(FlexRow)`
    justify-content: space-between;
    width: 100%;
    height: 70px;
    margin-bottom: 10px;
    button {
        font-size: 16px;
    }
`;
const StyledBoxBody = styled.div`
    min-height: 80px;
    /* background-color: skyblue; */
`;

const StyledBoxHeader = styled.div`
    background-color: ${(props) => props.theme.listHeaderColor};
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 60px;
    color: ${(props) => props.theme.fontColor};
    border-top-left-radius: 6px;
    border-top-right-radius: 6px;
    padding: 0 30px;
    font-weight: bold;
`;

const ContentsContainer = styled.div`
    min-height: 160px;
    border-radius: 10px;
    border: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.bgColorBody};
    color: ${(props) => props.theme.fontColor};
    /* background-color: azure; */
`;

const StyledLoader = styled.div`
    height: 100px;
    align-content: center;
`;
