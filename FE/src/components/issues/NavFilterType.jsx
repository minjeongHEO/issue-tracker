import React from 'react';
import { StyledHeaderContents } from '../../styles/theme';
import DropDownFilter from './DropDownFilter';
import { useFilterContext } from '../../context/FilterContext';

export default function NavFilterType({ dispatchTypeByFilterContents, imageTypeItems, labelTypeItems, milestoneTypeItems, ischecked }) {
    const { dispatch } = useFilterContext();

    const dispatchIssue = ({ target }) => {
        const attrValue = target.getAttribute('attr-key');
        if (!attrValue) return;
        dispatch({ type: dispatchTypeByFilterContents[attrValue], payload: attrValue });
    };

    return (
        <StyledHeaderContents>
            <div className="issue">
                <span className={`issueOption click ${ischecked ? '' : `checked`}`} attr-key="is:open" onClick={dispatchIssue}>
                    열린 이슈()
                </span>
                <span className={`issueOption click ${ischecked ? `checked` : ''}`} attr-key="is:closed" onClick={dispatchIssue}>
                    닫힌 이슈()
                </span>
            </div>
            <div className="filter">
                <span className="filterOption">
                    <DropDownFilter filterTitle={'assignee'} filterItems={imageTypeItems}>
                        담당자
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'label'} filterItems={labelTypeItems}>
                        레이블
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'milestone'} filterItems={milestoneTypeItems}>
                        마일스톤
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'author'} filterItems={imageTypeItems}>
                        작성자
                    </DropDownFilter>
                </span>
            </div>
        </StyledHeaderContents>
    );
}
