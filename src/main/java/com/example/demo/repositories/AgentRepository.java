package com.example.demo.repositories;

import com.example.demo.models.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface AgentRepository extends CrudRepository<Agent, Long>{
}
