package com.questioner.knapp.api.repositories;

import com.questioner.knapp.core.models.QElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RepositoryDefinition(domainClass = QElement.class, idClass = Long.class)
public interface QElementRepository extends CrudRepository<QElement, Long> {

}
