import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { PlusOutlined, RedoOutlined, CloseOutlined, DownOutlined } from '@ant-design/icons';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { FlexCol, FlexRow, StyledInput } from '../../styles/theme';
import { CustomButton } from '../../assets/CustomButton';
import { Radio, message } from 'antd';
import validateColor from 'validate-color';
import { useCreateNewLabel } from '../../hooks/useLabelData';

export default function LabelEditor({ isNew = true, togglePlusLabelState, toggleEditLabelState, id, bgColor, textColor, name, description }) {
    const clearInputForm = () => {
        setNewBgColor('#');
        setNewBgColorValue('#');
        setIsNewBgColorValidate(false);
        setIsNewLabelDisabled(false);
        setNewLabelName('');
        setNewFontColor('');
        discriptionRef.current.value = '';
    };
    const onSuccess = () => {
        clearInputForm();
        message.success('레이블이 생성되었습니다.');
    };
    const { mutate: createLabel } = useCreateNewLabel(onSuccess);

    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const [isNewLabelDisabled, setIsNewLabelDisabled] = useState(true);
    const [isEditLabelDisabled, setIsEditLabelDisabled] = useState(true);
    const [newLabelName, setNewLabelName] = useState('');

    const [newBgColor, setNewBgColor] = useState('#');
    const [newBgColorValue, setNewBgColorValue] = useState('#');
    const [isNewBgColorValidate, setIsNewBgColorValidate] = useState(false);

    const [newFontColor, setNewFontColor] = useState(''); // 'light' | 'dark'
    const [editDiscriptionValue, setEditDiscriptionValue] = useState('');
    const discriptionRef = useRef(null);
    const popupRef = useRef(null);

    const handleNameChange = ({ target }) => {
        const { value } = target;
        setNewLabelName(value);
    };

    const isValidateColor = (value) => {
        return value && validateColor(value);
    };

    const handleBgColorChange = ({ target }) => {
        const { value } = target;
        setNewBgColorValue(value);

        if (isValidateColor(value)) {
            setNewBgColor(value);
            setIsNewBgColorValidate(true);
        } else {
            setNewBgColor('');
            setIsNewBgColorValidate(false);
        }
    };
    const handleFontColorChange = (e) => {
        const fontColor = e.currentTarget.dataset.id ?? '';
        setNewFontColor(fontColor);
    };

    const togglePopup = () => setIsPopupVisible((prevState) => !prevState);

    const submitEditLabel = () => {};

    const submitLabel = () => {
        createLabel({
            name: newLabelName,
            description: discriptionRef.current.value === undefined || discriptionRef.current.value === '' ? null : discriptionRef.current.value,
            textColor: newFontColor === 'light' ? '#fff' : '#000',
            bgColor: newBgColor,
        });
    };

    const setRandomColors = () => {
        const letters = '0123456789ABCDEF';
        const randomColor = Array.from({ length: 6 }).reduce((acc, _, idx) => {
            if (idx === 0) acc += '#';
            return (acc += letters[Math.floor(Math.random() * 16)]);
        }, '');

        if (isValidateColor(randomColor)) {
            setNewBgColor(randomColor);
            setNewBgColorValue(randomColor);
            setIsNewBgColorValidate(true);
        }
    };

    const settingEditValues = (textColor, bgColor, name, description) => {
        textColor === '#fff' ? setNewFontColor('light') : setNewFontColor('dark');
        setNewBgColor(bgColor);
        setNewBgColorValue(bgColor);
        setNewLabelName(name);
        setEditDiscriptionValue(description);
    };

    const handleEditDescriptionChange = () => {
        if (isNew) return;
        setEditDiscriptionValue(discriptionRef.current.value);
    };

    const settingEditButtonDisable = (newLabelName, newBgColorValue, newFontColor, editDiscriptionValue) => {
        const convertTextColor = textColor === '#fff' ? 'light' : 'dark';
        if (
            newLabelName !== name ||
            (newBgColorValue !== bgColor && isValidateColor(newBgColorValue)) ||
            newFontColor !== convertTextColor ||
            editDiscriptionValue !== description
        )
            setIsEditLabelDisabled(false);
        else setIsEditLabelDisabled(true);
    };

    const settingNewButtonDisable = (newLabelName, newBgColor, newFontColor) => {
        if (newLabelName && newBgColor && newFontColor && isNewBgColorValidate) setIsNewLabelDisabled(false);
        else setIsNewLabelDisabled(true);
    };

    useEffect(() => {
        if (isNew) return;
        settingEditButtonDisable(newLabelName, newBgColorValue, newFontColor, editDiscriptionValue);
    }, [newLabelName, newBgColorValue, newFontColor, editDiscriptionValue]);

    useEffect(() => {
        if (isNew) return;
        settingEditValues(textColor, bgColor, name, description);
    }, [textColor, bgColor, name, description]);

    useEffect(() => {
        settingNewButtonDisable(newLabelName, newBgColor, newFontColor);
    }, [newLabelName, newBgColor, newFontColor]);

    return (
        <AddContainer $isNew={isNew}>
            <AddTitle>{isNew ? '새로운 레이블 추가' : '레이블 편집'}</AddTitle>
            <AddContents>
                <Preview>
                    <div>
                        <CustomLabelBadge backgroundColor={newBgColor} color={newFontColor === 'light' ? 'white' : 'black'}>
                            {newLabelName}
                        </CustomLabelBadge>
                    </div>
                </Preview>
                <InputContainer>
                    <StyledTitle>
                        <PlaceholdText className="placeholdText">이름</PlaceholdText>
                        <ModifyInput type="text" placeholder="최대 12자" value={newLabelName} onChange={handleNameChange} maxLength="12" />
                    </StyledTitle>
                    <StyledTitle>
                        <PlaceholdText className="placeholdText">설명(선택)</PlaceholdText>
                        <ModifyInput type="text" ref={discriptionRef} onChange={handleEditDescriptionChange} />
                    </StyledTitle>
                    <StyledColorOption>
                        <StyledTitle className="backgroudColorOption">
                            <PlaceholdText className="placeholdText">배경 색상</PlaceholdText>
                            <RandomIcon onClick={setRandomColors}>
                                <RedoOutlined />
                            </RandomIcon>
                            <ModifyInput type="text" value={newBgColorValue} onChange={handleBgColorChange} maxLength="15" />
                        </StyledTitle>
                        <div>
                            <StyledTextColorBtn onClick={togglePopup}>
                                <span>{newFontColor ? (newFontColor === 'light' ? '밝은 색' : '어두운 색') : '텍스트 색상'} </span>
                                <DownOutlined />{' '}
                            </StyledTextColorBtn>
                            {isPopupVisible && (
                                <PopupContainer ref={popupRef}>
                                    <ul>
                                        <StyledList className="title">텍스트 색상</StyledList>
                                        <StyledList>
                                            <FlexRow className="itemTitle">밝은 색</FlexRow>
                                            <FlexRow className="itemRadio">
                                                <Radio onClick={handleFontColorChange} data-id="light" checked={newFontColor === 'light'} />
                                            </FlexRow>
                                        </StyledList>
                                        <StyledList>
                                            <FlexRow className="itemTitle">어두운 색</FlexRow>
                                            <FlexRow className="itemRadio">
                                                <Radio onClick={handleFontColorChange} data-id="dark" checked={newFontColor === 'dark'} />
                                            </FlexRow>
                                        </StyledList>
                                    </ul>
                                </PopupContainer>
                            )}
                        </div>
                    </StyledColorOption>
                </InputContainer>
            </AddContents>

            <NavBtnContainer>
                <CustomButton type={'outline'} size={'large'} isDisabled={false} onClick={isNew ? togglePlusLabelState : toggleEditLabelState}>
                    <CloseOutlined />
                    취소
                </CustomButton>

                <CustomButton
                    className={'createBtn'}
                    type={'container'}
                    size={'large'}
                    isDisabled={isNew ? isNewLabelDisabled : isEditLabelDisabled}
                    onClick={isNew ? submitLabel : submitEditLabel}
                >
                    <PlusOutlined />
                    완료
                </CustomButton>
            </NavBtnContainer>
        </AddContainer>
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

const StyledTextColorBtn = styled.span`
    position: relative;
    display: inline-block;
    cursor: pointer;
`;
const PopupContainer = styled.div`
    position: absolute;
    min-width: 180px;
    min-height: 65px;
    border: 2px solid ${(props) => props.theme.borderColor};
    border-radius: 20px;
    background-color: ${(props) => props.theme.bgColorBody};
    opacity: 90%;
    z-index: 5;
    & .title {
        border-radius: 20px 20px 0 0;
        background-color: ${(props) => props.theme.listHeaderColor};
    }
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

const StyledColorOption = styled(FlexRow)`
    justify-content: flex-start;
    & .backgroudColorOption {
        margin-right: 20px;
    }
`;
const StyledTitle = styled(FlexRow)`
    position: relative;
    /* background-color: red; */
`;
const RandomIcon = styled.span`
    position: absolute;
    top: 15px;
    right: 15px;
    color: var(--secondary-color);
    font-size: 13px;
    cursor: pointer;
`;
const PlaceholdText = styled.span`
    position: absolute;
    top: 15px;
    left: 15px;
    color: var(--secondary-color);
    font-size: 13px;
`;
const ModifyInput = styled(StyledInput)`
    width: 100%;
    height: 100%;
    padding: 10px 20px 10px 90px;
    color: var(--secondary-color);
`;

const InputContainer = styled(FlexCol)`
    justify-content: space-between;
    width: 100%;
    align-self: normal;
    /* background-color: aquamarine; */

    & ::placeholder {
        font-size: 13px;
        font-style: italic;
        /* color: ${(props) => props.theme.fontColor}; */
    }
`;
const Preview = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    min-width: 200px;
    height: 150px;
    flex-basis: 30%;
    border-radius: 10px;
    border: 1px solid;
    border-color: ${(props) => (props.$isfocused ? 'var(--primary-color)' : props.theme.borderColor)};
    margin-right: 20px;
    background-color: white;
`;

const AddContents = styled(FlexRow)`
    margin-bottom: 20px;
    /* justify-content: space-between; */
`;

const AddTitle = styled.div`
    width: 100%;
    display: flex;
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 15px;
`;

const AddContainer = styled.div`
    width: 100%;
    border-radius: ${(props) => (props.$isNew ? '10px' : 0)};
    border: 2px solid;
    border-color: ${(props) => (props.$isNew ? 'var(--primary-color)' : props.theme.borderColor)};
    color: ${(props) => props.theme.fontColor};
    padding: 30px;
    background-color: ${(props) => props.theme.bgColorBody};
`;
