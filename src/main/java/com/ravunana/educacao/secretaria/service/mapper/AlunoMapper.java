package com.ravunana.educacao.secretaria.service.mapper;

import com.ravunana.educacao.secretaria.domain.*;
import com.ravunana.educacao.secretaria.service.dto.AlunoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aluno} and its DTO {@link AlunoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EncarregadoEducaoMapper.class})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {

    @Mapping(source = "encarregado.id", target = "encarregadoId")
    @Mapping(source = "encarregado.nome", target = "encarregadoNome")
    AlunoDTO toDto(Aluno aluno);

    @Mapping(target = "matriculas", ignore = true)
    @Mapping(target = "removeMatricula", ignore = true)
    @Mapping(target = "ocorrencias", ignore = true)
    @Mapping(target = "removeOcorrencia", ignore = true)
    @Mapping(source = "encarregadoId", target = "encarregado")
    Aluno toEntity(AlunoDTO alunoDTO);

    default Aluno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }
}
