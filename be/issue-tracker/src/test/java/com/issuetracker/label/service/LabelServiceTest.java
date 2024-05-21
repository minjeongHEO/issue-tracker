package com.issuetracker.label.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.entity.Label;
import com.issuetracker.label.exception.InvalidBgColorException;
import com.issuetracker.label.exception.LabelNotFoundException;
import com.issuetracker.label.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LabelServiceTest {
    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유효한 색상 코드를 가진 라벨 생성 요청이면 새 라벨을 생성할 수 있다.")
    @Test
    public void createLabel_WithValidBgColor() {
        LabelDto labelDto = new LabelDto("검정", null, "#000000", "#000000");
        createAndReturnMockLabel(labelDto, 1L);
        Label createdLabel = labelService.createLabel(labelDto);
        verifyLabelProperties(createdLabel, 1L, "검정", "#000000");
    }

    @DisplayName("유효하지 않은 색상 코드를 가진 라벨 생성 요청이면 새 라벨을 생성할 수 없다.")
    @Test
    public void createLabel_WithInvalidBgColor() {
        LabelDto labelDto = new LabelDto("올바르지 않은 색상", "#000000", null, "#dasdad");
        assertThatThrownBy(() -> {
            labelService.createLabel(labelDto);
        }).isInstanceOf(InvalidBgColorException.class);
        verify(labelRepository, never()).save(any(Label.class));
    }

    @DisplayName("유효한 색상 코드를 가진 라벨 수정 요청이면 라벨을 수정할 수 있다.")
    @Test
    public void modifyLabel_WithValidBgColor() {
        LabelDto labelDto = new LabelDto("검정", null, "#000000", "#000000");
        Label mockLabel = createAndReturnMockLabel(labelDto, 1L);
        Label modifiedLabel = labelService.modifyLabel(labelDto, mockLabel.getId());
        verifyLabelProperties(modifiedLabel, 1L, "검정", "#000000");
    }

    @DisplayName("유효하지 않은 색상 코드를 가진 라벨 수정 요청이면 라벨을 수정할 수 없다.")
    @Test
    public void modifyLabel_WithInvalidBgColor() {
        LabelDto labelDto = new LabelDto("올바르지 않은 색상", "#000000", null, "#dasdad");
        assertThatThrownBy(() -> {
            labelService.modifyLabel(labelDto, 1L);
        }).isInstanceOf(InvalidBgColorException.class);
        verify(labelRepository, never()).save(any(Label.class));
    }

    @DisplayName("존재하는 아이디를 가진 라벨 삭제 요청이면 라벨을 삭제할 수 있다.")
    @Test
    public void deleteLabel_WithValidId() {
        Long id = 1L;

        when(labelRepository.existsById(id)).thenReturn(true);

        Long deletedId = labelService.deleteLabel(id);
        assertThat(deletedId).isEqualTo(id);

        verify(labelRepository).deleteById(id);
    }

    @DisplayName("존재하지 않는 아이디를 가진 라벨 삭제 요청이면 라벨을 삭제할 수 없다.")
    @Test
    public void deleteLabel_WithInvalidId() {
        Long id = 10000L;

        when(labelRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> {
            labelService.deleteLabel(10000L);
        }).isInstanceOf(LabelNotFoundException.class);

        verify(labelRepository, never()).deleteById(id);
    }

    private Label createAndReturnMockLabel(LabelDto labelDto, Long id) {
        Label label = new Label(id, labelDto.getName(), labelDto.getDescription(), labelDto.getTextColor(),
                labelDto.getBgColor());
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
