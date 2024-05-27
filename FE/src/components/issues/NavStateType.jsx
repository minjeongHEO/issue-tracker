import React from 'react';
import { StyledHeaderContents } from '../../styles/theme';
import DropDownFilter from './DropDownFilter';

export default function NavStateType({ checkedItemsCount, stateModifyFilters, dispatch }) {
    return (
        <StyledHeaderContents>
            <div className="issue state">
                <span className="checked">{checkedItemsCount}</span>개 이슈 수정
            </div>
            <div className="filter state">
                <span className="filterOption">
                    <DropDownFilter filterTitle={'state'} filterItems={stateModifyFilters} dispatch={dispatch}>
                        상태 수정
                    </DropDownFilter>
                </span>
            </div>
        </StyledHeaderContents>
    );
}
