package com.avast.kotlinbasics

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.toMono

@RestController
class HelloController {
    @GetMapping(path = ["/numbers"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getNumbers() = Flux.range(1, 10).filter { it % 2 == 0 }
}

@Configuration
class Routing {
    @Bean
    fun routerHello() = router {
        GET("/hello") { ServerResponse.ok().body(mapOf("message" to "Hello world").toMono()) }
    }


}




