package com.questioner.knapp.api.repositories;

import com.questioner.knapp.core.models.QType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RepositoryDefinition(domainClass = QType.class, idClass = Long.class)
public interface QTypeRepository extends CrudRepository<QType, Long> {

}
