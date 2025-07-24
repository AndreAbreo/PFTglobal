package codigocreativo.uy.servidorapp.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class ModelosEquipoTest {

    private ModelosEquipo modeloEquipo;

    @BeforeEach
    public void setUp() {
        modeloEquipo = new ModelosEquipo();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        modeloEquipo.setId(id);
        assertEquals(id, modeloEquipo.getId());
    }

    @Test
    void testSetAndGetIdMarca() {
        MarcasModelo marca = new MarcasModelo(); // Simulamos una marca modelo
        modeloEquipo.setIdMarca(marca);
        assertEquals(marca, modeloEquipo.getIdMarca());
    }

    @Test
    void testSetAndGetNombre() {
        String nombre = "Modelo X";
        modeloEquipo.setNombre(nombre);
        assertEquals(nombre, modeloEquipo.getNombre());
    }

    @Test
    void testSetAndGetEstado() {
        String estado = "Activo";
        modeloEquipo.setEstado(estado);
        assertEquals(estado, modeloEquipo.getEstado());
    }

    @Test
     void testConstructorAndSetters() {
        Long id = 1L;
        MarcasModelo marca = new MarcasModelo(); // Simulamos una marca modelo
        String nombre = "Modelo Y";
        String estado = "Inactivo";

        modeloEquipo.setId(id);
        modeloEquipo.setIdMarca(marca);
        modeloEquipo.setNombre(nombre);
        modeloEquipo.setEstado(estado);

        assertEquals(id, modeloEquipo.getId());
        assertEquals(marca, modeloEquipo.getIdMarca());
        assertEquals(nombre, modeloEquipo.getNombre());
        assertEquals(estado, modeloEquipo.getEstado());
    }

    @Test
     void testToString() {
        String nombre = "Modelo Z";
        modeloEquipo.setNombre(nombre);
        assertEquals(nombre, modeloEquipo.toString());
    }
}
