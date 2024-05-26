import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { CustomProfile } from '../../assets/CustomProfile';
import { CustomLabelBadge } from '../../assets/CustomLabelBadge';
import { FlexRow } from '../../styles/theme';
import { Checkbox } from 'antd';

export default function OptionSidebarContents({ contents, filterName }) {
    return (
        <>
            {contents.map((content) => (
                <StyledList key={content.id}>
                    <FlexRow className="itemTitle">
                        {filterName === 'assignee' && (
                            <>
                                <CustomProfile src={content.imgUrl} />
                                <span className="titleName">{content.id}</span>
                            </>
                        )}
                        {filterName === 'label' && (
                            <StyledLabel backgroundColor={content.bgColor} color={content.textColor}>
                                {content.name}
                            </StyledLabel>
                        )}
                        {filterName === 'milestone' && <span className="titleName">{content.name}</span>}
                    </FlexRow>
                    <FlexRow className="itemRadio">
                        <Checkbox />
                    </FlexRow>
                </StyledList>
            ))}
        </>
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

const StyledLabel = styled(CustomLabelBadge)`
    /* height: 30px; */
    margin-right: 10px;
    font-size: 13px;
`;
