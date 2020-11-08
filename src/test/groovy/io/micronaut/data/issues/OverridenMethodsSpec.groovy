package io.micronaut.data.issues

import static java.util.stream.Collectors.toMap
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
        Map<UUID, CategoryAnswer> answersInserted = new HashMap<>()

        when:
        for (i in 1..4) {
            CategoryAnswer answer = new CategoryAnswer()
            answer.setAnswer(i)

            answer = categoryAnswerrepository.save(answer)

            for (j in 1..2) {
                AnswerVote vote = new AnswerVote()
                vote.setVote(j + 2*(i-1))
                vote.setAnswer(answer)

                vote = answerVoteRepository.save(vote)
                answer.getVotes().add(vote)
            }

            answersInserted.put(answer.getId(), answer)
        }

        CategoryAnswer answer = new CategoryAnswer()
        answer.setAnswer(5)
        answer = categoryAnswerrepository.save(answer)

        AnswerVote vote = new AnswerVote()
        vote.setVote(9)
        vote.setAnswer(answer)

        vote = answerVoteRepository.save(vote)
        answer.getVotes().add(vote)

        answersInserted.put(answer.getId(), answer)

        answer = new CategoryAnswer()
        answer.setAnswer(6)
        answer = categoryAnswerrepository.save(answer)
        for (j in 1..2) {
            vote = new AnswerVote()
            vote.setVote(j + 9)
            vote.setAnswer(answer)

            vote = answerVoteRepository.save(vote)
            answer.getVotes().add(vote)
        }
        answersInserted.put(answer.getId(), answer)

        then:
        categoryAnswerrepository.count() == answersInserted.size()

        when:
        List<CategoryAnswer> answers = categoryAnswerrepository.findAllOrderById()
        Map<UUID, CategoryAnswer> answersMap = answers.stream().collect(toMap(a -> a.getId() , a -> a));

        then:
        answers.size() == answersInserted.size()
        answersMap == answersInserted

        when:
        List<CategoryAnswer> answersFailure = categoryAnswerrepository.findAll()
        Map<UUID, CategoryAnswer> answersFailureMap = answersFailure.stream().collect(toMap(a -> a.getId() , a -> a));

        then:
        answersFailure.size() == answersInserted.size()
        answersFailureMap == answersInserted
    }
}
