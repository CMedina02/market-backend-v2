package com.merida.tecnm.market_backend_v2.persistence.entity;

import jakarta.persistence.*;
import java.util.*;

import java.time.LocalDateTime;

@Entity
@Table (name="compras")

public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id compra")
    private Integer idCompra;

    @Column (name = "id cliente")
    private String idcliente;


    private LocalDateTime fecha;

    @Column (name = "medio pago")
    private String medioPago;


    private String comentario;
    private String estado;

    //relacion con cliente
    //Mucho compras para un cliente

    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;


    //Relacion con CompraProducto
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<CompraProducto> productos;


    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CompraProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<CompraProducto> productos) {
        this.productos = productos;
    }
}
