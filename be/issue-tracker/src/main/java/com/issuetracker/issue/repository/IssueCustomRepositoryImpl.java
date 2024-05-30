package com.issuetracker.issue.repository;

import com.issuetracker.issue.dto.IssueFilterResult;
import com.issuetracker.issue.dto.IssueQueryDto;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IssueCustomRepositoryImpl implements IssueCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Set<IssueFilterResult> findIssueWithFilter(Map<String, Object> filter, IssueQueryDto issueQueryDto) {
        String sql = (String) filter.get("sql");
        Object[] params = (Object[]) filter.get("params");
        List<IssueFilterResult> result = jdbcTemplate.query(sql, params, issueFilterDtoMapper());
        return new LinkedHashSet<>(result);
    }

    private RowMapper<IssueFilterResult> issueFilterDtoMapper() {
        return (rs, rowNum) -> (IssueFilterResult.builder()
                .id(rs.getLong("i.id"))
                .title(rs.getString("title"))
                .createDate(rs.getTimestamp("i.create_date").toLocalDateTime())
                .isClosed(rs.getBoolean("i.is_closed"))
                .authorId(rs.getString("i.member_id"))
                .milestoneName(rs.getString("name"))
                .build()
        );
    }
}
