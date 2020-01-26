package com.ravunana.educacao.secretaria.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EncarregadoEducaoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EncarregadoEducaoSearchRepositoryMockConfiguration {

    @MockBean
    private EncarregadoEducaoSearchRepository mockEncarregadoEducaoSearchRepository;

}
