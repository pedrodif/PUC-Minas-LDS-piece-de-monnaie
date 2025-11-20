package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.entity.Professor;
import com.lab.piece_de_monnaie.exception.ResourceNotFoundException;
import com.lab.piece_de_monnaie.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor save(Professor professor){
        return professorRepository.save(professor);
    }

    public Professor findByUsernameOrThrow(String username) {
        return professorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Professor de username: " + username + " não encontrado."));
    }
}
