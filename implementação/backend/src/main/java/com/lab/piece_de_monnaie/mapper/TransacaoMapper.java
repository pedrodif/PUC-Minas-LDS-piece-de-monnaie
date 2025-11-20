package com.lab.piece_de_monnaie.mapper;

import com.lab.piece_de_monnaie.dto.transasao.TransacaoEnvioRequest;
import com.lab.piece_de_monnaie.dto.transasao.TransacaoEnvioResponse;
import com.lab.piece_de_monnaie.entity.Transacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    TransacaoEnvioResponse toTransacaoResponse(Transacao transacao);
}
