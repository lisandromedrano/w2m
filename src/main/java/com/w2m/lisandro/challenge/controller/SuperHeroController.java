package com.w2m.lisandro.challenge.controller;

import com.w2m.lisandro.challenge.annotation.LogExecutionTime;
import com.w2m.lisandro.challenge.model.SuperHero;
import com.w2m.lisandro.challenge.service.SuperHeroService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Find super heroes")
    public List<SuperHero> find(@RequestParam(required = false) String name){
        if(StringUtils.hasText(name)){
            return superHeroService.findByName(name);
        }
        return superHeroService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds a super hero by it id")
    public SuperHero findById(@PathVariable("id") Integer id){
        return superHeroService.findById(id);
    }

    @PostMapping
    @ApiOperation(value = "Creates a super hero")
    public SuperHero create(@RequestBody SuperHero superHero){
        return superHeroService.save(superHero);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Modifies a super hero by it id")
    public SuperHero update(@PathVariable("id") Integer id, @RequestBody SuperHero superHero ){
        return superHeroService.update(id, superHero);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a super hero by it id")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        superHeroService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
