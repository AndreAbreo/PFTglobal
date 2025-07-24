package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.dtos.BajaUbicacionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI, uses = {UsuarioMapper.class, UbicacionMapper.class})
public interface BajaUbicacionMapper {
    BajaUbicacion toEntity(BajaUbicacionDto bajaUbicacionDto, @Context CycleAvoidingMappingContext context);

    BajaUbicacionDto toDto(BajaUbicacion bajaUbicacion, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BajaUbicacion partialUpdate(BajaUbicacionDto bajaUbicacionDto, @MappingTarget BajaUbicacion bajaUbicacion, @Context CycleAvoidingMappingContext context);

    List<BajaUbicacion> toEntity(List<BajaUbicacionDto> bajaUbicacionDto, @Context CycleAvoidingMappingContext context);

    List<BajaUbicacionDto> toDto(List<BajaUbicacion> bajaUbicacion, @Context CycleAvoidingMappingContext context);
}