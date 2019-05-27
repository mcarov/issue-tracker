package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Issue;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IssueRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Issue> getIssues() {
        return template.query("SELECT id, title, description, date, votes FROM issues", new BeanPropertyRowMapper<>(Issue.class));
    }

    public void saveIssue(Issue issue) {

    }
}
