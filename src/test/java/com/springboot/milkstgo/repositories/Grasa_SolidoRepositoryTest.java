package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.Grasa_SolidoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Grasa_SolidoRepositoryTest {

    @Autowired
    Grasa_SolidoRepository grasa_solidoRepository;

    Grasa_SolidoEntity grasa_solido;

    @BeforeEach
    void setUp(){
        grasa_solido = new Grasa_SolidoEntity();
        grasa_solido.setSolido(15);
        grasa_solido.setGrasa(19);
        grasa_solido.setProveedor("13001");
    }

    @Test
    void findByProveedor() {
        //Given
        grasa_solidoRepository.save(grasa_solido);
        //When
        Grasa_SolidoEntity grasaSolidoDB = grasa_solidoRepository.findByProveedor(grasa_solido.getProveedor());
        //Then
        assertThat(grasaSolidoDB).isNotNull();
        grasa_solidoRepository.delete(grasa_solido);
    }
}