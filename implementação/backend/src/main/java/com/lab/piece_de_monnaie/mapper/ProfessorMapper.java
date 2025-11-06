package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.auth.ProfessorResponse;
import com.lab.piece_de_monnaie.entity.Professor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorResponse toProfessorResponse(Professor professor);
}
