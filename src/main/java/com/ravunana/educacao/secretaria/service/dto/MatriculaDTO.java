package com.ravunana.educacao.secretaria.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.educacao.secretaria.domain.Matricula} entity.
 */
public class MatriculaDTO implements Serializable {

    private Long id;

    private ZonedDateTime data;

    @NotNull
    @Min(value = 1)
    private Integer numeroChamada;

    @NotNull
    private Integer anoLectivo;

    @NotNull
    private Boolean fotografia;

    @NotNull
    private Boolean certificado;

    @NotNull
    private Boolean documentoIdentificacao;

    private Boolean resenciamentoMilitar;

    private Boolean documentoSaude;

    private Boolean fichaTransferencia;

    private Boolean historicoEscolar;

    @Lob
    private String observacao;

    @NotNull
    private Boolean confirmacao;

    @NotNull
    private String periodoLectivo;

    @NotNull
    private String turma;

    @NotNull
    private String curso;

    @NotNull
    private String turno;

    @NotNull
    private Integer sala;

    @NotNull
    private Integer classe;


    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long categoriaId;

    private String categoriaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Integer getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public Boolean isFotografia() {
        return fotografia;
    }

    public void setFotografia(Boolean fotografia) {
        this.fotografia = fotografia;
    }

    public Boolean isCertificado() {
        return certificado;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public Boolean isDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(Boolean documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public Boolean isResenciamentoMilitar() {
        return resenciamentoMilitar;
    }

    public void setResenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
    }

    public Boolean isDocumentoSaude() {
        return documentoSaude;
    }

    public void setDocumentoSaude(Boolean documentoSaude) {
        this.documentoSaude = documentoSaude;
    }

    public Boolean isFichaTransferencia() {
        return fichaTransferencia;
    }

    public void setFichaTransferencia(Boolean fichaTransferencia) {
        this.fichaTransferencia = fichaTransferencia;
    }

    public Boolean isHistoricoEscolar() {
        return historicoEscolar;
    }

    public void setHistoricoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean isConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(Boolean confirmacao) {
        this.confirmacao = confirmacao;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNumeroProcesso() {
        return alunoNumeroProcesso;
    }

    public void setAlunoNumeroProcesso(String alunoNumeroProcesso) {
        this.alunoNumeroProcesso = alunoNumeroProcesso;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaAlunoId) {
        this.categoriaId = categoriaAlunoId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaAlunoNome) {
        this.categoriaNome = categoriaAlunoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatriculaDTO matriculaDTO = (MatriculaDTO) o;
        if (matriculaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matriculaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatriculaDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", numeroChamada=" + getNumeroChamada() +
            ", anoLectivo=" + getAnoLectivo() +
            ", fotografia='" + isFotografia() + "'" +
            ", certificado='" + isCertificado() + "'" +
            ", documentoIdentificacao='" + isDocumentoIdentificacao() + "'" +
            ", resenciamentoMilitar='" + isResenciamentoMilitar() + "'" +
            ", documentoSaude='" + isDocumentoSaude() + "'" +
            ", fichaTransferencia='" + isFichaTransferencia() + "'" +
            ", historicoEscolar='" + isHistoricoEscolar() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", confirmacao='" + isConfirmacao() + "'" +
            ", periodoLectivo='" + getPeriodoLectivo() + "'" +
            ", turma='" + getTurma() + "'" +
            ", curso='" + getCurso() + "'" +
            ", turno='" + getTurno() + "'" +
            ", sala=" + getSala() +
            ", classe=" + getClasse() +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", categoriaId=" + getCategoriaId() +
            ", categoriaNome='" + getCategoriaNome() + "'" +
            "}";
    }
}
