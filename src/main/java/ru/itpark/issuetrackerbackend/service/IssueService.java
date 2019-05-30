package ru.itpark.issuetrackerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.issuetrackerbackend.domain.Issue;
import ru.itpark.issuetrackerbackend.domain.Label;
import ru.itpark.issuetrackerbackend.repository.IssueLabelIdRepository;
import ru.itpark.issuetrackerbackend.repository.IssueRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Issue> searchForIssues(String query) {
        List<Issue> issues = issueRepository.searchForIssues(query);

        List<Long> labelIds = labelService.getLabelIdsForSearch(query);
        List<Issue> issuesFoundByLabel = labelIds.stream().map(issueLabelIdRepository::getIssueIdsByLabelId).
                flatMap(Collection::stream).map(issueRepository::getIssueById).collect(Collectors.toList());

        Map<Long, Issue> issueMap = new TreeMap<>();
        issueMap.putAll(Stream.concat(issues.stream(), issuesFoundByLabel.stream()).
                collect(Collectors.toMap(Issue::getId, issue -> issue, (ke1, key2) -> ke1)));
        issueMap.values().forEach(this::addLabelsToIssue);

        return new ArrayList<>(issueMap.values());
    }

    public Issue getIssueById(long id) {
        Issue issue = issueRepository.getIssueById(id);
        addLabelsToIssue(issue);

        return issue;
    }

    public void saveIssue(Issue issue) {
        issueRepository.saveIssue(issue);

        Arrays.asList(issue.getLabels()).forEach(i -> {
            labelService.saveLabel(i);
            issueLabelIdRepository.save(issue.getId(), i.getId());
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
