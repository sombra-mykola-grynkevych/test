package com.test.reactive.services.impl;

import com.mongodb.client.result.UpdateResult;
import com.test.reactive.dto.MessageDTO;
import com.test.reactive.models.Person;
import com.test.reactive.repositories.PersonRepository;
import com.test.reactive.services.PersonService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private MongoOperations mongoOperations;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                             MongoOperations mongoOperations) {
        this.personRepository = personRepository;
        this.mongoOperations = mongoOperations;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Person> findById(String id) {
        if (Strings.isEmpty(id)) {
            throw new IllegalStateException("Id must not be empty");
        }

        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Person not found")));
    }

    @Override
    @Transactional
    public Flux<Person> insert(Flux<Person> persons) {
        return personRepository.insert(persons);
    }

    @Override
    @Transactional
    public Mono<MessageDTO> update(Mono<Person> person) {
        MessageDTO message = new MessageDTO("Person not found");
        person.subscribe(doUpdate(message));

        return Mono.just(message);
    }

    private Consumer<Person> doUpdate(MessageDTO message) {
        return p -> {
            if (Strings.isEmpty(p.getId())) {
                message.setMessage("Id must not be empty");
                return;
            }
            mongoOperations.save(p);
//            final Query query = new Query();
//            query.addCriteria(Criteria.where("_id").is(p.getId()));
//            query.fields().include("_id");
//
//            final Update update = new Update();
//            update.set("name", p.getName());
//            update.set("age", p.getAge());
//
//            final UpdateResult updateResult = mongoOperations.updateFirst(query, update, Person.class);
//            if (updateResult.getModifiedCount() > 0) {
                message.setMessage("Person has been updated");
//            } else {
//                message.setMessage("No persons updated/found");
//            }
        };
    }

    @Override
    @Transactional
    public Mono<MessageDTO> delete(String id) {
        if (Strings.isEmpty(id)) {
            throw new IllegalStateException("Id must not be empty");
        }

        personRepository.deleteById(id).subscribe();

        return Mono.just(new MessageDTO("Person has been removed"));
    }

}
