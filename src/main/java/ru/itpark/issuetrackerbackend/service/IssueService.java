package ru.itpark.issuetrackerbackend.service;

import lombok.RequiredArgsConstructor;
import ru.itpark.issuetrackerbackend.domain.Issue;
import ru.itpark.issuetrackerbackend.repository.IssueRepository;

import java.util.List;

@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    public List<Issue> getIssues() {
        return issueRepository.getIssues();
    }

    public void saveIssue(Issue issue) {
        issueRepository.saveIssue(issue);
    }
}
