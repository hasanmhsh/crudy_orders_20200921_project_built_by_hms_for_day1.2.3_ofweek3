package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Agent;

public interface AgentService {
    public Agent findById(long id);
    public void deleteUnassignedAgents();
}
