package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Agent;
import com.hasan.m.shehata.crudyrestaurants.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent findById(long id) throws EntityNotFoundException {
        return agentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Agent " + id + " not found!"));
    }

    @Transactional
    @Override
    public void deleteUnassignedAgent(long id) {
        Agent unassignedAgent = agentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Agent " + id + " not found!"));
        if(unassignedAgent.getCustomers() != null && unassignedAgent.getCustomers().size()>0){
            throw new EntityExistsException("Found A Customer For Agent " + id);
        }
        else {
            agentRepository.deleteById(id);
        }
    }
}
