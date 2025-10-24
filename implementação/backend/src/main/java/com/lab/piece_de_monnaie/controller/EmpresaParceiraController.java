package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.EmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.CreateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.dto.UpdateEmpresaParceiraDTO;
import com.lab.piece_de_monnaie.service.EmpresaParceiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas-parceiras")
@RequiredArgsConstructor
public class EmpresaParceiraController {
    
    private final EmpresaParceiraService empresaParceiraService;
    
    @GetMapping
    public ResponseEntity<List<EmpresaParceiraDTO>> findAll() {
        List<EmpresaParceiraDTO> empresas = empresaParceiraService.findAll();
        return ResponseEntity.ok(empresas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaParceiraDTO> findById(@PathVariable Long id) {
        EmpresaParceiraDTO empresa = empresaParceiraService.findById(id);
        return ResponseEntity.ok(empresa);
    }
    
    @PostMapping
    public ResponseEntity<EmpresaParceiraDTO> create(@Valid @RequestBody CreateEmpresaParceiraDTO createEmpresaParceiraDTO) {
        EmpresaParceiraDTO empresa = empresaParceiraService.create(createEmpresaParceiraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaParceiraDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateEmpresaParceiraDTO updateEmpresaParceiraDTO) {
        EmpresaParceiraDTO empresa = empresaParceiraService.update(id, updateEmpresaParceiraDTO);
        return ResponseEntity.ok(empresa);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empresaParceiraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
