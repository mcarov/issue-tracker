package ru.itpark.issuetrackerbackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Label {
    private long id;
    private String title;
}
