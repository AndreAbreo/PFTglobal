package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ModelosEquipoMapper {
    ModelosEquipo toEntity(ModelosEquipoDto modelosEquipoDto);

    ModelosEquipoDto toDto(ModelosEquipo modelosEquipo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ModelosEquipo partialUpdate(ModelosEquipoDto modelosEquipoDto, @MappingTarget ModelosEquipo modelosEquipo);

    List<ModelosEquipo> toEntity(List<ModelosEquipoDto> modelosEquipoDto);

    List<ModelosEquipoDto> toDto(List<ModelosEquipo> modelosEquipo);
}