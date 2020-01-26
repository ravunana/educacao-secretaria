package com.ravunana.educacao.secretaria.service.mapper;

import com.ravunana.educacao.secretaria.domain.*;
import com.ravunana.educacao.secretaria.service.dto.EncarregadoEducaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EncarregadoEducao} and its DTO {@link EncarregadoEducaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EncarregadoEducaoMapper extends EntityMapper<EncarregadoEducaoDTO, EncarregadoEducao> {


    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "removeAluno", ignore = true)
    EncarregadoEducao toEntity(EncarregadoEducaoDTO encarregadoEducaoDTO);

    default EncarregadoEducao fromId(Long id) {
        if (id == null) {
            return null;
        }
        EncarregadoEducao encarregadoEducao = new EncarregadoEducao();
        encarregadoEducao.setId(id);
        return encarregadoEducao;
    }
}
