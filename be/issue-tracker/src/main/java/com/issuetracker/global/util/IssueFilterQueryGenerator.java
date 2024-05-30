package com.issuetracker.global.util;

import com.issuetracker.issue.dto.IssueQueryDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class IssueFilterQueryGenerator {
    private final IssueQueryDto issueQueryDto;
    private final List<String> noValues;

    public IssueFilterQueryGenerator(IssueQueryDto issueQueryDto) {
        this.issueQueryDto = issueQueryDto;
        this.noValues = issueQueryDto.getNoValues();
    }

    public Map<String, Object> generate(Long labelId, Long milestoneId) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT i.id, i.title, i.create_date, i.is_closed, i.member_id, m.name " +
                "FROM issue i " +
                "LEFT JOIN issue_label il ON i.id = il.issue_id " +
                "LEFT JOIN issue_assignee ia ON i.id = ia.issue_id " +
                "LEFT JOIN comment c ON i.id = c.issue_id " +
                "LEFT JOIN milestone m ON i.milestone_id = m.id");

        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        appendAuthorFilter(conditions, params, issueQueryDto.getAuthorId());
        appendAssigneeFilter(conditions, params, issueQueryDto.getAssigneeId());
        appendMentionedFilter(conditions, params, issueQueryDto.getMentionedId());
        appendLabelFilter(conditions, params, labelId);
        appendMilestoneFilter(conditions, params, milestoneId);
        appendConditions(conditions, sql);
        sql.append(" ORDER BY i.create_date DESC, i.id DESC;"); // 최신순으로 정렬

        result.put("params", params.toArray());
        result.put("sql", sql.toString());
        return result;
    }

    private void appendAuthorFilter(List<String> conditions, List<Object> params, String authorId) {
        if (StringUtils.hasText(authorId)) { // 작성자 필터 조건이 있다면
            conditions.add("i.member_id = ?");
            params.add(authorId);
        }
    }

    private void appendAssigneeFilter(List<String> conditions, List<Object> params, String assigneeId) {
        if (StringUtils.hasText(assigneeId)) { // 담당자 필터 조건이 있다면
            conditions.add("ia.member_id = ?");
            params.add(assigneeId);
        }
        if (noValues.contains("assignee")) { // 담당자가 없는 필터 조건
            conditions.add("ia.member_id IS NULL");
        }
    }

    private void appendMentionedFilter(List<String> conditions, List<Object> params, String mentionedId) {
        if (StringUtils.hasText(mentionedId)) { // 댓글을 작성한 사용자 필터 조건이 있다면
            conditions.add("c.member_id = ?");
            params.add(mentionedId);
        }
    }

    private void appendLabelFilter(List<String> conditions, List<Object> params, Long labelId) {
        if (labelId != null) { // 라벨 필터 조건이 있다면
            conditions.add("il.label_id = ?");
            params.add(labelId);
        }
        if (noValues.contains("label")) { // 라벨이 없는 필터 조건
            conditions.add("il.label_id IS NULL");
        }
    }

    private void appendMilestoneFilter(List<String> conditions, List<Object> params, Long milestoneId) {
        if (milestoneId != null) { // 마일스톤 필터 조건이 있다면
            conditions.add("i.milestone_id = ?");
            params.add(milestoneId);
        }
        if (noValues.contains("milestone")) {  // 마일스톤이 없는 필터 조건
            conditions.add("i.milestone_id IS NULL");
        }
    }

    private void appendConditions(List<String> conditions, StringBuilder sql) {
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(String.join(" AND ", conditions));
        }
    }
}
