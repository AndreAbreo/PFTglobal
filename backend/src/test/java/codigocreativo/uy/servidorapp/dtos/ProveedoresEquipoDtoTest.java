package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Pais;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProveedoresEquipoDtoTest {

    @Test
    void testEquals_SameObject() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        ProveedoresEquipoDto dto1 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        ProveedoresEquipoDto dto2 = new ProveedoresEquipoDto(2L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombre() {
        ProveedoresEquipoDto dto1 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        ProveedoresEquipoDto dto2 = new ProveedoresEquipoDto(1L, "Proveedor2", Estados.ACTIVO, new Pais());
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        ProveedoresEquipoDto dto1 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        ProveedoresEquipoDto dto2 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.INACTIVO, new Pais());
        assertEquals(dto1, dto2); // Estado no se considera en equals
    }

    @Test
    void testEquals_DifferentPais() {
        ProveedoresEquipoDto dto1 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais(1L, "Pais1"));
        ProveedoresEquipoDto dto2 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais(2L, "Pais2"));
        assertEquals(dto1, dto2); // Pais no se considera en equals
    }

    @Test
    void testHashCode_SameObject() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        ProveedoresEquipoDto dto1 = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        ProveedoresEquipoDto dto2 = new ProveedoresEquipoDto(2L, "Proveedor1", Estados.ACTIVO, new Pais());
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto(1L, "Proveedor1", Estados.ACTIVO, new Pais());
        String expected = "Proveedor1";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombre = "Proveedor1";
        Estados estado = Estados.ACTIVO;
        Pais pais = new Pais();

        ProveedoresEquipoDto dto = new ProveedoresEquipoDto();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setEstado(estado);
        dto.setPais(pais);

        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(estado, dto.getEstado());
        assertEquals(pais, dto.getPais());
    }
}