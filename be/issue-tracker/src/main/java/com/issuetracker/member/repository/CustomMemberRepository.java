package com.issuetracker.member.repository;

import com.issuetracker.member.model.Member;

public interface CustomMemberRepository {
    Member insert(Member member);
}
