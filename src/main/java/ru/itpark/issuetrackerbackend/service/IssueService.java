package ru.itpark.issuetrackerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.issuetrackerbackend.domain.Issue;
import ru.itpark.issuetrackerbackend.domain.Label;
import ru.itpark.issuetrackerbackend.repository.IssueLabelIdRepository;
import ru.itpark.issuetrackerbackend.repository.IssueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final IssueLabelIdRepository issueLabelIdRepository;
    private final LabelService labelService;

    public List<Issue> getIssues() {
        List<Issue> issues = issueRepository.getIssues();
        issues.forEach(this::addLabelsToIssue);

        return issues;
    }

    public Issue getIssueById(long id) {
        Issue issue = issueRepository.getIssueById(id);
        addLabelsToIssue(issue);

        return issue;
    }

    public void saveIssue(Issue issue) {
        issueRepository.saveIssue(issue);

        Arrays.asList(issue.getLabels()).forEach(i -> {
            issueLabelIdRepository.save(issue.getId(), i.getId());
            labelService.saveLabel(i);
        });
    }

    public void removeIssueById(long id) {
        issueRepository.removeIssueById(id);
        List<Long> labelIds = issueLabelIdRepository.getLabelIdsByIssueId(id);
        issueLabelIdRepository.removeLabelIdsByIssueId(id);
        for(Long labelId : labelIds) {
            long count = issueLabelIdRepository.countLabelId(labelId);
            if(count == 0)
                labelService.removeLabelById(labelId);
        }
    }

    private void addLabelsToIssue(Issue issue) {
        List<Long> labelIds = issueLabelIdRepository.getLabelIdsByIssueId(issue.getId());
        Label[] labels = labelIds.stream().map(labelService::getLabelById).collect(Collectors.toList()).toArray(Label[]::new);
        issue.setLabels(labels);
    }
}
