package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.OperacionDto;
import codigocreativo.uy.servidorapp.entidades.Operacion;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface OperacionMapper {
    Operacion toEntity(OperacionDto operacionDto);

    OperacionDto toDto(Operacion operacion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Operacion partialUpdate(OperacionDto operacionDto, @MappingTarget Operacion operacion);

    List<Operacion> toEntity(List<OperacionDto> operacionDto);

    List<OperacionDto> toDto(List<Operacion> operacion);
}