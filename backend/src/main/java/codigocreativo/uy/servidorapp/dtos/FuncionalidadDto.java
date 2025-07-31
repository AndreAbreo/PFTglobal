package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import java.io.Serializable;
import java.util.List;

public class FuncionalidadDto implements Serializable {
    private Long id;
    private String nombreFuncionalidad;
    private String ruta;
    private Estados estado;  // Usamos el enum directamente
    private List<PerfilDto> perfiles;

    public FuncionalidadDto(Long id, String nombreFuncionalidad, List<PerfilDto> perfiles, String ruta, Estados estado) {
        this.estado = estado;
        this.id = id;
        this.nombreFuncionalidad = nombreFuncionalidad;
        this.perfiles = perfiles;
        this.ruta = ruta;
    }

    public FuncionalidadDto() {
    }

    public Long getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFuncionalidad() {
        return nombreFuncionalidad;
    }

    public void setNombreFuncionalidad(String nombreFuncionalidad) {
        this.nombreFuncionalidad = nombreFuncionalidad;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public List<PerfilDto> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<PerfilDto> perfiles) {
        this.perfiles = perfiles;
    }
}
