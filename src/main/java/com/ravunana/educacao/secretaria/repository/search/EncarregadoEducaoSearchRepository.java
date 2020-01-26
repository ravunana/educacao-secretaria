package com.ravunana.educacao.secretaria.repository.search;

import com.ravunana.educacao.secretaria.domain.EncarregadoEducao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EncarregadoEducao} entity.
 */
public interface EncarregadoEducaoSearchRepository extends ElasticsearchRepository<EncarregadoEducao, Long> {
}
