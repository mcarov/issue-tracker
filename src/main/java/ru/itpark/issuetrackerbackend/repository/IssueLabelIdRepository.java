package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class IssueLabelIdRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Long> getLabelIdsByIssueId(long id) {
        return template.query("SELECT label_id FROM issues_labels WHERE issue_id = :id;",
                Map.of("id", id), ((resultSet, i) -> resultSet.getLong(1)));
    }

    public void save(long issueId, long labelId) {
        template.update("INSERT INTO issues_labels (issue_id, label_id) VALUES (:issueId, :labelId);",
                Map.of("issueId", issueId, "labelId", labelId));
    }
}
