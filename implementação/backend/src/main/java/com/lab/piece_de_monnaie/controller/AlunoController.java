package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.AlunoDTO;
import com.lab.piece_de_monnaie.dto.CreateAlunoDTO;
import com.lab.piece_de_monnaie.dto.UpdateAlunoDTO;
import com.lab.piece_de_monnaie.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {
    
    private final AlunoService alunoService;
    
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll() {
        List<AlunoDTO> alunos = alunoService.findAll();
        return ResponseEntity.ok(alunos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable Long id) {
        AlunoDTO aluno = alunoService.findById(id);
        return ResponseEntity.ok(aluno);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateAlunoDTO updateAlunoDTO) {
        AlunoDTO aluno = alunoService.update(id, updateAlunoDTO);
        return ResponseEntity.ok(aluno);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<AlunoDTO>> findByCurso(@PathVariable Long cursoId) {
        List<AlunoDTO> alunos = alunoService.findByCurso(cursoId);
        return ResponseEntity.ok(alunos);
    }
    
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<AlunoDTO>> findByProfessor(@PathVariable Long professorId) {
        List<AlunoDTO> alunos = alunoService.findByProfessor(professorId);
        return ResponseEntity.ok(alunos);
    }
}
