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
    public List<Agent> getAll() {
        List<Agent> agents = new ArrayList<>();
        agentRepository.findAll()
                .iterator()
                .forEachRemaining(agents::add);
        return agents;
    }

    @Override
    public Agent getById(long id) throws EntityNotFoundException {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found!"));
        return agent;
    }

    @Override
    public boolean deleteUnassigned(long id) throws EntityNotFoundException {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found!"));
        if (agent.getCustomers() == null || agent.getCustomers()
                .size() == 0) {
            agentRepository.delete(agent);
            return true;
        }
        return false;
    }
}
