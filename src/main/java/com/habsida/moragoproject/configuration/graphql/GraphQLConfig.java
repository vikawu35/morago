package com.habsida.moragoproject.configuration.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurerUpload() {
        GraphQLScalarType uploadScalar = GraphQLScalarType.newScalar()
                .name("Upload")
                .coercing(new UploadCoercing())
                .build();

        return wiringBuilder -> wiringBuilder
                .scalar(uploadScalar)
                .scalar(localDateTimeScalar());
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> graphQlMultipartRouterFunction(
            GraphQlProperties properties,
            WebGraphQlHandler webGraphQlHandler,
            ObjectMapper objectMapper
    ) {
        String path = properties.getPath();
        RouterFunctions.Builder builder = RouterFunctions.route();
        GraphqlMultipartHandler graphQLMultipartHandler = new GraphqlMultipartHandler(webGraphQlHandler, objectMapper);

        builder = builder.POST(
                path,
                RequestPredicates.contentType(MediaType.MULTIPART_FORM_DATA)
                        .and(RequestPredicates.accept(
                                Arrays.asList(MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON)
                                        .toArray(MediaType[]::new)
                        )),
                graphQLMultipartHandler::handleRequest
                );
        return builder.build();
    }

    public GraphQLScalarType localDateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("LocalDateTime")
                .description("Java LocalDataTime type")
                .coercing(new Coercing<LocalDateTime, String>() {

                    @Override
                    public String serialize(final Object dataFetcherResult)  {
                        if (dataFetcherResult instanceof LocalDateTime) {
                            return dataFetcherResult.toString();
                        } else {
                            throw new CoercingSerializeException("Expected a LocalDateTime object.");
                        }
                    }

                    @Override
                    public  LocalDateTime parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return LocalDateTime.parse((String) input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException ex) {
                            throw new CoercingParseValueException(String.format("Not a valid date: %s.", input), ex);
                        }
                    }

                    @Override
                    public LocalDateTime parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return LocalDateTime.parse(((StringValue) input).getValue());
                            } catch (DateTimeParseException ex) {
                                throw new CoercingParseLiteralException(ex);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();
    }
}
