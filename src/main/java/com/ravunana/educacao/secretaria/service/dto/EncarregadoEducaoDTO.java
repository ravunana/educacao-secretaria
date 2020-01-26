package com.ravunana.educacao.secretaria.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.educacao.secretaria.domain.EncarregadoEducao} entity.
 */
public class EncarregadoEducaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sexo;

    
    private String nif;

    
    private String numeroIdentificacao;

    private String residencia;

    @NotNull
    private String contactoPessoal;

    
    private String contactoTrabalho;

    
    private String contactoResidencia;

    
    private String email;

    private String localTrabalho;

    private String cargo;

    @DecimalMin(value = "0")
    private BigDecimal salario;

    @NotNull
    private String grauParentesco;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getContactoPessoal() {
        return contactoPessoal;
    }

    public void setContactoPessoal(String contactoPessoal) {
        this.contactoPessoal = contactoPessoal;
    }

    public String getContactoTrabalho() {
        return contactoTrabalho;
    }

    public void setContactoTrabalho(String contactoTrabalho) {
        this.contactoTrabalho = contactoTrabalho;
    }

    public String getContactoResidencia() {
        return contactoResidencia;
    }

    public void setContactoResidencia(String contactoResidencia) {
        this.contactoResidencia = contactoResidencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EncarregadoEducaoDTO encarregadoEducaoDTO = (EncarregadoEducaoDTO) o;
        if (encarregadoEducaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encarregadoEducaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EncarregadoEducaoDTO{" +
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
