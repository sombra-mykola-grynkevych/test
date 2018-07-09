//package com.test.reactive.endpoints;
//
//import com.test.reactive.models.Person;
//import com.test.reactive.services.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
//import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
//import org.springframework.boot.actuate.endpoint.annotation.Selector;
//import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@Endpoint(id = "person-endpoint")
//public class PersonEndpoint {
//
//    private final PersonService personService;
//
//    @Autowired
//    public PersonEndpoint(PersonService personService) {
//        this.personService = personService;
//    }
//
//    @ReadOperation
//    public List<Person> getPersons() {
//        return personService.findAll().toStream().collect(Collectors.toList());
//    }
//
//    @ReadOperation
//    public Mono<Person> getById(@Selector String id) {
//        return personService.findById(id);
//    }
//
//}
