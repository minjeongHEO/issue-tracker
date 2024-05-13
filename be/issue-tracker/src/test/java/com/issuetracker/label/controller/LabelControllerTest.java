package com.issuetracker.label.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.service.LabelService;
import com.issuetracker.label.utils.HexColorGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(LabelController.class)
class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelService labelService;

    @DisplayName("라벨 생성 API를 사용하여 새 라벨을 생성할 수 있다.")
    @Test
    void createLabels() throws Exception {
        // Given
        String labelDtoJson = """
                {
                    "name": "버그",
                    "description": null,
                    "bgColor": "#ff0000"
                }
                """;
        Label label = new Label("버그", null, "#ff0000");
        label.setId(1L);

        given(labelService.createLabel(any(LabelDto.class))).willReturn(label);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/labels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(labelDtoJson)); // JSON 문자열 사용

        // Then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(label.getId().toString()))
                .andExpect(jsonPath("$.name").value(label.getName()))
                .andExpect(jsonPath("$.description").isEmpty())
                .andExpect(jsonPath("$.bgColor").value(label.getBgColor()));
    }

    @DisplayName("라벨 수정 API를 사용하여 라벨을 수정할 수 있다.")
    @Test
    void modifyLabel() throws Exception {
        // Given
        String labelDtoJson = """
                {
                    "name": "버그 수정",
                    "description": "수정된 설명",
                    "bgColor": "#00ff00"
                }
                """;
        Label updatedLabel = new Label("버그 수정", "수정된 설명", "#00ff00");
        updatedLabel.setId(1L);

        given(labelService.modifyLabel(any(LabelDto.class), eq(1L))).willReturn(updatedLabel);

        // When
        ResultActions resultActions = mockMvc.perform(put("/api/labels/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(labelDtoJson)); // JSON 문자열 사용

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedLabel.getId().toString()))
                .andExpect(jsonPath("$.name").value(updatedLabel.getName()))
                .andExpect(jsonPath("$.description").value(updatedLabel.getDescription()))
                .andExpect(jsonPath("$.bgColor").value(updatedLabel.getBgColor()));
    }

    @DisplayName("라벨 삭제 API를 사용하여 라벨을 삭제할 수 있다.")
    @Test
    void deleteLabel() throws Exception {
        // Given
        Long labelId = 1L;
        given(labelService.deleteLabel(labelId)).willReturn(1L);

        // When & Then
        mockMvc.perform(delete("/api/labels/{id}", labelId))
                .andExpect(status().isOk());
    }

    @DisplayName("라벨 배경 색상 랜덤 생성 API를 사용하여 배경 색상을 랜덤으로 얻을 수 있다.")
    @Test
    void refreshLabelBackgroundColor() throws Exception {
        try (MockedStatic<HexColorGenerator> mockedStatic = Mockito.mockStatic(HexColorGenerator.class)) {
            // generateRandomHexColor 메소드가 호출될 때 "#FFFFFF"를 반환하도록 설정
            mockedStatic.when(HexColorGenerator::generateRandomHexColor).thenReturn("#FFFFFF");

            mockMvc.perform(get("/api/labels/bgcolor/refresh")
                            .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk())
                    .andExpect(content().string("#FFFFFF"));
        }
    }
}
