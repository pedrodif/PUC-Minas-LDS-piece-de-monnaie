package com.lab.piece_de_monnaie.service.email;

import com.lab.piece_de_monnaie.dto.email.VoucherEmailData;

public interface EmailService {
    void enviarEmailVoucherAluno(VoucherEmailData dados);
    void enviarEmailVoucherEmpresa(VoucherEmailData dados);
}

