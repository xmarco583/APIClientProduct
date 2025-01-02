package com.cortez.usproducto.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("producto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    private long id;

    @Column("tipoproducto")
    private String tipoProducto;

    @Column("nombre")
    private String nombre;

    @Column("saldo")
    private float saldo;

    @Column("codigocliente")
    private Long codigoCliente;
}
