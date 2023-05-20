package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.GrasaSolidoEntity;
import com.springboot.milkstgo.repositories.GrasaSolidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GrasaSolidoServiceTest {

    @Autowired
    GrasaSolidoService grasaSolidoService;

    @Autowired
    GrasaSolidoRepository grasaSolidoRepository;

    private GrasaSolidoEntity grasaSolido1;

    @BeforeEach
    void setUp(){
        grasaSolido1 = new GrasaSolidoEntity();
        grasaSolido1.setProveedor("13001");
        grasaSolido1.setGrasa(19);
        grasaSolido1.setSolido(15);
    }
    @Test
    void obtenerGrasas_Solidos() {
        //Given
        grasaSolidoService.guardarGrasaSolido(grasaSolido1);
        //When
        List<GrasaSolidoEntity> grasaSolidoDB = grasaSolidoService.obtenerGrasaSolidos();
        //Then
        assertThat(grasaSolidoDB).isNotNull();
        assertThat(grasaSolidoDB.size()).isEqualTo(1);
        grasaSolidoRepository.delete(grasaSolido1);
    }

    @Test
    void guardarGrasa_Solido() {
        //Given
        grasaSolidoService.guardarGrasaSolido(grasaSolido1);
        //When
        Optional<GrasaSolidoEntity> nuevoGrasaSolido = grasaSolidoRepository.findById(grasaSolido1.getId());
        //Then
        assertThat(nuevoGrasaSolido).isNotNull();
        assertThat(nuevoGrasaSolido.get().getId()).isPositive();
        grasaSolidoRepository.delete(grasaSolido1);
    }

    @Test
    void guardarGrasa_SolidoDB() throws ParseException {
        //When
        grasaSolidoService.guardarGrasaSolidoDB(
                "13001",
                19,
                15);
        //Then
        Optional<GrasaSolidoEntity> nuevoGrasaSolido = grasaSolidoRepository.findById(3L);
        assertThat(nuevoGrasaSolido).isNotNull();
        assertThat(nuevoGrasaSolido.get().getId()).isPositive();
        System.out.print(nuevoGrasaSolido.get().getId());
        grasaSolidoRepository.delete(nuevoGrasaSolido.get());
    }

    @Test
    void buscarPorProveedor() {
        //Given
        grasaSolidoService.guardarGrasaSolido(grasaSolido1);
        //When
        GrasaSolidoEntity buscado = grasaSolidoService.buscarPorProveedor("13001");
        //Then
        assertThat(buscado).isNotNull();
        assertThat(buscado.getId()).isPositive();
        assertEquals("13001",buscado.getProveedor());
        grasaSolidoRepository.delete(buscado);
    }
}