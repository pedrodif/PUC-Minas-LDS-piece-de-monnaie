package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UsuarioRepository usuarioRepository;

    @Value("${security.jwt.secret-key}")
    private String chave;

    @Value("${security.jwt.expiration-time}")
    private long tempoExpiracao;

    public String extrairUsername(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public Date extrairDataExpiracao(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extrairTodosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String gerarToken(UserDetails usuario) {
        return construirToken(usuario, tempoExpiracao);
    }

    private String construirToken(UserDetails usuario, long tempoExpiracao) {
       return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValido(String token) {
        String username = extrairUsername(token);
        return (usuarioRepository.existsByUsername(username)
                && !isTokenExpirado(token));
    }

    public boolean isTokenExpirado(String token) {
        return extrairDataExpiracao(token).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(chave);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
