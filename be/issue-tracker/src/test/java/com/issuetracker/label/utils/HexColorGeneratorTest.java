package com.issuetracker.label.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HexColorGeneratorTest {
    @DisplayName("라벨의 배경 색을 랜덤으로 생성할 수 있다.")
    @Test
    void generateRandomHexColor() {
        String randomHexColor = HexColorGenerator.generateRandomHexColor();
        assertThat(BackgroundColorValidator.isHex(randomHexColor)).isTrue();
    }
}
