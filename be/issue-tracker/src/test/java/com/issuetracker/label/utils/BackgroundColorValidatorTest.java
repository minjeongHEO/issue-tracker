package com.issuetracker.label.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BackgroundColorValidatorTest {
    @DisplayName("올바른 배경 색상이다.")
    @ParameterizedTest
    @ValueSource(strings = {"#FFFFFF", "#000000", "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF", "#FF00FF",
            "#808080", "#A52A2A"})
    void isValidBgColor(String bgColor) {
        assertThat(BackgroundColorValidator.isHex(bgColor)).isTrue();
    }

    @DisplayName("올바르지 않은 배경 색상이다.")
    @ParameterizedTest
    @ValueSource(strings = {"#1234567", "#12", "#GGGGGG", "red", "#dasdad", "#FFFFFFF", "#000000000", "#FFA5008",
            "#00FF00GG", "#ABCDEF1"})
    void isInvalidBgColor(String bgColor) {
        assertThat(BackgroundColorValidator.isHex(bgColor)).isFalse();
    }
}
