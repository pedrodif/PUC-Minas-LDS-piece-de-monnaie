package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.dto.VantagemResponse;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.mapper.VantagemMapper;
import com.lab.piece_de_monnaie.service.VantagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{vantagemId}")
    public ResponseEntity<VantagemResponse> update(@PathVariable Long vantagemId,
                                                   @RequestBody @Valid VantagemRequest vantagemRequest) {
        return ResponseEntity.ok(vantagemMapper
                .toVantagemResponse(vantagemService
                        .update(vantagemRequest, vantagemId)));
    }

    @DeleteMapping("/{vantagemId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long vantagemId) {
        vantagemService.deleteById(vantagemId);
        return ResponseEntity.ok().build();
    }
}
