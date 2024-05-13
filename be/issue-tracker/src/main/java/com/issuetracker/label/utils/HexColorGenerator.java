package com.issuetracker.label.utils;

import java.util.Random;

public class HexColorGenerator {
    private static final Random random = new Random();

    public static String generateRandomHexColor() {
        // 0부터 255 사이의 무작위 정수 값을 RGB 색상 코드로 생성
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        // 생성된 RGB 값을 Hex 문자열로 변환
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
