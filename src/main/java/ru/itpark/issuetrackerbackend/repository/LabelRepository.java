package ru.itpark.issuetrackerbackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issuetrackerbackend.domain.Label;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LabelRepository {
    private final NamedParameterJdbcTemplate template;

    public List<Long> getLabelIdsForSearch(String query) {
        return template.query("SELECT id, title FROM labels WHERE lower(title) LIKE :query;",
                Map.of("query", String.join("", "%", query.toLowerCase(), "%")),
                (resultSet, i) -> resultSet.getLong(1));
    }

    public Label getLabelById(long id) {
        return template.queryForObject("SELECT id, title FROM labels WHERE id = :id;",
                Map.of("id", id), new BeanPropertyRowMapper<>(Label.class));
    }

    public void saveLabel(Label label) {
        if(label.getId() == 0) {
            template.update("INSERT INTO labels (title) VALUES (:title);", Map.of("title", label.getTitle()));

            Optional<Long> labelId = Optional.ofNullable(template.getJdbcTemplate().queryForObject("SELECT last_insert_rowid();", Long.class));
            label.setId(labelId.orElseThrow(() -> new EmptyResultDataAccessException(1)));

            return;
        }
        template.update("UPDATE labels SET title = :title WHERE id = :id;", Map.of("id", label.getId()));
    }

    public void removeLabelById(long id) {
        template.update("DELETE FROM labels WHERE id = :id", Map.of("id", id));
    }
}
