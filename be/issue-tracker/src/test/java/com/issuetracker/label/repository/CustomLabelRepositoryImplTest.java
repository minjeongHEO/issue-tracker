package com.issuetracker.label.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.issuetracker.label.domain.Label;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomLabelRepositoryImplTest {
    @Autowired
    LabelRepository labelRepository;

    @BeforeEach
    void setUp() {
        labelRepository.deleteAll();
    }

    @DisplayName("라벨이 데이터베이스에 저장된다.")
    @Test
    void insert() {
        // Given
        Label label = new Label("버그", null, "#ff0000");

        // When
        Label savedLabel = labelRepository.insert(label);

        // Then
        assertThat(savedLabel.toString()).isEqualTo(label.toString());

        // 정말로 DB에 삽입되었는지 검증한다.
        Optional<Label> findLabelOptional = labelRepository.findById(savedLabel.getName());
        assertThat(findLabelOptional.isPresent()).isTrue();

        Label findLabel = findLabelOptional.get();
        assertThat(findLabel.toString()).isEqualTo(label.toString());
    }
}