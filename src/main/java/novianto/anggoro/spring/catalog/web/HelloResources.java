package novianto.anggoro.spring.catalog.web;

import novianto.anggoro.spring.catalog.dto.HelloMessageResponseDTO;
import novianto.anggoro.spring.catalog.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResources {

    Logger log = LoggerFactory.getLogger(HelloResources.class);

    private GreetingService greetingService;

    public HelloResources(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public ResponseEntity<HelloMessageResponseDTO> helloWorld(){
        log.trace("this is log trace");
        log.debug("this is log debug");
        log.info("this is log info");
        log.warn("this is log warn");
        log.error("this is log error");
        HelloMessageResponseDTO dto = new HelloMessageResponseDTO();
        dto.setMessage(greetingService.sayGreeting());
       // return "Hello World from Kentod";
        return ResponseEntity.ok().body(dto);
    }
}
