package com.w2m.lisandro.challenge.service;

import com.w2m.lisandro.challenge.model.SuperHero;
import com.w2m.lisandro.challenge.repository.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperHeroService {
    @Autowired
    private SuperHeroRepository superHeroRepository;

    public List<SuperHero> findAll(){
        return superHeroRepository.findAll();
    }

    public SuperHero findById(Integer id){
        //TODO: test returning empty
        return superHeroRepository.findById(id).get();
    }

    public SuperHero save(String name){
        SuperHero newSuperHero = new SuperHero();
        newSuperHero.setName(name);

        return superHeroRepository.save(newSuperHero);
    }

    public SuperHero save(SuperHero newSuperHero){
        return superHeroRepository.save(newSuperHero);
    }

    public SuperHero update(Integer id, SuperHero superHero){
        SuperHero update = superHeroRepository.findById(id).get();
        update.setName(superHero.getName());

        return superHeroRepository.save(update);
    }

    public void delete(Integer id){
        superHeroRepository.deleteById(id);
    }

    public List<SuperHero> findByName(String name){
        return superHeroRepository.findByNameContainingIgnoreCase(name);
    }
}
