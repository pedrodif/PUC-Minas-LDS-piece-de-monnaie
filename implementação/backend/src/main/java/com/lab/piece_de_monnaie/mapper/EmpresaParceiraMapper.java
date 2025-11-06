package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.EmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.CreateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.UpdateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.auth.EmpresaParceiraResponse;
import com.lab.piece_de_monnaie.entity.EmpresaParceira;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmpresaParceiraMapper {
    
    EmpresaParceiraDTO toDTO(EmpresaParceira empresaParceira);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cnpj", ignore = true)
    @Mapping(target = "email", ignore = true)
    EmpresaParceira toEntity(CreateEmpresaParceiraDTO createEmpresaParceiraDTO);
    
    void updateEntity(UpdateEmpresaParceiraDTO updateEmpresaParceiraDTO, @MappingTarget EmpresaParceira empresaParceira);

    EmpresaParceiraResponse toEmpresaParceiraResponse(EmpresaParceira empresaParceira);
}
