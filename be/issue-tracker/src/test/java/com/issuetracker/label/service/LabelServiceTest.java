package com.issuetracker.label.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.exception.InvalidBgColorException;
import com.issuetracker.label.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LabelServiceTest {
    private LabelDto labelDto;

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.labelDto = new LabelDto();
    }

    @DisplayName("유효한 색상 코드를 가진 라벨 생성 요청이면 새 라벨을 생성할 수 있다.")
    @Test
    public void createLabel_WithValidBgColor() {
        setupLabelDto("검정", "#000000");
        createAndReturnMockLabel("검정", "#000000", 1L);
        Label createdLabel = labelService.createLabel(labelDto);
        verifyLabelProperties(createdLabel, 1L, "검정", "#000000");
    }

    @DisplayName("유효하지 않은 색상 코드를 가진 라벨 생성 요청이면 새 라벨을 생성할 수 없다.")
    @Test
    public void createLabel_WithInvalidBgColor() {
        setupLabelDto("올바르지 않은 색상", "#dasdad");
        assertThatThrownBy(() -> {
            labelService.createLabel(labelDto);
        }).isInstanceOf(InvalidBgColorException.class);
        verify(labelRepository, never()).save(any(Label.class));
    }

    @DisplayName("유효한 색상 코드를 가진 라벨 수정 요청이면 라벨을 수정할 수 있다.")
    @Test
    public void modifyLabel_WithValidBgColor() {
        setupLabelDto("검정", "#000000");
        Label mockLabel = createAndReturnMockLabel("검정", "#000000", 1L);
        Label modifiedLabel = labelService.modifyLabel(labelDto, mockLabel.getId());
        verifyLabelProperties(modifiedLabel, 1L, "검정", "#000000");
    }

    @DisplayName("유효하지 않은 색상 코드를 가진 라벨 수정 요청이면 라벨을 수정할 수 없다.")
    @Test
    public void modifyLabel_WithInvalidBgColor() {
        setupLabelDto("올바르지 않은 색상", "#dasdad");
        assertThatThrownBy(() -> {
            labelService.modifyLabel(labelDto, 1L);
        }).isInstanceOf(InvalidBgColorException.class);
        verify(labelRepository, never()).save(any(Label.class));
    }

    private void setupLabelDto(String name, String bgColor) {
        labelDto.setName(name);
        labelDto.setBgColor(bgColor);
    }

    private Label createAndReturnMockLabel(String name, String bgColor, Long id) {
        Label label = new Label(name, null, bgColor);
        label.setId(id);
        when(labelRepository.save(any(Label.class))).thenReturn(label);
        return label;
    }

    private void verifyLabelProperties(Label label, Long expectedId, String expectedName, String expectedBgColor) {
        assertThat(label).isNotNull();
        assertThat(label.getId()).isEqualTo(expectedId);
        assertThat(label.getName()).isEqualTo(expectedName);
        assertThat(label.getDescription()).isNull();
        assertThat(label.getBgColor()).isEqualTo(expectedBgColor);
    }
}
