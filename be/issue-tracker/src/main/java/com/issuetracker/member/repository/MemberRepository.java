package com.issuetracker.member.repository;

import com.issuetracker.global.repository.WithInsert;
import com.issuetracker.member.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String>, WithInsert<Member> {
}
