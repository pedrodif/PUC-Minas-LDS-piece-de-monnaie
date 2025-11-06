package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.EmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.CreateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.UpdateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.entity.EmpresaParceira;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.exception.ResourceNotFoundException;
import com.lab.piece_de_monnaie.mapper.EmpresaParceiraMapper;
import com.lab.piece_de_monnaie.mapper.VantagemMapper;
import com.lab.piece_de_monnaie.repository.EmpresaParceiraRepository;
import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import com.lab.piece_de_monnaie.repository.VantagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaParceiraService {
    
    private final EmpresaParceiraRepository empresaParceiraRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaParceiraMapper empresaParceiraMapper;
    private final PasswordEncoder passwordEncoder;
    private final VantagemMapper vantagemMapper;
    private final VantagemRepository vantagemRepository;

    public List<EmpresaParceiraDTO> findAll() {
        return empresaParceiraRepository.findAll().stream()
                .map(empresaParceiraMapper::toDTO)
                .toList();
    }
    
    public EmpresaParceiraDTO findById(Long id) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa Parceira não encontrada com ID: " + id));
        return empresaParceiraMapper.toDTO(empresaParceira);
    }
    
    public EmpresaParceiraDTO create(CreateEmpresaParceiraDTO createEmpresaParceiraDTO) {
        // Verificar se CNPJ já existe
        if (empresaParceiraRepository.existsByCnpj(createEmpresaParceiraDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado: " + createEmpresaParceiraDTO.getCnpj());
        }
        
        // Verificar se email já existe
        if (empresaParceiraRepository.existsByEmail(createEmpresaParceiraDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + createEmpresaParceiraDTO.getEmail());
        }
        
        // Verificar se username já existe
        if (usuarioRepository.existsByUsername(createEmpresaParceiraDTO.getUsername())) {
            throw new IllegalArgumentException("Username já cadastrado: " + createEmpresaParceiraDTO.getUsername());
        }
        
        // Criar empresa parceira (que herda de Usuario)
        EmpresaParceira empresaParceira = new EmpresaParceira();
        empresaParceira.setUsername(createEmpresaParceiraDTO.getUsername());
        empresaParceira.setSenha(passwordEncoder.encode(createEmpresaParceiraDTO.getSenha()));
        empresaParceira.setNome(createEmpresaParceiraDTO.getNome());
        empresaParceira.setCnpj(createEmpresaParceiraDTO.getCnpj());
        empresaParceira.setEmail(createEmpresaParceiraDTO.getEmail());
        
        EmpresaParceira savedEmpresaParceira = empresaParceiraRepository.save(empresaParceira);
        return empresaParceiraMapper.toDTO(savedEmpresaParceira);
    }
    
    public EmpresaParceiraDTO update(Long id, UpdateEmpresaParceiraDTO updateEmpresaParceiraDTO) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa Parceira não encontrada com ID: " + id));
        
        // Verificar se está alterando CNPJ e se já existe
        if (updateEmpresaParceiraDTO.getCnpj() != null && 
            !updateEmpresaParceiraDTO.getCnpj().equals(empresaParceira.getCnpj()) &&
            empresaParceiraRepository.existsByCnpj(updateEmpresaParceiraDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado: " + updateEmpresaParceiraDTO.getCnpj());
        }
        
        // Verificar se está alterando email e se já existe
        if (updateEmpresaParceiraDTO.getEmail() != null && 
            !updateEmpresaParceiraDTO.getEmail().equals(empresaParceira.getEmail()) &&
            empresaParceiraRepository.existsByEmail(updateEmpresaParceiraDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + updateEmpresaParceiraDTO.getEmail());
        }
        
        empresaParceiraMapper.updateEntity(updateEmpresaParceiraDTO, empresaParceira);
        EmpresaParceira savedEmpresaParceira = empresaParceiraRepository.save(empresaParceira);
        return empresaParceiraMapper.toDTO(savedEmpresaParceira);
    }
    
    public void delete(Long id) {
        if (!empresaParceiraRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empresa Parceira não encontrada com ID: " + id);
        }
        empresaParceiraRepository.deleteById(id);
    }

    public Vantagem cadastrarVantagemEmEmpresaParceira(VantagemRequest vantagemRequest, Long empresaParceiraId) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(empresaParceiraId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa Parceira não encontrada com ID: " + empresaParceiraId));

        Vantagem vantagem = vantagemMapper.toVantagem(vantagemRequest);
        vantagem.setEmpresaParceira(empresaParceira);
        return vantagemRepository.save(vantagem);
    }
}
