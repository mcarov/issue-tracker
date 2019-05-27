package ru.itpark.issuetrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.issuetrackerbackend.service.IssueService;

@RestController
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
}
