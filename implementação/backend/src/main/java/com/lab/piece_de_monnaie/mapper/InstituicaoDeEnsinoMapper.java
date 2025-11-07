package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.InstituicaoDeEnsinoResponse;
import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituicaoDeEnsinoMapper {
    InstituicaoDeEnsinoResponse toInstituicaoDeEnsinoResponse(InstituicaoDeEnsino instituicaoDeEnsino);
}
