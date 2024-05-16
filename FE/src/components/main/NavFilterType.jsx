import React from 'react';
import { StyledHeaderContents } from '../../styles/theme';
import DropDownFilter from './DropDownFilter';

export default function NavFilterType({ dispatchTypeByFilterContents, imageTypeItems, labelTypeItems, milestoneTypeItems, dispatch, ischecked }) {
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
                    <DropDownFilter filterTitle={'author'} filterItems={imageTypeItems} dispatch={dispatch}>
                        담당자
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'label'} filterItems={labelTypeItems} dispatch={dispatch}>
                        레이블
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'milestone'} filterItems={milestoneTypeItems} dispatch={dispatch}>
                        마일스톤
                    </DropDownFilter>
                </span>
                <span className="filterOption">
                    <DropDownFilter filterTitle={'assignee'} filterItems={imageTypeItems} dispatch={dispatch}>
                        작성자
                    </DropDownFilter>
                </span>
            </div>
        </StyledHeaderContents>
    );
}
