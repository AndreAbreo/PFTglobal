package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface TiposEquipoMapper {
    TiposEquipo toEntity(TiposEquipoDto tiposEquipoDto);

    TiposEquipoDto toDto(TiposEquipo tiposEquipo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TiposEquipo partialUpdate(TiposEquipoDto tiposEquipoDto, @MappingTarget TiposEquipo tiposEquipo);

    List<TiposEquipo> toEntity(List<TiposEquipoDto> tiposEquipoDto);

    List<TiposEquipoDto> toDto(List<TiposEquipo> tiposEquipo);
}