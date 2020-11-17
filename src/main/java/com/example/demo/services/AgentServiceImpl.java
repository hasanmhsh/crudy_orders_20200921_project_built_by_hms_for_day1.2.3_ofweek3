package com.example.demo.services;

import com.example.demo.models.Agent;
import com.example.demo.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent getById(long id) throws EntityNotFoundException{
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found!"));
        return agent;
    }
}
