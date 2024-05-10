package com.issuetracker.label.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetracker.label.domain.Label;
import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.exception.InvalidBgColorException;
import com.issuetracker.label.service.LabelService;
import com.issuetracker.label.utils.HexColorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LabelController.class)
class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LabelService labelService;
    private LabelDto labelDto;

    @BeforeEach
    void setLabelDto() {
        labelDto = new LabelDto();
        labelDto.setName("버그");
        labelDto.setDescription(null);
        labelDto.setBgColor("#ff0000");
    }

    @DisplayName("라벨 생성 API를 사용하여 새 라벨을 생성할 수 있다.")
    @Test
    void createLabels() throws Exception {
        Label label = new Label("버그", null, "#ff0000");
        label.setId(1L);

        given(labelService.createLabel(any(LabelDto.class))).willReturn(label);

        mockMvc.perform(post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", String.format("/api/labels/%s", label.getId())))
                .andExpect(jsonPath("$.id").value(label.getId().toString()))
                .andExpect(jsonPath("$.name").value(label.getName()))
                .andExpect(jsonPath("$.description").isEmpty())
                .andExpect(jsonPath("$.bgColor").value(label.getBgColor()));
    }

    @DisplayName("라벨 생성 API에서 데이터 바인딩이 실패한 경우 상태코드 400을 반환한다.")
    @Test
    public void createLabels_WithBindingErrors() throws Exception {
        // 예를 들어, Name 필드가 비어있어서 유효하지 않은 경우로 설정
        labelDto.setName("");

        mockMvc.perform(post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isBadRequest()); // BAD_REQUEST(400) 상태 코드를 기대한다.
    }

    @DisplayName("라벨 생성 API에서 라벨 DTO의 배경 색깔이 유효하지 않다면 상태코드 400을 반환한다.")
    @Test
    public void createLabels_WithInvalidBgColor() throws Exception {
        // 유효하지 않은 색상 코드로 배경 색 설정
        labelDto.setBgColor("#12"); // 유효하지 않은 색상 코드

        given(labelService.createLabel(any(LabelDto.class))).willThrow(InvalidBgColorException.class);

        mockMvc.perform(post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isBadRequest()); // BAD_REQUEST(400) 상태 코드를 기대한다.
    }

    @DisplayName("라벨 생성 API로 이미 존재하는 라벨 이름으로 요청하면 생성하지 않고 상태코드 409를 반환한다.")
    @Test
    public void createLabels_WithDuplicateName() throws Exception {
        labelDto.setName("existingLabelName");

        given(labelService.createLabel(any(LabelDto.class))).willThrow(DuplicateKeyException.class);

        mockMvc.perform(post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isConflict()); // CONFLICT(409) 상태 코드를 기대한다.
    }

    @DisplayName("라벨 수정 API를 사용하여 라벨을 수정할 수 있다.")
    @Test
    void modifyLabel() throws Exception {
        labelDto.setName("버그 수정");
        labelDto.setDescription("수정된 설명");
        labelDto.setBgColor("#00ff00");

        Label updatedLabel = new Label("버그 수정", "수정된 설명", "#00ff00");
        updatedLabel.setId(1L);

        given(labelService.modifyLabel(any(LabelDto.class), eq(1L))).willReturn(updatedLabel);

        mockMvc.perform(put("/api/labels/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(labelDto))) // JSON 문자열 사용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedLabel.getId().toString()))
                .andExpect(jsonPath("$.name").value(updatedLabel.getName()))
                .andExpect(jsonPath("$.description").value(updatedLabel.getDescription()))
                .andExpect(jsonPath("$.bgColor").value(updatedLabel.getBgColor()));
    }

    @DisplayName("라벨 삭제 API를 사용하여 라벨을 삭제할 수 있다.")
    @Test
    void deleteLabel() throws Exception {
        Long labelId = 1L;
        given(labelService.deleteLabel(labelId)).willReturn(1L);

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
