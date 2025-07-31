package codigocreativo.uy.servidorapp.dtos.dtomappers;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PerfilMapper {

    Perfil toEntity(PerfilDto perfilDto);

    PerfilDto toDto(Perfil perfil);

    List<Perfil> toEntityList(List<PerfilDto> perfilDtos);

    List<PerfilDto> toDtoList(List<Perfil> perfiles);
}
