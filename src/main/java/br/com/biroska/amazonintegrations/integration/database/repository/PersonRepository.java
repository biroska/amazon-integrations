package br.com.biroska.amazonintegrations.integration.database.repository;

import br.com.biroska.amazonintegrations.integration.database.model.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<PersonEntity, String> {
}
