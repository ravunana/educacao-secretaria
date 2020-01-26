package com.ravunana.educacao.secretaria.repository;

import com.ravunana.educacao.secretaria.domain.Matricula;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Matricula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

}
