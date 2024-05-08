package com.issuetracker.member.repository;

import com.issuetracker.member.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String>, CustomMemberRepository {
}
