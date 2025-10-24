package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.AlunoDTO;
import com.lab.piece_de_monnaie.dto.CreateAlunoDTO;
import com.lab.piece_de_monnaie.dto.UpdateAlunoDTO;
import com.lab.piece_de_monnaie.entity.Aluno;
import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.exception.ResourceNotFoundException;
import com.lab.piece_de_monnaie.mapper.AlunoMapper;
import com.lab.piece_de_monnaie.repository.AlunoRepository;
import com.lab.piece_de_monnaie.repository.CursoRepository;
import com.lab.piece_de_monnaie.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlunoService {
    
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoMapper alunoMapper;
    
    public List<AlunoDTO> findAll() {
        return alunoRepository.findAll().stream()
                .map(alunoMapper::toDTO)
                .toList();
    }
    
    public AlunoDTO findById(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));
        return alunoMapper.toDTO(aluno);
    }
    
    public AlunoDTO create(CreateAlunoDTO createAlunoDTO) {
        // Verificar se o curso existe
        Curso curso = cursoRepository.findById(createAlunoDTO.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + createAlunoDTO.getCursoId()));
        
        // Verificar se CPF já existe
        if (alunoRepository.existsByCpf(createAlunoDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + createAlunoDTO.getCpf());
        }
        
        // Verificar se username já existe
        if (usuarioRepository.existsByUsername(createAlunoDTO.getUsername())) {
            throw new IllegalArgumentException("Username já cadastrado: " + createAlunoDTO.getUsername());
        }
        
        // Criar aluno (que herda de Usuario)
        Aluno aluno = new Aluno();
        aluno.setUsername(createAlunoDTO.getUsername());
        aluno.setSenha(createAlunoDTO.getSenha());
        aluno.setNome(createAlunoDTO.getNome());
        aluno.setCpf(createAlunoDTO.getCpf());
        aluno.setRg(createAlunoDTO.getRg());
        aluno.setEmail(createAlunoDTO.getEmail());
        aluno.setEndereco(createAlunoDTO.getEndereco());
        aluno.setQuantidadeMoeda(0L);
        aluno.setCurso(curso);
        
        Aluno savedAluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(savedAluno);
    }
    
    public AlunoDTO update(Long id, UpdateAlunoDTO updateAlunoDTO) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));
        
        // Se está alterando o curso, verificar se existe
        if (updateAlunoDTO.getCursoId() != null) {
            Curso curso = cursoRepository.findById(updateAlunoDTO.getCursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + updateAlunoDTO.getCursoId()));
            aluno.setCurso(curso);
        }
        
        alunoMapper.updateEntity(updateAlunoDTO, aluno);
        Aluno savedAluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(savedAluno);
    }
    
    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado com ID: " + id);
        }
        alunoRepository.deleteById(id);
    }
    
    public List<AlunoDTO> findByCurso(Long cursoId) {
        return alunoRepository.findByCursoId(cursoId).stream()
                .map(alunoMapper::toDTO)
                .toList();
    }
}
