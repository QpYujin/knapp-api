package com.questioner.knapp.api.repositories;

import com.questioner.knapp.core.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RepositoryDefinition(domainClass = Question.class, idClass = Long.class)
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
