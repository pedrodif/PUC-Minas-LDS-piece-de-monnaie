package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.auth.JwtResponse;
import com.lab.piece_de_monnaie.dto.auth.LoginRequest;
import com.lab.piece_de_monnaie.dto.auth.LoginResponse;
import com.lab.piece_de_monnaie.dto.auth.UsuarioResponse;
import com.lab.piece_de_monnaie.entity.Aluno;
import com.lab.piece_de_monnaie.entity.EmpresaParceira;
import com.lab.piece_de_monnaie.entity.Professor;
import com.lab.piece_de_monnaie.entity.Usuario;
import com.lab.piece_de_monnaie.mapper.AlunoMapper;
import com.lab.piece_de_monnaie.mapper.EmpresaParceiraMapper;
import com.lab.piece_de_monnaie.mapper.ProfessorMapper;
import com.lab.piece_de_monnaie.repository.AlunoRepository;
import com.lab.piece_de_monnaie.repository.EmpresaParceiraRepository;
import com.lab.piece_de_monnaie.repository.ProfessorRepository;
import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final AlunoMapper alunoMapper;
    private final ProfessorMapper professorMapper;
    private final EmpresaParceiraMapper empresaParceiraMapper;
    private final JwtService jwtService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final EmpresaParceiraRepository empresaParceiraRepository;


    public LoginResponse autenticar(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.senha()
                )
        );

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.username())
                .orElseThrow();

        UsuarioResponse usuarioResponse = null;
        switch (usuario.getTipo()) {
            case ALUNO:
                usuarioResponse = getAlunoResponse(usuario.getId());
                break;
            case PROFESSOR:
                usuarioResponse = getProfessorResponse(usuario.getId());
                break;
            case EMPRESA:
                usuarioResponse = getEmpresaParceiraResponse(usuario.getId());
                break;
            default:
                break;
        }

        return new LoginResponse(usuarioResponse,
                new JwtResponse(jwtService.gerarToken(usuario)));
    }

    private UsuarioResponse getAlunoResponse(Long usuarioId) {
        Aluno aluno = alunoRepository.findById(usuarioId).orElseThrow();
        return alunoMapper.toAlunoResponse(aluno);
    }

    private UsuarioResponse getProfessorResponse(Long usuarioId) {
        Professor professor = professorRepository.findById(usuarioId).orElseThrow();
        return professorMapper.toProfessorResponse(professor);
    }

    private UsuarioResponse getEmpresaParceiraResponse(Long usuarioId) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(usuarioId).orElseThrow();
        return empresaParceiraMapper.toEmpresaParceiraResponse(empresaParceira);
    }
}
