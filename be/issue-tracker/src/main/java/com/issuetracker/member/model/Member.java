package com.issuetracker.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("member")
@Getter
@RequiredArgsConstructor
public class Member {
    @Id
    private final String id;
    private final String password;
    private final String nickname;
    private final String email;
}
