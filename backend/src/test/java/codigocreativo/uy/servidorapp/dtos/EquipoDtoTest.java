package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

class EquipoDtoTest {

    @Test
    void testBuilder() {
        Long id = 1L;
        String idInterno = "ID123";
        String nroSerie = "SN123456";
        String garantia = "2 years";
        TiposEquipoDto idTipo = new TiposEquipoDto();
        ProveedoresEquipoDto idProveedor = new ProveedoresEquipoDto();
        PaisDto idPais = new PaisDto();
        ModelosEquipoDto idModelo = new ModelosEquipoDto();
        LinkedHashSet<EquiposUbicacioneDto> equiposUbicaciones = new LinkedHashSet<>();
        UbicacionDto idUbicacion = new UbicacionDto();
        String nombre = "Equipo1";
        String imagen = "imagen.png";
        LocalDate fechaAdquisicion = LocalDate.now();
        Estados estado = Estados.ACTIVO;

        EquipoDto dto = new EquipoDto.Builder()
                .id(id)
                .idInterno(idInterno)
                .nroSerie(nroSerie)
                .garantia(garantia)
                .idTipo(idTipo)
                .idProveedor(idProveedor)
                .idPais(idPais)
                .idModelo(idModelo)
                .equiposUbicaciones(equiposUbicaciones)
                .idUbicacion(idUbicacion)
                .nombre(nombre)
                .imagen(imagen)
                .fechaAdquisicion(fechaAdquisicion)
                .estado(estado)
                .build();

        assertEquals(id, dto.getId());
        assertEquals(idInterno, dto.getIdInterno());
        assertEquals(nroSerie, dto.getNroSerie());
        assertEquals(garantia, dto.getGarantia());
        assertEquals(idTipo, dto.getIdTipo());
        assertEquals(idProveedor, dto.getIdProveedor());
        assertEquals(idPais, dto.getIdPais());
        assertEquals(idModelo, dto.getIdModelo());
        assertEquals(equiposUbicaciones, dto.getEquiposUbicaciones());
        assertEquals(idUbicacion, dto.getIdUbicacion());
        assertEquals(nombre, dto.getNombre());
        assertEquals(imagen, dto.getImagen());
        assertEquals(fechaAdquisicion, dto.getFechaAdquisicion());
        assertEquals(estado, dto.getEstado());
    }

    @Test
    void testSetters() {
        EquipoDto equipoDto = new EquipoDto();
        equipoDto.setIdInterno("idInterno")
                .setNroSerie("nroSerie")
                .setNombre("nombre")
                .setImagen("imagen")
                .setFechaAdquisicion(LocalDate.now())
                .setEstado(Estados.ACTIVO)
                .setIdTipo(new TiposEquipoDto())
                .setIdProveedor(new ProveedoresEquipoDto())
                .setIdPais(new PaisDto())
                .setIdModelo(new ModelosEquipoDto())
                .setEquiposUbicaciones(new LinkedHashSet<>())
                .setIdUbicacion(new UbicacionDto())
                .setGarantia("garantia");
        EquipoDto equipoDto2 = new EquipoDto();
        equipoDto2.setIdInterno("idInterno")
                .setNroSerie("nroSerie")
                .setNombre("nombre")
                .setImagen("imagen")
                .setFechaAdquisicion(LocalDate.now())
                .setEstado(Estados.ACTIVO);

        assertEquals("idInterno", equipoDto2.getIdInterno());
        assertEquals("nroSerie", equipoDto2.getNroSerie());
        assertEquals("nombre", equipoDto2.getNombre());
        assertEquals("imagen", equipoDto2.getImagen());
        assertEquals(LocalDate.now(), equipoDto2.getFechaAdquisicion());
        assertEquals(Estados.ACTIVO, equipoDto2.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        EquipoDto equipoDto1 = new EquipoDto.Builder()
                .id(1L)
                .idInterno("idInterno")
                .nroSerie("nroSerie")
                .nombre("nombre")
                .imagen("imagen")
                .fechaAdquisicion(LocalDate.now())
                .estado(Estados.ACTIVO)
                .build();

        EquipoDto equipoDto2 = new EquipoDto.Builder()
                .id(1L)
                .idInterno("idInterno")
                .nroSerie("nroSerie")
                .nombre("nombre")
                .imagen("imagen")
                .fechaAdquisicion(LocalDate.now())
                .estado(Estados.ACTIVO)
                .build();

        EquipoDto equipoDto3 = new EquipoDto.Builder()
                .id(2L)
                .idInterno("diferenteIdInterno")
                .nroSerie("diferenteNroSerie")
                .nombre("otroNombre")
                .imagen("otraImagen")
                .fechaAdquisicion(LocalDate.now().minusDays(1))
                .estado(Estados.INACTIVO)
                .build();

        assertEquals(equipoDto1, equipoDto2);
        assertEquals(equipoDto1, equipoDto1);
        assertEquals(equipoDto1.hashCode(), equipoDto2.hashCode());
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testToString() {
        EquipoDto equipoDto = new EquipoDto.Builder()
                .idInterno("idInterno")
                .nombre("nombre")
                .build();

        assertEquals("idInterno - nombre", equipoDto.toString());
    }

    @Test
    void testEqualsForIdInterno() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().idInterno("idInterno").build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().idInterno("idInterno").build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().idInterno("differentIdInterno").build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testEqualsForNroSerie() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().nroSerie("nroSerie").build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().nroSerie("nroSerie").build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().nroSerie("differentNroSerie").build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testEqualsForNombre() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().nombre("nombre").build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().nombre("nombre").build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().nombre("differentNombre").build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testEqualsForImagen() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().imagen("imagen").build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().imagen("imagen").build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().imagen("differentImagen").build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testEqualsForFechaAdquisicion() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().fechaAdquisicion(LocalDate.now()).build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().fechaAdquisicion(LocalDate.now()).build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().fechaAdquisicion(LocalDate.now().minusDays(1)).build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testEqualsForEstado() {
        EquipoDto equipoDto1 = new EquipoDto.Builder().estado(Estados.ACTIVO).build();
        EquipoDto equipoDto2 = new EquipoDto.Builder().estado(Estados.ACTIVO).build();
        EquipoDto equipoDto3 = new EquipoDto.Builder().estado(Estados.INACTIVO).build();

        assertEquals(equipoDto1, equipoDto2);
        assertNotEquals(equipoDto1, equipoDto3);
    }
}
