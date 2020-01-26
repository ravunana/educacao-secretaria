package com.ravunana.educacao.secretaria.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Matricula.
 */
@Entity
@Table(name = "sec_matricula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matricula")
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @NotNull
    @Min(value = 1)
    @Column(name = "numero_chamada", nullable = false)
    private Integer numeroChamada;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private Integer anoLectivo;

    @NotNull
    @Column(name = "fotografia", nullable = false)
    private Boolean fotografia;

    @NotNull
    @Column(name = "certificado", nullable = false)
    private Boolean certificado;

    @NotNull
    @Column(name = "documento_identificacao", nullable = false)
    private Boolean documentoIdentificacao;

    @Column(name = "resenciamento_militar")
    private Boolean resenciamentoMilitar;

    @Column(name = "documento_saude")
    private Boolean documentoSaude;

    @Column(name = "ficha_transferencia")
    private Boolean fichaTransferencia;

    @Column(name = "historico_escolar")
    private Boolean historicoEscolar;

    @Lob
    @Column(name = "observacao")
    private String observacao;

    @NotNull
    @Column(name = "confirmacao", nullable = false)
    private Boolean confirmacao;

    @NotNull
    @Column(name = "periodo_lectivo", nullable = false)
    private String periodoLectivo;

    @NotNull
    @Column(name = "turma", nullable = false)
    private String turma;

    @NotNull
    @Column(name = "curso", nullable = false)
    private String curso;

    @NotNull
    @Column(name = "turno", nullable = false)
    private String turno;

    @NotNull
    @Column(name = "sala", nullable = false)
    private Integer sala;

    @NotNull
    @Column(name = "classe", nullable = false)
    private Integer classe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("matriculas")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("matriculas")
    private CategoriaAluno categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Matricula data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Integer getNumeroChamada() {
        return numeroChamada;
    }

    public Matricula numeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
        return this;
    }

    public void setNumeroChamada(Integer numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public Matricula anoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public Boolean isFotografia() {
        return fotografia;
    }

    public Matricula fotografia(Boolean fotografia) {
        this.fotografia = fotografia;
        return this;
    }

    public void setFotografia(Boolean fotografia) {
        this.fotografia = fotografia;
    }

    public Boolean isCertificado() {
        return certificado;
    }

    public Matricula certificado(Boolean certificado) {
        this.certificado = certificado;
        return this;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public Boolean isDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public Matricula documentoIdentificacao(Boolean documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
        return this;
    }

    public void setDocumentoIdentificacao(Boolean documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public Boolean isResenciamentoMilitar() {
        return resenciamentoMilitar;
    }

    public Matricula resenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
        return this;
    }

    public void setResenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
    }

    public Boolean isDocumentoSaude() {
        return documentoSaude;
    }

    public Matricula documentoSaude(Boolean documentoSaude) {
        this.documentoSaude = documentoSaude;
        return this;
    }

    public void setDocumentoSaude(Boolean documentoSaude) {
        this.documentoSaude = documentoSaude;
    }

    public Boolean isFichaTransferencia() {
        return fichaTransferencia;
    }

    public Matricula fichaTransferencia(Boolean fichaTransferencia) {
        this.fichaTransferencia = fichaTransferencia;
        return this;
    }

    public void setFichaTransferencia(Boolean fichaTransferencia) {
        this.fichaTransferencia = fichaTransferencia;
    }

    public Boolean isHistoricoEscolar() {
        return historicoEscolar;
    }

    public Matricula historicoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
        return this;
    }

    public void setHistoricoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
    }

    public String getObservacao() {
        return observacao;
    }

    public Matricula observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean isConfirmacao() {
        return confirmacao;
    }

    public Matricula confirmacao(Boolean confirmacao) {
        this.confirmacao = confirmacao;
        return this;
    }

    public void setConfirmacao(Boolean confirmacao) {
        this.confirmacao = confirmacao;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public Matricula periodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
        return this;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public String getTurma() {
        return turma;
    }

    public Matricula turma(String turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getCurso() {
        return curso;
    }

    public Matricula curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public Matricula turno(String turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getSala() {
        return sala;
    }

    public Matricula sala(Integer sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public Integer getClasse() {
        return classe;
    }

    public Matricula classe(Integer classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Matricula aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public CategoriaAluno getCategoria() {
        return categoria;
    }

    public Matricula categoria(CategoriaAluno categoriaAluno) {
        this.categoria = categoriaAluno;
        return this;
    }

    public void setCategoria(CategoriaAluno categoriaAluno) {
        this.categoria = categoriaAluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matricula)) {
            return false;
        }
        return id != null && id.equals(((Matricula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Matricula{" +
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
            "}";
    }
}
