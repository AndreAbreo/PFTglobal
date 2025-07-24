package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface IntervencionMapper {
    Intervencion toEntity(IntervencionDto intervencionDto, @Context CycleAvoidingMappingContext context);

    IntervencionDto toDto(Intervencion intervencion, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Intervencion partialUpdate(IntervencionDto intervencionDto, @MappingTarget Intervencion intervencion, @Context CycleAvoidingMappingContext context);

    List<Intervencion> toEntity(List<IntervencionDto> intervencionDto, @Context CycleAvoidingMappingContext context);

    List<IntervencionDto> toDto(List<Intervencion> intervencion, @Context CycleAvoidingMappingContext context);
}