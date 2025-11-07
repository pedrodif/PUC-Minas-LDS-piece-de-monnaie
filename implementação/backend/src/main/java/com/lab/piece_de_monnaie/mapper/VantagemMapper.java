package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.dto.VantagemResponse;
import com.lab.piece_de_monnaie.entity.Vantagem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = EmpresaParceiraMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VantagemMapper {
    VantagemResponse toVantagemResponse(Vantagem vantagem);
    Vantagem toVantagem(VantagemRequest vantagemRequest);
    Vantagem updateVantagem(VantagemRequest vantagemRequest, @MappingTarget Vantagem vantagem);
}
