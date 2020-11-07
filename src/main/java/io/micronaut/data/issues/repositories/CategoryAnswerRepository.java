package io.micronaut.data.issues.repositories;


import io.micronaut.data.annotation.Join;
import io.micronaut.data.issues.entities.CategoryAnswer;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.H2)
public abstract class CategoryAnswerRepository implements CrudRepository<CategoryAnswer, UUID> {

    @Join("votes")
    public abstract List<CategoryAnswer> findAll();

    @Join("votes")
    public abstract List<CategoryAnswer> findAllOrderById();
}