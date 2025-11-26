package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.transasao.TransacaoEnvioRequest;
import com.lab.piece_de_monnaie.entity.Aluno;
import com.lab.piece_de_monnaie.entity.Professor;
import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.entity.interfaces.Poupançavel;
import com.lab.piece_de_monnaie.exception.SaldoInvalidoException;
import com.lab.piece_de_monnaie.repository.TransacaoRepository;
import com.lab.piece_de_monnaie.type.TipoTransacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final TransacaoRepository transacaoRepository;
    private final VantagemService vantagemService;

    public List<Transacao> findAllOwn(Authentication authentication) {
        return transacaoRepository.findAllByEmissorOrReceptorUsername(authentication.getName());
    }

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
        checaValidadeDeSaldoPorMontanteGasto(professor, transacaoEnvioRequest.montante());
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

    public Transacao solicitaTransacaoTrocaByVantagemId(Authentication authentication, Long vantagemId) {
        return this.computaTransacaoTrocaByVantagemId(authentication, vantagemId);
        // para envio posterior de emails. usa o id da transação como código de email mesmo, fodase.
    }

    public Transacao computaTransacaoTrocaByVantagemId(Authentication authentication, Long vantagemId) {
        var vantagem = vantagemService.findByIdOrThrow(vantagemId);
        var aluno = alunoService.findByUsernameOrThrow(authentication.getName());
        checaValidadeDeSaldoPorMontanteGasto(aluno, vantagem.getValor());
        aluno.setQuantidadeMoeda(aluno.getQuantidadeMoeda() - vantagem.getValor());

        var transacao = this.criaTransacaoDeTrocaDeAlunoEVantagem(aluno, vantagem);
        return transacaoRepository.save(transacao);
    }

    private void checaValidadeDeSaldoPorMontanteGasto(Poupançavel usuario, Long montante) {
        if (!usuario.possuiSaldoSuficiente(montante)) {
            throw new SaldoInvalidoException("Saldo insuficiente para transação.");
        }
    }

    private Transacao criaTransacaoDeTrocaDeAlunoEVantagem(Aluno aluno, Vantagem vantagem) {
        return Transacao.builder()
                .montante(vantagem.getValor())
                .mensagem("Transação realizada em: "  + LocalDateTime.now())
                .tipo(TipoTransacao.TROCA)
                .vantagem(vantagem)
                .emissor(aluno)
                .receptor(vantagem.getEmpresaParceira())
                .build();
    }

}
