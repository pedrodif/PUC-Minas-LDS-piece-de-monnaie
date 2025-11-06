package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.UsuarioResponse;
import com.lab.piece_de_monnaie.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponse toUsuarioResponse(Usuario usuario);
}
