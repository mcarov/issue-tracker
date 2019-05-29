package ru.itpark.issuetrackerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.issuetrackerbackend.domain.Label;
import ru.itpark.issuetrackerbackend.repository.LabelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    public Label getLabelById(long id) {
        return labelRepository.getLabelById(id);
    }

    public void saveLabel(Label label) {
        labelRepository.saveLabel(label);
    }

    public void removeLabelById(long id) {
        labelRepository.removeLabelById(id);
    }
}
