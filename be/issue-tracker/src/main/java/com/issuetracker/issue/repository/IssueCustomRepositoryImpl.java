package com.issuetracker.issue.repository;

import com.issuetracker.issue.dto.IssueFilterDto;
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
    public Set<IssueFilterDto> findIssueWithFilter(Map<String, Object> filter, IssueQueryDto issueQueryDto) {
        String sql = (String) filter.get("sql");
        Object[] params = (Object[]) filter.get("params");
        List<IssueFilterDto> result = jdbcTemplate.query(sql, params, issueFilterDtoMapper());
        return new LinkedHashSet<>(result);
    }

    private RowMapper<IssueFilterDto> issueFilterDtoMapper() {
        return (rs, rowNum) -> (IssueFilterDto.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .authorId(rs.getString("member_id"))
                .createDate(rs.getTimestamp("create_date").toLocalDateTime())
                .milestoneName(rs.getString("name"))
                .build()
        );
    }
}
