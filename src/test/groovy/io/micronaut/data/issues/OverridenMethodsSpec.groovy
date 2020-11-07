package io.micronaut.data.issues

import io.micronaut.data.issues.entities.AnswerVote
import io.micronaut.data.issues.entities.CategoryAnswer
import io.micronaut.data.issues.repositories.AnswerVoteRepository
import io.micronaut.data.issues.repositories.CategoryAnswerRepository
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class OverridenMethodsSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    @Inject
    CategoryAnswerRepository categoryAnswerrepository

    @Inject
    AnswerVoteRepository answerVoteRepository

    void 'test method override'() {
        expect:
        application.running
    }

    void "test issue  #669"() {
        when:

        for (i in 1..4) {
            CategoryAnswer answer = new CategoryAnswer()
            answer.setAnswer("answer " + i)

            answer = categoryAnswerrepository.save(answer)

            for (j in 1..2) {
                AnswerVote vote = new AnswerVote()
                vote.setVote(j + 2*(i-1))
                vote.setAnswer(answer)

                answerVoteRepository.save(vote)
            }
        }

        then:
        categoryAnswerrepository.count() == 4

        when:
        List<CategoryAnswer> answers = categoryAnswerrepository.findAllOrderById()

        then:
        answers.get(0).getVotes().size() == 2
        answers.get(1).getVotes().size() == 2
        answers.get(2).getVotes().size() == 2
        answers.get(3).getVotes().size() == 2

        when:
        List<CategoryAnswer> answersFailure = categoryAnswerrepository.findAll()

        then:
        answersFailure.size() == 4

        answersFailure.get(0).getVotes().size() == 2
        answersFailure.get(1).getVotes().size() == 2
        answersFailure.get(2).getVotes().size() == 2
        answersFailure.get(3).getVotes().size() == 2
    }

}
