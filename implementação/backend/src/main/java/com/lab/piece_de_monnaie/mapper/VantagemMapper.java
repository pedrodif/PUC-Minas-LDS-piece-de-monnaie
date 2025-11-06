package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.dto.VantagemResponse;
import com.lab.piece_de_monnaie.entity.Vantagem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EmpresaParceiraMapper.class)
public interface VantagemMapper {
    VantagemResponse toVantagemResponse(Vantagem vantagem);
    Vantagem toVantagem(VantagemRequest vantagemRequest);
}
