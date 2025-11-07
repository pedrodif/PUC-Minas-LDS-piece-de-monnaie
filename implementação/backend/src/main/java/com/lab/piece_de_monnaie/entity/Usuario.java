package com.lab.piece_de_monnaie.entity;

import com.lab.piece_de_monnaie.type.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "senha")
    private String senha;
    @Column(name = "nome")
    private String nome;
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipo.toString()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
