package com.ravunana.educacao.secretaria.repository;

import com.ravunana.educacao.secretaria.domain.CategoriaAluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaAluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaAlunoRepository extends JpaRepository<CategoriaAluno, Long> {

}
