package com.w2m.lisandro.challenge.service;

import com.w2m.lisandro.challenge.model.SuperHero;
import com.w2m.lisandro.challenge.repository.SuperHeroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SuperHeroServiceTest {

    @Autowired
    private SuperHeroService superHeroService;
    @MockBean
    private SuperHeroRepository superHeroRepository;

    @Test
    public void testFindAll() {
        when(superHeroRepository.findAll()).thenReturn(Arrays.asList(new SuperHero()));
        assertThat(superHeroService.findAll()).hasSize(1);
    }

    @Test
    public void findById() {
        when(superHeroRepository.findById(anyInt())).thenReturn(Optional.of(new SuperHero()));
        assertThat(superHeroService.findById(1)).isNotNull();
    }

    @Test
    public void findByIdNotFound() {
        when(superHeroRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            superHeroService.findById(1);
        });
    }

    @Test
    public void testSave() {
        SuperHero superHero = new SuperHero();
        superHero.setId(1);
        superHero.setName("test");
        when(superHeroRepository.save(any())).thenReturn(superHero);
        ArgumentCaptor<SuperHero> captor = ArgumentCaptor.forClass(SuperHero.class);

        SuperHero saved = superHeroService.save("test");

        verify(superHeroRepository).save(captor.capture());
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(captor.getValue().getName()).isEqualTo("test");
    }

    @Test
    public void deleteIdNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(superHeroRepository).deleteById(anyInt());
        assertThrows(EmptyResultDataAccessException.class, () -> {
            superHeroService.delete(1);
        });

    }

    @Test
    public void testUpdate() {
        SuperHero superHero = new SuperHero();
        superHero.setId(1);
        superHero.setName("test");
        when(superHeroRepository.save(any())).thenReturn(superHero);
        when(superHeroRepository.findById(any())).thenReturn(Optional.of(superHero));
        ArgumentCaptor<SuperHero> captor = ArgumentCaptor.forClass(SuperHero.class);

        SuperHero saved = superHeroService.update(1, superHero);

        verify(superHeroRepository).save(captor.capture());
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(captor.getValue().getName()).isEqualTo("test");
        assertThat(captor.getValue().getId()).isEqualTo(1);
    }

    @Test
    public void testUpdateNotFound() {
        assertThrows(NoSuchElementException.class, () -> {
            SuperHero superHero = new SuperHero();
            superHero.setId(1);
            superHero.setName("test");
            superHeroService.update(1, superHero);
        });
    }

    @Test
    public void testFindByName() {
        when(superHeroRepository.findByNameContainingIgnoreCase(eq("man"))).thenReturn(Arrays.asList(new SuperHero()));

        List<SuperHero> results = superHeroService.findByName("man");

        assertThat(results).hasSize(1);
    }
}
