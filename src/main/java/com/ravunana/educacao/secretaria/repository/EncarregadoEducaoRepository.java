package com.ravunana.educacao.secretaria.repository;

import com.ravunana.educacao.secretaria.domain.EncarregadoEducao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EncarregadoEducao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncarregadoEducaoRepository extends JpaRepository<EncarregadoEducao, Long> {

}
