package ru.itpark.issuetrackerbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Label {
    private long id;
    private String title;
}
