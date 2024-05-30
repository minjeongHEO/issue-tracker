import React from 'react';
import { DropTitle } from '../../styles/theme.js';

const clearTypeItemTitle = {
    type1: '가 없는 이슈',
    type2: '이 없는 이슈',
};

export default function DropDownTitle({ name, filterTitle }) {
    return (
        <DropTitle>
            {name}
            {filterTitle === 'author' || filterTitle === 'assignee' ? clearTypeItemTitle['type1'] : clearTypeItemTitle['type2']}
        </DropTitle>
    );
}
