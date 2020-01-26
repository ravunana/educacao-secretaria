package com.ravunana.educacao.secretaria.service.mapper;

import com.ravunana.educacao.secretaria.domain.*;
import com.ravunana.educacao.secretaria.service.dto.MatriculaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Matricula} and its DTO {@link MatriculaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlunoMapper.class, CategoriaAlunoMapper.class})
public interface MatriculaMapper extends EntityMapper<MatriculaDTO, Matricula> {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    MatriculaDTO toDto(Matricula matricula);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "categoriaId", target = "categoria")
    Matricula toEntity(MatriculaDTO matriculaDTO);

    default Matricula fromId(Long id) {
        if (id == null) {
            return null;
        }
        Matricula matricula = new Matricula();
        matricula.setId(id);
        return matricula;
    }
}
