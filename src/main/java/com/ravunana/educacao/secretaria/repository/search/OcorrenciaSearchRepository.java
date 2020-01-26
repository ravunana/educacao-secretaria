package com.ravunana.educacao.secretaria.repository.search;

import com.ravunana.educacao.secretaria.domain.Ocorrencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ocorrencia} entity.
 */
public interface OcorrenciaSearchRepository extends ElasticsearchRepository<Ocorrencia, Long> {
}
