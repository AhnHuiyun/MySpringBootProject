package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.controller.dto.PublisherDTO;
import com.rookies3.myspringbootlab.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherDTO.SimpleResponse>> getAll() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO.SimpleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PublisherDTO.SimpleResponse> getByName(@PathVariable String name) {
        return ResponseEntity.ok(publisherService.getPublisherByName(name));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PublisherDTO.Request request) {
        publisherService.createPublisher(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PublisherDTO.Request request) {
        publisherService.updatePublisher(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
