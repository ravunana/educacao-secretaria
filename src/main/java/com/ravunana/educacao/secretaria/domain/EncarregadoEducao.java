package com.ravunana.educacao.secretaria.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A EncarregadoEducao.
 */
@Entity
@Table(name = "encarregado_educao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "encarregadoeducao")
public class EncarregadoEducao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    
    @Column(name = "nif", unique = true)
    private String nif;

    
    @Column(name = "numero_identificacao", unique = true)
    private String numeroIdentificacao;

    @Column(name = "residencia")
    private String residencia;

    @NotNull
    @Column(name = "contacto_pessoal", nullable = false, unique = true)
    private String contactoPessoal;

    
    @Column(name = "contacto_trabalho", unique = true)
    private String contactoTrabalho;

    
    @Column(name = "contacto_residencia", unique = true)
    private String contactoResidencia;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "local_trabalho")
    private String localTrabalho;

    @Column(name = "cargo")
    private String cargo;

    @DecimalMin(value = "0")
    @Column(name = "salario", precision = 21, scale = 2)
    private BigDecimal salario;

    @NotNull
    @Column(name = "grau_parentesco", nullable = false)
    private String grauParentesco;

    @OneToMany(mappedBy = "encarregado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> alunos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public EncarregadoEducao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public EncarregadoEducao sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNif() {
        return nif;
    }

    public EncarregadoEducao nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public EncarregadoEducao numeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
        return this;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public String getResidencia() {
        return residencia;
    }

    public EncarregadoEducao residencia(String residencia) {
        this.residencia = residencia;
        return this;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getContactoPessoal() {
        return contactoPessoal;
    }

    public EncarregadoEducao contactoPessoal(String contactoPessoal) {
        this.contactoPessoal = contactoPessoal;
        return this;
    }

    public void setContactoPessoal(String contactoPessoal) {
        this.contactoPessoal = contactoPessoal;
    }

    public String getContactoTrabalho() {
        return contactoTrabalho;
    }

    public EncarregadoEducao contactoTrabalho(String contactoTrabalho) {
        this.contactoTrabalho = contactoTrabalho;
        return this;
    }

    public void setContactoTrabalho(String contactoTrabalho) {
        this.contactoTrabalho = contactoTrabalho;
    }

    public String getContactoResidencia() {
        return contactoResidencia;
    }

    public EncarregadoEducao contactoResidencia(String contactoResidencia) {
        this.contactoResidencia = contactoResidencia;
        return this;
    }

    public void setContactoResidencia(String contactoResidencia) {
        this.contactoResidencia = contactoResidencia;
    }

    public String getEmail() {
        return email;
    }

    public EncarregadoEducao email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public EncarregadoEducao localTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
        return this;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getCargo() {
        return cargo;
    }

    public EncarregadoEducao cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EncarregadoEducao salario(BigDecimal salario) {
        this.salario = salario;
        return this;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public EncarregadoEducao grauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
        return this;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public EncarregadoEducao alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public EncarregadoEducao addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.setEncarregado(this);
        return this;
    }

    public EncarregadoEducao removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.setEncarregado(null);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncarregadoEducao)) {
            return false;
        }
        return id != null && id.equals(((EncarregadoEducao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EncarregadoEducao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", nif='" + getNif() + "'" +
            ", numeroIdentificacao='" + getNumeroIdentificacao() + "'" +
            ", residencia='" + getResidencia() + "'" +
            ", contactoPessoal='" + getContactoPessoal() + "'" +
            ", contactoTrabalho='" + getContactoTrabalho() + "'" +
            ", contactoResidencia='" + getContactoResidencia() + "'" +
            ", email='" + getEmail() + "'" +
            ", localTrabalho='" + getLocalTrabalho() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", salario=" + getSalario() +
            ", grauParentesco='" + getGrauParentesco() + "'" +
            "}";
    }
}
