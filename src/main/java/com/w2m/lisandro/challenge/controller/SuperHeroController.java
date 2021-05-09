package com.w2m.lisandro.challenge.controller;

import com.w2m.lisandro.challenge.annotation.LogExecutionTime;
import com.w2m.lisandro.challenge.model.SuperHero;
import com.w2m.lisandro.challenge.service.SuperHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superheroes")
public class SuperHeroController {
    @Autowired
    private SuperHeroService superHeroService;

    @GetMapping
    @LogExecutionTime
    public List<SuperHero> find(@RequestParam(required = false) String name){
        if(StringUtils.hasText(name)){
            return superHeroService.findByName(name);
        }
        return superHeroService.findAll();
    }

    @GetMapping("/{id}")
    public SuperHero findById(@PathVariable("id") Integer id){
        return superHeroService.findById(id);
    }

    @PostMapping
    public SuperHero create(@RequestBody SuperHero superHero){
        return superHeroService.save(superHero);
    }

    @PutMapping("/{id}")
    public SuperHero update(@PathVariable("id") Integer id, @RequestBody SuperHero superHero ){
        return superHeroService.update(id, superHero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        superHeroService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
