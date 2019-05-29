package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Issue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            template.update("INSERT INTO issues (title, description, date, votes) VALUES (:id, :description, :date, :votes);",
                    Map.of("title", issue.getTitle(),
                            "description", issue.getDescription(),
                            "date", issue.getDate(),
                            "votes", issue.getVotes()));

            Optional<Long> issueId = Optional.ofNullable(template.getJdbcTemplate().queryForObject("SELECT last_insert_rowid();", Long.class));
            issue.setId(issueId.orElseThrow(() -> new EmptyResultDataAccessException(1)));

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
