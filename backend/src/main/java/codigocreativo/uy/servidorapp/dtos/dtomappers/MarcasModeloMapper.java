package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MarcasModeloMapper {
    MarcasModelo toEntity(MarcasModeloDto marcasModeloDto);

    MarcasModeloDto toDto(MarcasModelo marcasModelo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MarcasModelo partialUpdate(MarcasModeloDto marcasModeloDto, @MappingTarget MarcasModelo marcasModelo);

    List<MarcasModelo> toEntity(List<MarcasModeloDto> marcasModeloDto);

    List<MarcasModeloDto> toDto(List<MarcasModelo> marcasModelo);
}