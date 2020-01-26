package com.ravunana.educacao.secretaria.repository.search;

import com.ravunana.educacao.secretaria.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Aluno} entity.
 */
public interface AlunoSearchRepository extends ElasticsearchRepository<Aluno, Long> {
}
