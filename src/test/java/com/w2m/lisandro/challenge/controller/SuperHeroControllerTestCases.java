package com.w2m.lisandro.challenge.controller;

import org.junit.jupiter.api.Test;

public interface SuperHeroControllerTestCases {
    @Test
    void testFindAll();

    @Test
    void testFindByName();

    @Test
    void testGetById_200_OK();

    @Test
    void testGetById_404_Not_Found();

    @Test
    void testPost_200_OK();

    @Test
    void testPut_200_OK();

    @Test
    void testPut_404_Not_Found();

    @Test
    void testDelete_204_No_Content();

    @Test
    void testDelete_404_Not_Found();
}
