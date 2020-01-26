package com.ravunana.educacao.secretaria.service.mapper;

import com.ravunana.educacao.secretaria.domain.*;
import com.ravunana.educacao.secretaria.service.dto.OcorrenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ocorrencia} and its DTO {@link OcorrenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlunoMapper.class})
public interface OcorrenciaMapper extends EntityMapper<OcorrenciaDTO, Ocorrencia> {

    @Mapping(source = "matricula.id", target = "matriculaId")
    @Mapping(source = "matricula.numeroProcesso", target = "matriculaNumeroProcesso")
    OcorrenciaDTO toDto(Ocorrencia ocorrencia);

    @Mapping(source = "matriculaId", target = "matricula")
    Ocorrencia toEntity(OcorrenciaDTO ocorrenciaDTO);

    default Ocorrencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(id);
        return ocorrencia;
    }
}
