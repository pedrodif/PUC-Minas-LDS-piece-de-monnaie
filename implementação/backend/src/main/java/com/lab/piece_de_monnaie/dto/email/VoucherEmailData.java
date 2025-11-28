package com.lab.piece_de_monnaie.dto.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoucherEmailData {
    private String voucherCode;
    private String alunoNome;
    private String alunoEmail;
    private String vantagemDescricao;
    private String empresaNome;
    private String empresaEmail;
}

