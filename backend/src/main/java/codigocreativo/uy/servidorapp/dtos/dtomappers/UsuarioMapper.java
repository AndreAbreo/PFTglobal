package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDto usuarioDto, @Context CycleAvoidingMappingContext context);

    UsuarioDto toDto(Usuario usuario, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate(UsuarioDto usuarioDto, @MappingTarget Usuario usuario);

    List<Usuario> toEntity(List<UsuarioDto> usuarioDto, @Context CycleAvoidingMappingContext context);

    List<UsuarioDto> toDto(List<Usuario> usuario, @Context CycleAvoidingMappingContext context);
}