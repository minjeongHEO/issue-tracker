package com.issuetracker.label.utils;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackgroundColorValidator {
    private static final String HEX_COLOR_REGEX = "^#[0-9a-fA-F]{3}([0-9a-fA-F]{3})?$";

    public static boolean isHex(String bgColor) {
        if (!validateRegex(bgColor)) {
            return false;
        }

        try {
            Color color = Color.decode(bgColor);
            // RGB 색상인 경우, 알파 값은 항상 255
            return color.getAlpha() == 255;
        } catch (NumberFormatException e) {
            // 변환 중 예외 발생 시 유효하지 않음
            return false;
        }
    }

    private static boolean validateRegex(String bgColor) {
        Pattern pattern = Pattern.compile(HEX_COLOR_REGEX);
        Matcher matcher = pattern.matcher(bgColor);
        return matcher.matches();
    }
}
