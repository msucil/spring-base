package com.msucil.dev.springbase.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/index")
public class IndexController {

    @GetMapping("")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Hello Spring Base");
    }
}
