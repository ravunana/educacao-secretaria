package com.ravunana.educacao.secretaria.repository;

import com.ravunana.educacao.secretaria.domain.Ocorrencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ocorrencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

}
