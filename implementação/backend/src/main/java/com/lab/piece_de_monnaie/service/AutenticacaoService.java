package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.JwtResponse;
import com.lab.piece_de_monnaie.dto.LoginRequest;
import com.lab.piece_de_monnaie.dto.LoginResponse;
import com.lab.piece_de_monnaie.dto.UsuarioResponse;
import com.lab.piece_de_monnaie.entity.Usuario;
import com.lab.piece_de_monnaie.mapper.UsuarioMapper;
import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    public AutenticacaoService(UsuarioRepository usuarioRespository,
                               AuthenticationManager authenticationManager,
                               UsuarioMapper usuarioMapper,
                               JwtService jwtService) {
        this.usuarioRepository = usuarioRespository;
        this.authenticationManager = authenticationManager;
        this.usuarioMapper = usuarioMapper;
        this.jwtService = jwtService;
    }

    public LoginResponse autenticar(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.senha()
                )
        );

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.username())
                .orElseThrow();
        UsuarioResponse usuarioReponse = usuarioMapper.toUsuarioResponse(usuario);
        return new LoginResponse(usuarioReponse,
                new JwtResponse(jwtService.gerarToken(usuario)));
    }
}
