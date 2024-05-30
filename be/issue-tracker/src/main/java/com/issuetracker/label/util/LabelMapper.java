package com.issuetracker.label.util;

import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.entity.Label;

public class LabelMapper {
    public static Label toLabel(LabelDto labelDto) {
        return new Label(null, labelDto.getName(), labelDto.getDescription(), labelDto.getTextColor(),
                labelDto.getBgColor());
    }

    public static Label toModifiedLabel(Long id, LabelDto labelDto) {
        return new Label(id, labelDto.getName(), labelDto.getDescription(), labelDto.getTextColor(),
                labelDto.getBgColor());
    }
}
