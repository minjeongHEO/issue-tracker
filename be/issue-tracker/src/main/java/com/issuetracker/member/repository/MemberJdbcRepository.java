package com.issuetracker.member.repository;

import com.issuetracker.member.model.Member;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJdbcRepository implements MemberRepository {
    @Override
    public <S extends Member> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Member> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Member> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Member> findAll() {
        return null;
    }

    @Override
    public Iterable<Member> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Member entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
