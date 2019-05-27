package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Label;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LabelsRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Label> getLabels() {
        return template.query("SELECT id, title FROM labels", new BeanPropertyRowMapper<>(Label.class));
    }

    public void saveLabel(Label label) {

    }
}
