package com.w2m.lisandro.challenge.repository;

import com.w2m.lisandro.challenge.model.SuperHero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends CrudRepository<SuperHero, Integer> {
    List<SuperHero> findByNameContainingIgnoreCase(String name);
}
