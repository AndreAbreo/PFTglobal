package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface EquipoMapper {
    Equipo toEntity(EquipoDto equipoDto, @Context CycleAvoidingMappingContext context);

    EquipoDto toDto(Equipo equipo, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Equipo partialUpdate(EquipoDto equipoDto, @MappingTarget Equipo equipo, @Context CycleAvoidingMappingContext context);

    List<Equipo> toEntity(List<EquipoDto> equipoDto, @Context CycleAvoidingMappingContext context);

    List<EquipoDto> toDto(List<Equipo> equipo, @Context CycleAvoidingMappingContext context);
}