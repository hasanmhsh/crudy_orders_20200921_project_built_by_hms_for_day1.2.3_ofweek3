package com.hasan.m.shehata.crudyrestaurants.controllers;

import com.hasan.m.shehata.crudyrestaurants.models.Agent;
import com.hasan.m.shehata.crudyrestaurants.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;
    //    GET /agents/agent/{id} - Returns the agent and their customers with the given agent id
    @GetMapping(value = "/agent/{id}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long id){
        Agent agent = agentService.findById(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }


//    DELETE /agents/unassigned/{agentcode} - Deletes an agent if they are not assigned to a customer
    @DeleteMapping(value = "/unassigned/{id}")
    public ResponseEntity<?> deleteAgentByIdIfHasNoCustomers(@PathVariable long id){
        agentService.deleteUnassignedAgent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
