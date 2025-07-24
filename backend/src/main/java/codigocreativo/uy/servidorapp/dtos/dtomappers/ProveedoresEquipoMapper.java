package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ProveedoresEquipoMapper {
    ProveedoresEquipo toEntity(ProveedoresEquipoDto proveedoresEquipoDto);

    ProveedoresEquipoDto toDto(ProveedoresEquipo proveedoresEquipo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProveedoresEquipo partialUpdate(ProveedoresEquipoDto proveedoresEquipoDto, @MappingTarget ProveedoresEquipo proveedoresEquipo);

    List<ProveedoresEquipo> toEntity(List<ProveedoresEquipoDto> proveedoresEquipoDto);

    List<ProveedoresEquipoDto> toDto(List<ProveedoresEquipo> proveedoresEquipo);
}