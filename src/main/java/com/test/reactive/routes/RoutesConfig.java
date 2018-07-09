package com.test.reactive.routes;

import com.test.reactive.dto.MessageDTO;
import com.test.reactive.models.Person;
import com.test.reactive.services.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<?> routes(PersonService personService) {
        return nest(path("/person"),

                    route(RequestPredicates.GET("/{id}"),
                          request ->
                                  ok().body(
                                          personService.findById(request.pathVariable("id")),
                                          Person.class
                                  )

                    ).andRoute(method(HttpMethod.GET),
                               request -> ok().body(personService.findAll(), Person.class)
                    ).andRoute(method(HttpMethod.POST),
                               request -> ok().body(personService.insert(request.bodyToFlux(Person.class)),
                                                    Person.class)
                    ).andRoute(method(HttpMethod.PUT),
                               request -> ok().body(personService.update(request.bodyToMono(Person.class)),
                                                    MessageDTO.class))
        );
    }

}
