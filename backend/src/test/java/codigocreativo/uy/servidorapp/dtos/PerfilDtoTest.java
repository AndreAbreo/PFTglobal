package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfilDtoTest {

    @Test
    void testEquals_SameObject() {
        PerfilDto dto = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        PerfilDto dto = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        PerfilDto dto = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        PerfilDto dto1 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        PerfilDto dto2 = new PerfilDto(2L, "Perfil1", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombrePerfil() {
        PerfilDto dto1 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        PerfilDto dto2 = new PerfilDto(1L, "Perfil2", Estados.ACTIVO);
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        PerfilDto dto = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_EqualObjects() {
        PerfilDto dto1 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        PerfilDto dto2 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        PerfilDto dto1 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        PerfilDto dto2 = new PerfilDto(2L, "Perfil1", Estados.ACTIVO);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombrePerfil = "Perfil1";
        Estados estado = Estados.ACTIVO;

        PerfilDto dto = new PerfilDto();
        dto.setId(id);
        dto.setNombrePerfil(nombrePerfil);
        dto.setEstado(estado);

        assertEquals(id, dto.getId());
        assertEquals(nombrePerfil, dto.getNombrePerfil());
        assertEquals(estado, dto.getEstado());
    }
}