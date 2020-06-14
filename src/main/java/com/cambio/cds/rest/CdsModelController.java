package com.cambio.cds.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/models")
public class CdsModelController {

    @PostMapping("/content-model")
    public void uploadModel() {

    }

    @GetMapping("/content-model")
    public void downloadModel() {

    }

    @DeleteMapping("/content-model")
    public void deleteModel() {

    }
}