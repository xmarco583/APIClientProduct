package com.cortez.usclient.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("clientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {

    @Id
    private long id;
    @Column("codigounico")
    private Long codigoUnico;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("tipodoc")
    private String tipoDoc;

    @Column("numerodoc")
    private String numeroDoc;
    
}
