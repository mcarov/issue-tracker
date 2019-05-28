package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Issue;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class IssueRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Issue> getIssues() {
        return template.query("SELECT id, title, description, date, votes FROM issues;",
                new BeanPropertyRowMapper<>(Issue.class));
    }

    public void saveIssue(Issue issue) {
        if(issue.getId() == 0) {
            template.update("INSERT INTO issues (title, description, date, votes) VALUES (:id, :description, :date, :votes);",
                    Map.of("title", issue.getTitle(),
                            "description", issue.getDescription(),
                            "date", issue.getDate(),
                            "votes", issue.getVotes()));
            return;
        }
        template.update("UPDATE issues SET title = :title, description = :description, date = :date, votes = :votes WHERE id = :id;",
                Map.of("id", issue.getId(),
                        "title", issue.getTitle(),
                        "description", issue.getDescription(),
                        "date", issue.getDate(),
                        "votes", issue.getVotes()));
    }
}
