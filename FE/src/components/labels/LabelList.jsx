import React, { useState } from 'react';
import styled from 'styled-components';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { EditOutlined } from '@ant-design/icons';
import { IconTrash } from '../../assets/icons/IconTrash';
import { FlexRow } from '../../styles/theme';
import LabelEditor from './LabelEditor';

export default function LabelList({ id, name, description, textColor, bgColor, isNoList = false }) {
    const [isLabelEditState, setLabelEditState] = useState(false);

    const toggleEditLabelState = () => setLabelEditState((prev) => !prev);

    return (
        <>
            {isNoList ? (
                <ListContainer>
                    <div className="noList">등록된 레이블이 없습니다.</div>
                </ListContainer>
            ) : isLabelEditState ? (
                <LabelEditor
                    isNew={false}
                    toggleEditLabelState={toggleEditLabelState}
                    id={id}
                    bgColor={bgColor}
                    textColor={textColor}
                    name={name}
                    description={description || ''}
                />
            ) : (
                <ListContainer id={id}>
                    <TitleContainer>
                        <BadgeContainer>
                            <CustomLabelBadge backgroundColor={bgColor} color={textColor}>
                                {name}
                            </CustomLabelBadge>
                        </BadgeContainer>
                        <StyledDescription>{description || ''}</StyledDescription>
                    </TitleContainer>

                    <StyledBtnContainer>
                        <StyledBtn onClick={toggleEditLabelState}>
                            <EditOutlined />
                            편집
                        </StyledBtn>
                        <StyledBtn>
                            <IconTrash />
                            삭제
                        </StyledBtn>
                    </StyledBtnContainer>
                </ListContainer>
            )}
        </>
    );
}

const StyledBtn = styled.div`
    display: flex;
    cursor: pointer;
    color: ${(props) => props.theme.fontColor};
    margin-left: 20px;
`;
const BadgeContainer = styled(FlexRow)`
    min-width: 200px;
    margin-right: 10px;
`;
const TitleContainer = styled(FlexRow)`
    flex-basis: 80%;
    justify-content: flex-start;
`;

const StyledBtnContainer = styled.div`
    display: flex;
`;

const StyledDescription = styled.div``;

const ListContainer = styled(FlexRow)`
    min-height: 80px;
    align-items: center;
    justify-content: space-between;
    border-top: 1px solid;
    border-color: ${(props) => props.theme.borderColor};
    padding: 0 30px;

    & .noList {
        width: 100%;
        height: 100%;
        align-content: center;
        margin-top: 20px;
    }
`;
