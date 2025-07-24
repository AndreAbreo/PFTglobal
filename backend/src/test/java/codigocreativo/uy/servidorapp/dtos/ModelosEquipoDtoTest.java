package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelosEquipoDtoTest {

    @Test
    void testEquals_SameObject() {
        ModelosEquipoDto dto = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        ModelosEquipoDto dto = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        ModelosEquipoDto dto = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(2L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(1L, "Modelo2", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.INACTIVO), Estados.INACTIVO);
        assertEquals(dto1, dto2); // Estado no se considera en equals
    }

    @Test
    void testEquals_DifferentMarca() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(2L, "Marca2", Estados.ACTIVO), Estados.ACTIVO);
        assertEquals(dto1, dto2); // Marca no se considera en equals
    }

    @Test
    void testHashCode_SameObject() {
        ModelosEquipoDto dto = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        ModelosEquipoDto dto1 = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        ModelosEquipoDto dto2 = new ModelosEquipoDto(2L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ModelosEquipoDto dto = new ModelosEquipoDto(1L, "Modelo1", new MarcasModeloDto(1L, "Marca1", Estados.ACTIVO), Estados.ACTIVO);
        String expected = "Modelo1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Modelo1";
        Estados estado = Estados.ACTIVO;
        MarcasModeloDto marca = new MarcasModeloDto();

        ModelosEquipoDto dto = new ModelosEquipoDto();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setEstado(estado);
        dto.setIdMarca(marca);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(estado, dto.getEstado());
        assertEquals(marca, dto.getIdMarca());
    }
}