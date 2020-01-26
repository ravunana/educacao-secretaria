package com.ravunana.educacao.secretaria.repository.search;

import com.ravunana.educacao.secretaria.domain.CategoriaAluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CategoriaAluno} entity.
 */
public interface CategoriaAlunoSearchRepository extends ElasticsearchRepository<CategoriaAluno, Long> {
}
