package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.transasao.TransacaoEnvioRequest;
import com.lab.piece_de_monnaie.entity.Aluno;
import com.lab.piece_de_monnaie.entity.Professor;
import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.exception.SaldoInvalidoException;
import com.lab.piece_de_monnaie.repository.TransacaoRepository;
import com.lab.piece_de_monnaie.type.TipoTransacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final TransacaoRepository transacaoRepository;

    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> findById(Long id) {
        return transacaoRepository.findById(id);
    }

    @Transactional
    public Transacao solicitaTransacaoEnvio(Authentication authentication,
                                            Long alunoDestinatarioId,
                                            TransacaoEnvioRequest transacaoEnvioRequest) {
        var professor = professorService.findByUsernameOrThrow(authentication.getName());
        checaValidadeDeSaldo(professor, transacaoEnvioRequest.montante());
        log.info("Saldo suficiente encontrado em professor {}", professor.getUsername());

        var aluno = alunoService.findByIdOrThrow(alunoDestinatarioId);

        computaTransacaoProfessorAluno(professor, aluno, transacaoEnvioRequest.montante());

        return transacaoRepository.save(Transacao.builder()
                .montante(transacaoEnvioRequest.montante())
                .mensagem(transacaoEnvioRequest.mensagem())
                .tipo(TipoTransacao.ENVIO)
                .emissor(professor)
                .receptor(aluno)
                .build());
    }

    @Transactional
    private void computaTransacaoProfessorAluno(Professor professor, Aluno aluno, Long montante) {
        professor.setQuantidadeMoeda(professor.getQuantidadeMoeda() - montante);
        aluno.setQuantidadeMoeda(aluno.getQuantidadeMoeda() + montante);
        professorService.save(professor);
        alunoService.save(aluno);
    }

    private void checaValidadeDeSaldo(Professor professor, Long montante) {
        if (!professor.temSaldo(montante)) {
            throw new SaldoInvalidoException("Saldo insuficiente para envio.");
        }
    }
}
