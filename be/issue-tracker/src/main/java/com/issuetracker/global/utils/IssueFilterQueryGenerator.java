package com.issuetracker.global.utils;

import com.issuetracker.issue.dto.IssueQueryDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueFilterQueryGenerator {
    public static Map<String, Object> generate(IssueQueryDto issueQueryDto) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT i.id, i.title, i.member_id, i.create_date, m.name " +
                "FROM issue i " +
                "LEFT JOIN issue_label il ON i.id = il.issue_id " +
                "LEFT JOIN issue_assignee ia ON i.id = ia.issue_id " +
                "LEFT JOIN comment c ON i.id = c.issue_id " +
                "LEFT JOIN milestone m ON i.milestone_id = m.id " +
                "WHERE i.is_closed = ? ");

        List<Object> params = new ArrayList<>();
        params.add(issueQueryDto.getIsClosed()); // 열림/닫힘 여부는 기본으로 설정되어 있는 필터

        appendAuthorFilter(sql, params, issueQueryDto.getAuthorId());
        appendAssigneeFilter(sql, params, issueQueryDto.getAssigneeId());
        appendMentionedFilter(sql, params, issueQueryDto.getMentionedId());
        appendLabelFilter(sql, params, issueQueryDto.getLabelId());
        appendMilestoneFilter(sql, params, issueQueryDto.getMilestoneId());

        sql.append("ORDER BY i.create_date DESC"); // 최신순으로 정렬

        result.put("params", params.toArray());
        result.put("sql", sql.toString());
        return result;
    }

    private static void appendAuthorFilter(StringBuilder sql, List<Object> params, String authorId) {
        if (authorId != null) { // 작성자 필터 조건이 있다면
            sql.append("AND i.member_id = ? ");
            params.add(authorId);
        }
    }

    private static void appendAssigneeFilter(StringBuilder sql, List<Object> params, String assigneeId) {
        if (assigneeId != null) { // 담당자 필터 조건이 있다면
            if (assigneeId.equals("-1")) { // 담당자가 없는 필터 조건
                sql.append("AND ia.member_id IS NULL ");
            } else { // 특정 담당자 필터 조건
                sql.append("AND ia.member_id = ? ");
                params.add(assigneeId);
            }
        }
    }

    private static void appendMentionedFilter(StringBuilder sql, List<Object> params, String mentionedId) {
        if (mentionedId != null) { // 댓글을 작성한 사용자 필터 조건이 있다면
            sql.append("AND c.member_id = ? ");
            params.add(mentionedId);
        }
    }

    private static void appendLabelFilter(StringBuilder sql, List<Object> params, Long labelId) {
        if (labelId != null) { // 라벨 필터 조건이 있다면
            if (labelId == -1L) { // 라벨이 없는 필터 조건
                sql.append("AND il.label_id IS NULL ");
            } else { // 특정 라벨 필터 조건
                sql.append("AND il.label_id = ? ");
                params.add(labelId);
            }
        }
    }

    private static void appendMilestoneFilter(StringBuilder sql, List<Object> params, Long milestoneId) {
        if (milestoneId != null) { // 마일스톤 필터 조건이 있다면
            if (milestoneId == -1L) { // 마일스톤이 없는 필터 조건
                sql.append("AND i.milestone_id IS NULL ");
            } else { // 특정 마일스톤 필터 조건
                sql.append("AND i.milestone_id = ? ");
                params.add(milestoneId);
            }
        }
    }
}
