package com.ravunana.educacao.secretaria.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.educacao.secretaria.web.rest.TestUtil;

public class EncarregadoEducaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncarregadoEducao.class);
        EncarregadoEducao encarregadoEducao1 = new EncarregadoEducao();
        encarregadoEducao1.setId(1L);
        EncarregadoEducao encarregadoEducao2 = new EncarregadoEducao();
        encarregadoEducao2.setId(encarregadoEducao1.getId());
        assertThat(encarregadoEducao1).isEqualTo(encarregadoEducao2);
        encarregadoEducao2.setId(2L);
        assertThat(encarregadoEducao1).isNotEqualTo(encarregadoEducao2);
        encarregadoEducao1.setId(null);
        assertThat(encarregadoEducao1).isNotEqualTo(encarregadoEducao2);
    }
}
