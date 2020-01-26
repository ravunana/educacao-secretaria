package com.ravunana.educacao.secretaria.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.educacao.secretaria.web.rest.TestUtil;

public class EncarregadoEducaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncarregadoEducaoDTO.class);
        EncarregadoEducaoDTO encarregadoEducaoDTO1 = new EncarregadoEducaoDTO();
        encarregadoEducaoDTO1.setId(1L);
        EncarregadoEducaoDTO encarregadoEducaoDTO2 = new EncarregadoEducaoDTO();
        assertThat(encarregadoEducaoDTO1).isNotEqualTo(encarregadoEducaoDTO2);
        encarregadoEducaoDTO2.setId(encarregadoEducaoDTO1.getId());
        assertThat(encarregadoEducaoDTO1).isEqualTo(encarregadoEducaoDTO2);
        encarregadoEducaoDTO2.setId(2L);
        assertThat(encarregadoEducaoDTO1).isNotEqualTo(encarregadoEducaoDTO2);
        encarregadoEducaoDTO1.setId(null);
        assertThat(encarregadoEducaoDTO1).isNotEqualTo(encarregadoEducaoDTO2);
    }
}
