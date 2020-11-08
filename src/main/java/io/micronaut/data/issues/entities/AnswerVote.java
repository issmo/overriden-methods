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
@MappedEntity(value = "votes", escape = false)
public class AnswerVote {
    @Id
    @AutoPopulated
    UUID id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Relation(value = Relation.Kind.MANY_TO_ONE, cascade = Relation.Cascade.ALL)
    @MappedProperty(value = "answer_id")
    CategoryAnswer answer;

    int vote;
}