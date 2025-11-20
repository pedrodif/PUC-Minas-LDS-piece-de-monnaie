package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.entity.Aluno;
import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.exception.ResourceNotFoundException;
import com.lab.piece_de_monnaie.exception.SaldoInvalidoException;
import com.lab.piece_de_monnaie.mapper.VantagemMapper;
import com.lab.piece_de_monnaie.repository.VantagemRepository;
import com.lab.piece_de_monnaie.type.TipoTransacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    private final VantagemMapper vantagemMapper;
    private final AlunoService alunoService;

    public List<Vantagem> findAll() {
        return vantagemRepository.findAll();
    }

    public Optional<Vantagem> findById(Long id) {
        return vantagemRepository.findById(id);
    }

    public Vantagem update(VantagemRequest vantagemRequest, Long vantagemId) {
        var vantagem = findByIdOrThrow(vantagemId);

        Vantagem vantagemAtualizada = vantagemMapper.updateVantagem(vantagemRequest, vantagem);
        return vantagemRepository.save(vantagemAtualizada);
    }

    public void deleteById(Long id) {
        vantagemRepository.deleteById(id);
    }

    // todo: refatorar posteriormente para TransacaoService
//    public void resgatarVantagemOfId(Long vantagemId, Principal principal) {
//        var vantagem = findByIdOrThrow(vantagemId);
//        var aluno = alunoService.findByUsernameOrThrow(principal.getName());
//        if(!aluno.podeComprar(vantagem)) {
//            throw new SaldoInvalidoException("O saldo é insuficiente para realizar a transação.");
//        }
//        var transacao = Transacao.builder()
//                .montante(vantagem.getValor())
//                .mensagem("Transação realizada em: "  + LocalDateTime.now())
//                .tipo(TipoTransacao.TROCA)
//                .vantagem(vantagem)
//                .emissor(aluno)
//                .receptor(vantagem.getEmpresaParceira())
//                .build();
//
//    }

    private Vantagem findByIdOrThrow(Long vantagemId) {
        return vantagemRepository.findById(vantagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Vantagem de id " + vantagemId + " não encontrada."));
    }
}
