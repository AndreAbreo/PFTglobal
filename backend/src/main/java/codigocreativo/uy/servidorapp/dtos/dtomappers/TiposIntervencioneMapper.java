package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface TiposIntervencioneMapper {
    TiposIntervencione toEntity(TiposIntervencioneDto tiposIntervencioneDto);

    TiposIntervencioneDto toDto(TiposIntervencione tiposIntervencione);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TiposIntervencione partialUpdate(TiposIntervencioneDto tiposIntervencioneDto, @MappingTarget TiposIntervencione tiposIntervencione);

    List<TiposIntervencione> toEntity(List<TiposIntervencioneDto> tiposIntervencioneDto);

    List<TiposIntervencioneDto> toDto(List<TiposIntervencione> tiposIntervencione);
}