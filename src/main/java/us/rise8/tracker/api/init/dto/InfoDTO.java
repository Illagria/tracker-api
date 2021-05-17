package us.rise8.tracker.api.init.dto;

import lombok.Getter;

@Getter
public class InfoDTO {
    public InfoDTO(String classificationString, String caveat) {
        this.classification = new ClassificationDTO(classificationString, caveat);
    }
    private final ClassificationDTO classification;
}
