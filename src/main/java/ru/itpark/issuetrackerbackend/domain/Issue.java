package ru.itpark.issuetrackerbackend.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP")
public class Issue {
    private long id;
    private String title;
    private String description;
    private Date date;
    private long votes;
    private Label[] labels;
}
