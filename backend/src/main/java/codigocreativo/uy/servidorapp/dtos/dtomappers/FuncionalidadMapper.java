package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.FuncionalidadesPerfilesId;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface FuncionalidadMapper {

    @Mapping(target = "perfiles", source = "funcionalidadesPerfiles")
    FuncionalidadDto toDto(Funcionalidad funcionalidad, @Context CycleAvoidingMappingContext context);

    @AfterMapping
    default void mapPerfilesFromFuncionalidadesPerfiles(Funcionalidad funcionalidad, @MappingTarget FuncionalidadDto dto, @Context CycleAvoidingMappingContext context) {

        if (funcionalidad.getFuncionalidadesPerfiles() != null) {
            List<PerfilDto> perfiles = funcionalidad.getFuncionalidadesPerfiles().stream()
                    .map(funcPerfiles -> {
                        PerfilDto perfilDto = new PerfilDto();
                        perfilDto.setId(map(funcPerfiles.getId()));  // Mapeo manual del ID
                        perfilDto.setNombrePerfil(funcPerfiles.getPerfil().getNombrePerfil());
                        perfilDto.setEstado(funcPerfiles.getPerfil().getEstado());
                        return perfilDto;
                    }).toList();  // Use Stream.toList() to get an unmodifiable list
            dto.setPerfiles(perfiles);
        } else {
            dto.setPerfiles(new ArrayList<>());  // Inicializar como lista vacía
        }
    }


    Funcionalidad toEntity(FuncionalidadDto dto, @Context CycleAvoidingMappingContext context);

    List<Funcionalidad> toEntity(List<FuncionalidadDto> dtoList, @Context CycleAvoidingMappingContext context);

    List<FuncionalidadDto> toDto(List<Funcionalidad> entityList, @Context CycleAvoidingMappingContext context);

    default Long map(FuncionalidadesPerfilesId id) {
        return id.getIdPerfil();
    }
}
