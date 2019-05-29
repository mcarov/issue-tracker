package ru.itpark.issuetrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itpark.issuetrackerbackend.domain.Issue;
import ru.itpark.issuetrackerbackend.service.IssueService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping("/api/issues")
    public List<Issue> getIssues() {
        return issueService.getIssues();
    }

    @GetMapping("api/search/issues")
    public List<Issue> searchForIssues(@RequestParam String q) {
        return issueService.searchForIssues(q);
    }

    @GetMapping("/api/issues/{id}")
    public Issue getIssueById(@PathVariable long id) {
        return issueService.getIssueById(id);
    }

    @PostMapping("/api/issues")
    public void saveIssue(@RequestBody Issue issue) {
        issueService.saveIssue(issue);
    }

    @DeleteMapping("/api/issues/{id}")
    public void removeIssue(@PathVariable long id) {
        issueService.removeIssueById(id);
    }
}
