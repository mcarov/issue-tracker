package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IssueLabelIdRepository {
    private final NamedParameterJdbcTemplate template;

    public long countLabelId(long id) {
        Optional<Long> count = Optional.ofNullable(template.queryForObject(
                "SELECT COUNT(*) FROM issues_labels WHERE label_id = :id", Map.of("id", id), Long.class));
        return count.orElse(0L);
    }

    public List<Long> getIssueIdsByLabelId(long id) {
        return template.query("SELECT issue_id FROM issues_labels WHERE label_id = :id;",
                Map.of("id", id),
                (resultSet, i) -> resultSet.getLong(1));
    }

    public List<Long> getLabelIdsByIssueId(long id) {
        return template.query("SELECT label_id FROM issues_labels WHERE issue_id = :id;",
                Map.of("id", id),
                (resultSet, i) -> resultSet.getLong(1));
    }

    public void save(long issueId, long labelId) {
        template.update("MERGE INTO issues_labels (issue_id, label_id) KEY (issue_id, label_id) VALUES (:issueId, :labelId);",
                Map.of("issueId", issueId, "labelId", labelId));
    }

    public void removeLabelIdsByIssueId(long id) {
        template.update("DELETE FROM issues_labels WHERE issue_id = :id", Map.of("id", id));
    }
}
