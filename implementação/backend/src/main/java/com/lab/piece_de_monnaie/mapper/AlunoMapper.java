package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.AlunoDTO;
import com.lab.piece_de_monnaie.dto.CreateAlunoDTO;
import com.lab.piece_de_monnaie.dto.UpdateAlunoDTO;
import com.lab.piece_de_monnaie.dto.auth.AlunoResponse;
import com.lab.piece_de_monnaie.entity.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlunoMapper {
    
    @Mapping(target = "cursoId", source = "curso.id")
    @Mapping(target = "cursoNome", source = "curso.nome")
    AlunoDTO toDTO(Aluno aluno);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantidadeMoeda", constant = "0L")
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "rg", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Aluno toEntity(CreateAlunoDTO createAlunoDTO);
    
    void updateEntity(UpdateAlunoDTO updateAlunoDTO, @MappingTarget Aluno aluno);

    AlunoResponse toAlunoResponse(Aluno aluno);
}
