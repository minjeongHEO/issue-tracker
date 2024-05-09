package com.issuetracker.member.repository;

import com.issuetracker.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomMemberRepositoryImpl implements CustomMemberRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Member insert(Member member) {
        String sql = "INSERT INTO member (id, password, nickname, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getId(), member.getPassword(), member.getNickname(), member.getEmail());
        return member;
    }

    @Override
    public Member update(Member member) {
        String sql = "UPDATE member SET password = ?, nickname = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, member.getPassword(), member.getNickname(), member.getEmail(), member.getId());
        return member;
    }
}
