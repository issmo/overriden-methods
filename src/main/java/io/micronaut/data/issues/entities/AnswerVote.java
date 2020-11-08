package io.micronaut.data.issues.entities;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Id;
import java.util.UUID;

@Data
@MappedEntity("votes")
public class AnswerVote {
    @Id
    @AutoPopulated
    UUID id;

    @Relation(value = Relation.Kind.MANY_TO_ONE, cascade = Relation.Cascade.ALL)
    @MappedProperty(value = "answer_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    CategoryAnswer answer;

    int vote;
}