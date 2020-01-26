package com.ravunana.educacao.secretaria.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EncarregadoEducaoMapperTest {

    private EncarregadoEducaoMapper encarregadoEducaoMapper;

    @BeforeEach
    public void setUp() {
        encarregadoEducaoMapper = new EncarregadoEducaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(encarregadoEducaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(encarregadoEducaoMapper.fromId(null)).isNull();
    }
}
