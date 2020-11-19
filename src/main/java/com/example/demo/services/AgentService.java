package com.example.demo.services;

import com.example.demo.models.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> getAll();
    Agent getById(long id);
    boolean deleteUnassigned(long id);
}
