package com.springboot.milkstgo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "acopios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcopioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date fecha;

    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "proveedor", nullable = false)
    private String proveedor;

    @Column(name = "kls_leche", nullable = false)
    private String kls_leche;
}
