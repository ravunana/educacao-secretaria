package com.ravunana.educacao.secretaria.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Ocorrencia.
 */
@Entity
@Table(name = "sec_ocorrencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ocorrencia")
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "tipo_ocorrencia", nullable = false)
    private String tipoOcorrencia;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotNull
    @Column(name = "reportar_encarregado", nullable = false)
    private Boolean reportarEncarregado;

    @Column(name = "de")
    private LocalDate de;

    @Column(name = "ate")
    private LocalDate ate;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ocorrencias")
    private Aluno matricula;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public Ocorrencia tipoOcorrencia(String tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
        return this;
    }

    public void setTipoOcorrencia(String tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Ocorrencia data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public Ocorrencia numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isReportarEncarregado() {
        return reportarEncarregado;
    }

    public Ocorrencia reportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
        return this;
    }

    public void setReportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
    }

    public LocalDate getDe() {
        return de;
    }

    public Ocorrencia de(LocalDate de) {
        this.de = de;
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public Ocorrencia ate(LocalDate ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return descricao;
    }

    public Ocorrencia descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Aluno getMatricula() {
        return matricula;
    }

    public Ocorrencia matricula(Aluno aluno) {
        this.matricula = aluno;
        return this;
    }

    public void setMatricula(Aluno aluno) {
        this.matricula = aluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ocorrencia)) {
            return false;
        }
        return id != null && id.equals(((Ocorrencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" +
            "id=" + getId() +
            ", tipoOcorrencia='" + getTipoOcorrencia() + "'" +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            ", reportarEncarregado='" + isReportarEncarregado() + "'" +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
