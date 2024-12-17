package com.challenge.votation.infra.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration
{
    @Bean
    public OpenAPI openAPI()
    {
        List<Server> servers = Stream.of( "http://localhost:9010" ).map( url -> new Server().url( url ) ).toList();
        
        return new OpenAPI()
                .servers( servers )
                .info( new io.swagger.v3.oas.models.info.Info()
                .title( "Desafio Votação" )
                .version( "1.0.0" )
                .description( "API para gerenciamento de pautas" )
                .contact( new Contact().name( "Nícolas Cristiel Endrizzi" )
                                       .email( "nicolasendrizzi@gmail.com" ) ) )
                .externalDocs( new ExternalDocumentation().url( "https://github.com/Nicola-12/desafio-votacao" ) );
    }
}
