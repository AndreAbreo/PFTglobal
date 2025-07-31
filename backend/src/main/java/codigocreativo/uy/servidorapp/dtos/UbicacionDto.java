package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;


public class UbicacionDto implements Serializable {
    private Long id;
    private String nombre;
    private String sector;
    private Long piso;
    private Long numero;
    private Long cama;
    private InstitucionDto idInstitucion;
    private Estados estado;

    public UbicacionDto() {
    }

    public UbicacionDto(Long id, String nombre, String sector, Long piso, Long numero, Long cama, Estados estado) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.piso = piso;
        this.numero = numero;
        this.cama = cama;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public UbicacionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public UbicacionDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getSector() {
        return sector;
    }

    public UbicacionDto setSector(String sector) {
        this.sector = sector;
        return this;
    }

    public Long getPiso() {
        return piso;
    }

    public UbicacionDto setPiso(Long piso) {
        this.piso = piso;
        return this;
    }

    public Long getNumero() {
        return numero;
    }

    public UbicacionDto setNumero(Long numero) {
        this.numero = numero;
        return this;
    }

    public Long getCama() {
        return cama;
    }

    public UbicacionDto setCama(Long cama) {
        this.cama = cama;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public Estados setEstado(Estados estado) {
        this.estado = estado;
        return this.estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UbicacionDto entity = (UbicacionDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.sector, entity.sector) &&
                Objects.equals(this.piso, entity.piso) &&
                Objects.equals(this.numero, entity.numero) &&
                Objects.equals(this.cama, entity.cama) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, sector, piso, numero, cama, estado);
    }

    @Override
    public String toString() {
        return nombre + " - " +sector;
    }

    public InstitucionDto getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(InstitucionDto idInstitucion) {
        this.idInstitucion = idInstitucion;
    }
}