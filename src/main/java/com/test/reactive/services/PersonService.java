package com.test.reactive.services;

import com.test.reactive.dto.MessageDTO;
import com.test.reactive.models.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    Flux<Person> findAll();

    Mono<Person> findById(String id);

    Flux<Person> insert(Flux<Person> persons);

    Mono<MessageDTO> update(Mono<Person> person);

    Mono<MessageDTO> delete(String id);

}
