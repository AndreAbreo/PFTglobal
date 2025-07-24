package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.entidades.Pais;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PaisMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pais partialUpdate(PaisDto paisDto, @MappingTarget Pais pais);

    List<Pais> toEntity(List<PaisDto> paisDto);

    List<PaisDto> toDto(List<Pais> pais);

    default Pais toEntity(PaisDto dto) {
        if (dto == null) return null;
        Pais entity = new Pais();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setEstado(dto.getEstado() != null ? dto.getEstado().name() : null);
        return entity;
    }

    default PaisDto toDto(Pais entity) {
        if (entity == null) return null;
        PaisDto dto = new PaisDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEstado(entity.getEstado() != null ? codigocreativo.uy.servidorapp.enumerados.Estados.valueOf(entity.getEstado()) : null);
        return dto;
    }
}