package com.issuetracker.member.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Table
public class Member {
    @Id
    private final String id;
    private final String password;
    private final String nickname;
    private final String email;
    private final Long fileId;

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }
}
