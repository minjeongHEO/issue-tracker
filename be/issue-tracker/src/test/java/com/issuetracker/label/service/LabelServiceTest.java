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
    public void createNewLabel_WithValidBgColor() {
        // 유효한 색상 코드를 가진 LabelCreateDto 설정
        labelDto.setName("검정");
        labelDto.setBgColor("#000000");

        // save 메소드가 호출될 때 리턴할 가짜 Label 객체 생성
        Label savedLabel = new Label("검정", null, "#000000");
        when(labelRepository.insert(any(Label.class))).thenReturn(savedLabel);

        // createNewLabel 메소드 호출 및 반환값 검증
        Label createdLabel = labelService.createNewLabel(labelDto);
        assertThat(createdLabel).isNotNull();
        assertThat(createdLabel.getName()).isEqualTo("검정");
        assertThat(createdLabel.getBgColor()).isEqualTo("#000000");
    }

    @DisplayName("유효하지 않은 색상 코드를 가진 라벨 생성 요청이면 새 라벨을 생성할 수 없다.")
    @Test
    public void createNewLabel_WithInvalidBgColor() {
        // 유효하지 않은 색상 코드를 가진 LabelCreateDto 생성
        labelDto.setName("올바르지 않은 색상");
        labelDto.setBgColor("#dasdad");

        // createNewLabel 메소드 호출 시 InvalidBgColorException이 발생하는지 검증
        assertThatThrownBy(() -> {
            labelService.createNewLabel(labelDto);
        }).isInstanceOf(InvalidBgColorException.class);

        // save 메소드가 호출되지 않았는지 검증
        verify(labelRepository, never()).save(any(Label.class));
    }
}