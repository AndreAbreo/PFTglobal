package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.EquiposUbicacioneDto;
import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface EquiposUbicacioneMapper {
    EquiposUbicacione toEntity(EquiposUbicacioneDto equiposUbicacioneDto, @Context CycleAvoidingMappingContext context);

    EquiposUbicacioneDto toDto(EquiposUbicacione equiposUbicacione, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EquiposUbicacione partialUpdate(EquiposUbicacioneDto equiposUbicacioneDto, @MappingTarget EquiposUbicacione equiposUbicacione);

    List<EquiposUbicacione> toEntity(List<EquiposUbicacioneDto> equiposUbicacioneDto, @Context CycleAvoidingMappingContext context);

    List<EquiposUbicacioneDto> toDto(List<EquiposUbicacione> equiposUbicacione, @Context CycleAvoidingMappingContext context);
}