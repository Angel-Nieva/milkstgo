package com.springboot.milkstgo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grasas_solidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrasaSolidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "proveedor", nullable = false)
    private String proveedor;

    @Column(name = "grasa", nullable = false)
    private Integer grasa;

    @Column(name = "solido", nullable = false)
    private Integer solido;
}
