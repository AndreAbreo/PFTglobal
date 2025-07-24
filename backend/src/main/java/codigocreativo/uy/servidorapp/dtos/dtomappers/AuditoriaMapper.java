package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.AuditoriaDto;
import codigocreativo.uy.servidorapp.entidades.Auditoria;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface AuditoriaMapper {
    Auditoria toEntity(AuditoriaDto auditoriaDto);

    AuditoriaDto toDto(Auditoria auditoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auditoria partialUpdate(AuditoriaDto auditoriaDto, @MappingTarget Auditoria auditoria);

    List<Auditoria> toEntity(List<AuditoriaDto> auditoriaDto);

    List<AuditoriaDto> toDto(List<Auditoria> auditoria);
}