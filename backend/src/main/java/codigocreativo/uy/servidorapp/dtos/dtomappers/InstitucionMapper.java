package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface InstitucionMapper {
    Institucion toEntity(InstitucionDto institucionDto);

    InstitucionDto toDto(Institucion institucion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Institucion partialUpdate(InstitucionDto institucionDto, @MappingTarget Institucion institucion);

    List<Institucion> toEntity(List<InstitucionDto> institucionDto);

    List<InstitucionDto> toDto(List<Institucion> institucion);
}