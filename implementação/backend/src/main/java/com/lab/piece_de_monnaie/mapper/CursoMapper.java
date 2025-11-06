package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.CursoResponse;
import com.lab.piece_de_monnaie.entity.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = InstituicaoDeEnsinoMapper.class)
public interface CursoMapper {
    CursoResponse toCursoResponse(Curso curso);
}
