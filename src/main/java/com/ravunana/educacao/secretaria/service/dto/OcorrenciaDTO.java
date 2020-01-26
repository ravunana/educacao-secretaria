package com.ravunana.educacao.secretaria.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.educacao.secretaria.domain.Ocorrencia} entity.
 */
public class OcorrenciaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoOcorrencia;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private String numero;

    @NotNull
    private Boolean reportarEncarregado;

    private LocalDate de;

    private LocalDate ate;

    
    @Lob
    private String descricao;


    private Long matriculaId;

    private String matriculaNumeroProcesso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(String tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isReportarEncarregado() {
        return reportarEncarregado;
    }

    public void setReportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
    }

    public LocalDate getDe() {
        return de;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long alunoId) {
        this.matriculaId = alunoId;
    }

    public String getMatriculaNumeroProcesso() {
        return matriculaNumeroProcesso;
    }

    public void setMatriculaNumeroProcesso(String alunoNumeroProcesso) {
        this.matriculaNumeroProcesso = alunoNumeroProcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OcorrenciaDTO ocorrenciaDTO = (OcorrenciaDTO) o;
        if (ocorrenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ocorrenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OcorrenciaDTO{" +
            "id=" + getId() +
            ", tipoOcorrencia='" + getTipoOcorrencia() + "'" +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            ", reportarEncarregado='" + isReportarEncarregado() + "'" +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", matriculaId=" + getMatriculaId() +
            ", matriculaNumeroProcesso='" + getMatriculaNumeroProcesso() + "'" +
            "}";
    }
}
