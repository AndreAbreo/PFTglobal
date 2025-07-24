package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FuncionalidadDtoTest {

    @Test
    void testEquals_SameObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    static Stream<Arguments> provideDifferentFuncionalidadDtos() {
        List<PerfilDto> perfiles1 = new ArrayList<>();
        List<PerfilDto> perfiles2 = new ArrayList<>();
        PerfilDto perfil1 = new PerfilDto(1L, "Perfil1", Estados.ACTIVO);
        PerfilDto perfil2 = new PerfilDto(2L, "Perfil2", Estados.ACTIVO);
        PerfilDto perfil3 = new PerfilDto(3L, "Perfil3", Estados.ACTIVO);

        perfiles1.add(perfil1);
        perfiles1.add(perfil2);
        perfiles2.add(perfil1);
        perfiles2.add(perfil3);

        return Stream.of(

            Arguments.of(
                new FuncionalidadDto(1L, "Funcionalidad1",perfiles1 , "/ruta1", Estados.ACTIVO),
                new FuncionalidadDto(2L, "Funcionalidad1",perfiles1 , "/ruta1", Estados.ACTIVO)
            ),
            Arguments.of(
                new FuncionalidadDto(1L, "Funcionalidad1",perfiles1, "/ruta1", Estados.ACTIVO),
                new FuncionalidadDto(1L, "Funcionalidad2",perfiles2, "/ruta1", Estados.ACTIVO)
            ),
            Arguments.of(
                new FuncionalidadDto(1L, "Funcionalidad1",perfiles1, "/ruta1", Estados.ACTIVO),
                new FuncionalidadDto(1L, "Funcionalidad1",perfiles2, "/ruta2", Estados.ACTIVO)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDifferentFuncionalidadDtos")
    void testEquals_DifferentAttributes(FuncionalidadDto dto1, FuncionalidadDto dto2) {
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(1L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.INACTIVO);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(2L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.ACTIVO);

        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombreFuncionalidad = "Funcionalidad1";
        String ruta = "/ruta1";
        Estados estado = Estados.ACTIVO;
        List<PerfilDto> perfiles = new ArrayList<>();

        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(id);
        dto.setNombreFuncionalidad(nombreFuncionalidad);
        dto.setRuta(ruta);
        dto.setEstado(estado);
        dto.setPerfiles(perfiles);

        assertEquals(id, dto.getId());
        assertEquals(nombreFuncionalidad, dto.getNombreFuncionalidad());
        assertEquals(ruta, dto.getRuta());
        assertEquals(estado, dto.getEstado());
        assertEquals(perfiles, dto.getPerfiles());
    }
}