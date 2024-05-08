package com.issuetracker.member.repository;

import com.issuetracker.member.model.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJdbcRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public <S extends Member> S save(S entity) {
        String sql = "INSERT INTO member (id, password, nickname, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getId(), entity.getPassword(), entity.getNickname(), entity.getEmail());
        return entity;
    }

    @Override
    public <S extends Member> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public Optional<Member> findById(String id) {
        String sql = "SELECT id, password, nickname, email FROM member WHERE id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class), id)
                .stream()
                .findAny();
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public Iterable<Member> findAll() {
        String sql = "SELECT id, password, nickname, email FROM member";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }

    @Override
    public Iterable<Member> findAllById(Iterable<String> ids) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        String sql = "SELECT id, password, nickname, email FROM member WHERE id IN (:ids)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(Member.class));
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) from member";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Member member) {
        deleteById(member.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids);

        String sql = "DELETE FROM member WHERE id IN (:ids)";
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {
        List<String> ids = getIds(entities);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids);

        String sql = "DELETE FROM member WHERE id IN (:ids)";
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM member";
        jdbcTemplate.update(sql);
    }

    private List<String> getIds(Iterable<? extends Member> entities) {
        List<String> ids = new ArrayList<>();
        for (Member entity : entities) {
            ids.add(entity.getId());
        }
        return ids;
    }
}
