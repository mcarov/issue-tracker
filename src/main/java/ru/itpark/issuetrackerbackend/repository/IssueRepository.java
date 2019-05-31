package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Issue;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class IssueRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Issue> getIssues() {
        return template.query("SELECT id, title, description, date, votes FROM issues;",
                new BeanPropertyRowMapper<>(Issue.class));
    }

    public List<Issue> searchForIssues(String query) {
        return template.query("SELECT id, title, description, date, votes FROM issues WHERE " +
                        "lower(title) LIKE :query OR lower(description) LIKE :query;",
                Map.of("query", String.join("", "%", query.toLowerCase(), "%")),
                new BeanPropertyRowMapper<>(Issue.class));
    }

    public Issue getIssueById(long id) {
        return template.queryForObject("SELECT id, title, description, date, votes FROM issues WHERE id = :id;",
                Map.of("id", id), new BeanPropertyRowMapper<>(Issue.class));
    }

    public void saveIssue(Issue issue) {
        if(issue.getId() == 0) {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource().
                    addValue("title", issue.getTitle()).
                    addValue("description", issue.getDescription()).
                    addValue("date", issue.getDate()).
                    addValue("votes", issue.getVotes());

            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            template.update("INSERT INTO issues (title, description, date, votes) VALUES (:id, :description, :date, :votes);",
                    sqlParameterSource, keyHolder);

            long issueId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            issue.setId(issueId);

            return;
        }
        template.update("UPDATE issues SET title = :title, description = :description, date = :date, votes = :votes WHERE id = :id;",
                Map.of("id", issue.getId(),
                        "title", issue.getTitle(),
                        "description", issue.getDescription(),
                        "date", issue.getDate(),
                        "votes", issue.getVotes()));
    }

    public void removeIssueById(long id) {
        template.update("DELETE FROM issues WHERE id = :id;", Map.of("id", id));
    }
}
