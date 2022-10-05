package com.javacomwebflux.webclientrickandmortyapi.client;

import com.javacomwebflux.webclientrickandmortyapi.response.CharacterResponse;
import com.javacomwebflux.webclientrickandmortyapi.response.EpisodeResponse;
import com.javacomwebflux.webclientrickandmortyapi.response.ListOfEpisodesResponse;
import com.javacomwebflux.webclientrickandmortyapi.response.LocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.stream.Location;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class RickAndMortyClient {

    private final WebClient webClient;

    public RickAndMortyClient(WebClient.Builder builder){

        webClient = builder.baseUrl("https://rickandmortyapi.com/api").build();
    }

    public Mono<CharacterResponse> findAnCharacterById(String id){
        log.info("buscando o personagem com o id [{}]", id);
        return webClient
                .get()
                .uri("/character/"+ id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os paramentos informados")))
                .bodyToMono(CharacterResponse.class);
    }

    public Mono<LocationResponse> findAnLocationById(String id){
        log.info("buscando o localização com o id [{}]", id);
        return webClient
                .get()
                .uri("/location/"+ id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os paramentos informados")))
                .bodyToMono(LocationResponse.class);
    }

    public Mono<EpisodeResponse> findAnEpisodeById(String id){
        log.info("buscando o episodio com o id [{}]", id);
        return webClient
                .get()
                .uri("/episode/"+ id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os paramentos informados")))
                .bodyToMono(EpisodeResponse.class);
    }

    public Flux<ListOfEpisodesResponse> getAllEpisodes(){
        log.info("Listando todos os episódios");
        return webClient
                .get()
                .uri("/episode/")
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os paramentos informados")))
                .bodyToFlux(ListOfEpisodesResponse.class);
    }




}
