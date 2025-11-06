package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.VantagemResponse;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.mapper.VantagemMapper;
import com.lab.piece_de_monnaie.service.VantagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vantagens")
@RequiredArgsConstructor
public class VantagemController {

    private final VantagemService vantagemService;
    private final VantagemMapper vantagemMapper;

    @GetMapping()
    public ResponseEntity<List<VantagemResponse>> findAllVantagens() {
        return ResponseEntity.ok(vantagemService.findAll().stream().map(vantagemMapper::toVantagemResponse).toList());
    }

    @GetMapping("/{vantagemId}")
    public ResponseEntity<VantagemResponse> findById(@PathVariable Long vantagemId) {
        Optional<Vantagem> vantagemOptional = vantagemService.findById(vantagemId);
        return vantagemOptional.map(
                        vantagem -> ResponseEntity.ok()
                                .body(vantagemMapper
                                        .toVantagemResponse(vantagem)))
                .orElse(ResponseEntity.notFound().build());
    }
}
