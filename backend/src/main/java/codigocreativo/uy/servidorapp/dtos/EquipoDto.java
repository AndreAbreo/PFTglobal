package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Equipo}
 */
public class EquipoDto implements Serializable {
    private Long id;
    private String idInterno;
    private String nroSerie;
    private String garantia;
    private TiposEquipoDto idTipo;
    private ProveedoresEquipoDto idProveedor;
    private PaisDto idPais;
    private ModelosEquipoDto idModelo;
    @JsonManagedReference
    private Set<EquiposUbicacioneDto> equiposUbicaciones = new LinkedHashSet<>();
    private UbicacionDto idUbicacion;
    private String nombre;
    private String imagen;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaAdquisicion;
    private Estados estado;

    // Public no-argument constructor
    public EquipoDto() {
    }

    private EquipoDto(Builder builder) {
        this.id = builder.id;
        this.idInterno = builder.idInterno;
        this.nroSerie = builder.nroSerie;
        this.garantia = builder.garantia;
        this.idTipo = builder.idTipo;
        this.idProveedor = builder.idProveedor;
        this.idPais = builder.idPais;
        this.idModelo = builder.idModelo;
        this.equiposUbicaciones = builder.equiposUbicaciones;
        this.idUbicacion = builder.idUbicacion;
        this.nombre = builder.nombre;
        this.imagen = builder.imagen;
        this.fechaAdquisicion = builder.fechaAdquisicion;
        this.estado = builder.estado;
    }

    public static class Builder {
        private Long id;
        private String idInterno;
        private String nroSerie;
        private String garantia;
        private TiposEquipoDto idTipo;
        private ProveedoresEquipoDto idProveedor;
        private PaisDto idPais;
        private ModelosEquipoDto idModelo;
        private Set<EquiposUbicacioneDto> equiposUbicaciones = new LinkedHashSet<>();
        private UbicacionDto idUbicacion;
        private String nombre;
        private String imagen;
        private LocalDate fechaAdquisicion;
        private Estados estado;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder idInterno(String idInterno) {
            this.idInterno = idInterno;
            return this;
        }

        public Builder nroSerie(String nroSerie) {
            this.nroSerie = nroSerie;
            return this;
        }

        public Builder garantia(String garantia) {
            this.garantia = garantia;
            return this;
        }

        public Builder idTipo(TiposEquipoDto idTipo) {
            this.idTipo = idTipo;
            return this;
        }

        public Builder idProveedor(ProveedoresEquipoDto idProveedor) {
            this.idProveedor = idProveedor;
            return this;
        }

        public Builder idPais(PaisDto idPais) {
            this.idPais = idPais;
            return this;
        }

        public Builder idModelo(ModelosEquipoDto idModelo) {
            this.idModelo = idModelo;
            return this;
        }

        public Builder equiposUbicaciones(Set<EquiposUbicacioneDto> equiposUbicaciones) {
            this.equiposUbicaciones = equiposUbicaciones;
            return this;
        }

        public Builder idUbicacion(UbicacionDto idUbicacion) {
            this.idUbicacion = idUbicacion;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder imagen(String imagen) {
            this.imagen = imagen;
            return this;
        }

        public Builder fechaAdquisicion(LocalDate fechaAdquisicion) {
            this.fechaAdquisicion = fechaAdquisicion;
            return this;
        }

        public Builder estado(Estados estado) {
            this.estado = estado;
            return this;
        }

        public EquipoDto build() {
            return new EquipoDto(this);
        }
    }

    public Long getId() {
        return id;
    }

    public EquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public EquipoDto setIdInterno(String idInterno) {
        this.idInterno = idInterno;
        return this;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public EquipoDto setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public EquipoDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getImagen() {
        return imagen;
    }

    public EquipoDto setImagen(String imagen) {
        this.imagen = imagen;
        return this;
    }
    @JsonbTransient
    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public EquipoDto setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public EquipoDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipoDto entity = (EquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idInterno, entity.idInterno) &&
                Objects.equals(this.nroSerie, entity.nroSerie) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.imagen, entity.imagen) &&
                Objects.equals(this.fechaAdquisicion, entity.fechaAdquisicion) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idInterno, nroSerie, nombre, imagen, fechaAdquisicion, estado);
    }

    @Override
    public String toString() {
        return idInterno + " - " + nombre;
    }

    public TiposEquipoDto getIdTipo() {
        return idTipo;
    }

    public EquipoDto setIdTipo(TiposEquipoDto idTipo) {
        this.idTipo = idTipo;
        return this;
    }

    public ProveedoresEquipoDto getIdProveedor() {
        return idProveedor;
    }

    public EquipoDto setIdProveedor(ProveedoresEquipoDto idProveedor) {
        this.idProveedor = idProveedor;
        return this;
    }

    public PaisDto getIdPais() {
        return idPais;
    }

    public EquipoDto setIdPais(PaisDto idPais) {
        this.idPais = idPais;
        return this;
    }

    public ModelosEquipoDto getIdModelo() {
        return idModelo;
    }

    public EquipoDto setIdModelo(ModelosEquipoDto idModelo) {
        this.idModelo = idModelo;
        return this;
    }
    public Set<EquiposUbicacioneDto> getEquiposUbicaciones() {
        return equiposUbicaciones;
    }

    public EquipoDto setEquiposUbicaciones(Set<EquiposUbicacioneDto> equiposUbicaciones) {
        this.equiposUbicaciones = equiposUbicaciones;
        return this;
    }

    public UbicacionDto getIdUbicacion() {
        return idUbicacion;
    }

    public EquipoDto setIdUbicacion(UbicacionDto idUbicacion) {
        this.idUbicacion = idUbicacion;
        return this;
    }

    public String getGarantia() {
        return garantia;
    }

    public EquipoDto setGarantia(String garantia) {
        this.garantia = garantia;
        return this;
    }
}