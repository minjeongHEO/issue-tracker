package com.issuetracker.member.repository;

import com.issuetracker.global.repository.WithInsert;
import com.issuetracker.member.model.Member;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String>, WithInsert<Member> {
    @Query("SELECT file_id FROM member WHERE id = :id")
    Long findFileIdById(String id);
}
