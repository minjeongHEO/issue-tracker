package com.issuetracker.label.util;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BackgroundColorValidator {
    private static final String HEX_COLOR_REGEX = "^#[0-9a-fA-F]{3}([0-9a-fA-F]{3})?$";

    public static boolean isHex(String bgColor) {
        if (!validateRegex(bgColor)) {
            log.error(bgColor + "색은 라벨 정규표현식에 맞지 않아 유효하지 않습니다.");
            return false;
        }

        try {
            Color color = Color.decode(bgColor);
            // RGB 색상인 경우, 알파 값은 항상 255
            return color.getAlpha() == 255;
        } catch (NumberFormatException e) {
            // 변환 중 예외 발생 시 유효하지 않음
            log.error(bgColor + "은 색상으로 해석할 수 없는 문자열입니다.");
            return false;
        }
    }

    private static boolean validateRegex(String bgColor) {
        Pattern pattern = Pattern.compile(HEX_COLOR_REGEX);
        Matcher matcher = pattern.matcher(bgColor);
        return matcher.matches();
    }
}
