package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.UsuariosTelefonoDto;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {UsuarioMapper.class})
public interface UsuariosTelefonoMapper {
    UsuariosTelefono toEntity(UsuariosTelefonoDto usuariosTelefonoDto);

    UsuariosTelefonoDto toDto(UsuariosTelefono usuariosTelefono);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsuariosTelefono partialUpdate(UsuariosTelefonoDto usuariosTelefonoDto, @MappingTarget UsuariosTelefono usuariosTelefono);

    List<UsuariosTelefono> toEntity(List<UsuariosTelefonoDto> usuariosTelefonoDto);

    List<UsuariosTelefonoDto> toDto(List<UsuariosTelefono> usuariosTelefono);
}