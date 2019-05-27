package ru.itpark.issuetrackerbackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Issue {
    private long id;
    private String title;
    private String description;
    private Date date;
    private long votes;
    private Label[] labels;
}
