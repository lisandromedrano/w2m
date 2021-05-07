package com.w2m.lisandro.challenge.repository;

import com.w2m.lisandro.challenge.ChallengeApplication;
import com.w2m.lisandro.challenge.model.SuperHero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ChallengeApplication.class)
@Transactional
public class SuperHeroRepositoryTest {
    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Test
    @Sql("/db/scripts/insert_super_heroes.sql")
    public void testFindAll() {
        assertThat(superHeroRepository.findAll()).hasSize(11);
    }

    @Test
    @Sql("/db/scripts/insert_super_heroes.sql")
    public void testFindById() {
        SuperHero superHero = getSuperHero(3);
        assertThat(superHero.getName()).isEqualTo("Birdman");
    }

    private SuperHero getSuperHero(Integer id) {
        Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(id);

        assertThat(optionalSuperHero).isPresent();
        SuperHero superHero = optionalSuperHero.get();
        return superHero;
    }

    @Test
    public void testCreateSuperHero() {
        SuperHero newHero = new SuperHero();
        newHero.setName("Lisandro");

        SuperHero savedOne = superHeroRepository.save(newHero);

        assertThat(savedOne).isNotNull();
        assertThat(savedOne.getId()).isNotNull();
        assertThat(savedOne.getCreatedAt()).isNotNull();
    }

    @Test
    @Sql("/db/scripts/insert_super_heroes.sql")
    public void testDeleteSuperHero() {
        assertThat(superHeroRepository.findAll()).hasSize(11);

        superHeroRepository.deleteById(1);

        assertThat(superHeroRepository.findAll()).hasSize(10);

    }

    @Test
    @Sql("/db/scripts/insert_super_heroes.sql")
    public void testFindByNameContainingIgnoreCase(){
        List<SuperHero> result = superHeroRepository.findByNameContainingIgnoreCase("mAn");

        assertThat(result).hasSize(5);
    }

}
