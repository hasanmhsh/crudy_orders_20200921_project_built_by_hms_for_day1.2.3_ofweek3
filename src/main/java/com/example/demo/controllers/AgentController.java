package com.example.demo.controllers;

import com.example.demo.models.Agent;
import com.example.demo.repositories.AgentRepository;
import com.example.demo.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @GetMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> getAgentById(@PathVariable long id){
        Agent agent = agentService.getById(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
