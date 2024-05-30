package com.issuetracker.label.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HexColorGeneratorTest {
    @DisplayName("라벨의 배경 색을 랜덤으로 생성할 수 있다.")
    @Test
    void generateRandomHexColor() {
        HexColorGenerator hexColorGenerator = new HexColorGenerator();
        String randomHexColor = hexColorGenerator.generateRandomHexColor();
        assertThat(BackgroundColorValidator.isHex(randomHexColor)).isTrue();
    }
}
