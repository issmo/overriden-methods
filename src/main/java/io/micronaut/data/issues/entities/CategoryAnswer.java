package io.micronaut.data.issues.entities;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@MappedEntity("answers")
public class CategoryAnswer implements Serializable {
    @Id
    @AutoPopulated
    private UUID id;

    private String answer;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "answer")
    Set<AnswerVote> votes = new HashSet<>();
}
