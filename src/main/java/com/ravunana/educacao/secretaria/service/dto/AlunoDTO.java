package com.ravunana.educacao.secretaria.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.educacao.secretaria.domain.Aluno} entity.
 */
public class AlunoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sexo;

    @Lob
    private byte[] fotografia;

    private String fotografiaContentType;
    @NotNull
    private String pai;

    @NotNull
    private String mae;

    @NotNull
    private LocalDate nascimento;

    @NotNull
    private String nacionalidade;

    @NotNull
    private String naturalidade;

    @NotNull
    private String contacto;

    
    private String email;

    @NotNull
    private String tipoDocumento;

    @NotNull
    private String numeroDocumento;

    @NotNull
    private LocalDate emissao;

    @NotNull
    private LocalDate validade;

    private String localEmissao;

    
    private String nif;

    @NotNull
    private String provincia;

    @NotNull
    private String municipio;

    @NotNull
    private String bairro;

    @NotNull
    @Size(max = 200)
    private String rua;

    @NotNull
    @Size(max = 10)
    private String quarteirao;

    @NotNull
    @Size(max = 10)
    private String numeroPorta;

    @NotNull
    private Boolean fazEducacaoFisica;

    private String grupoSanguinio;

    @NotNull
    private Boolean autorizaMedicamento;

    @Min(value = 0)
    private Integer altura;

    @DecimalMin(value = "0")
    private Double peso;

    private String nomeMedico;

    private String contactoMedico;

    @NotNull
    private Boolean desmaioConstante;

    private String alergia;

    private String dificiencia;

    @NotNull
    @Min(value = 2000)
    private Integer anoConclusao;

    @NotNull
    private String situacaoAnterior;

    private String meioTransporte;

    @NotNull
    private String escolaAnterior;

    @NotNull
    private Integer classeAnterior;

    @NotNull
    private String cursoAnterior;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private String numeroProcesso;

    @NotNull
    private Boolean transferido;

    @Lob
    private String observacao;

    private String situacaoAtual;


    private Long encarregadoId;

    private String encarregadoNome;

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

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return fotografiaContentType;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public Boolean isFazEducacaoFisica() {
        return fazEducacaoFisica;
    }

    public void setFazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
    }

    public String getGrupoSanguinio() {
        return grupoSanguinio;
    }

    public void setGrupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
    }

    public Boolean isAutorizaMedicamento() {
        return autorizaMedicamento;
    }

    public void setAutorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getContactoMedico() {
        return contactoMedico;
    }

    public void setContactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
    }

    public Boolean isDesmaioConstante() {
        return desmaioConstante;
    }

    public void setDesmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDificiencia() {
        return dificiencia;
    }

    public void setDificiencia(String dificiencia) {
        this.dificiencia = dificiencia;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getSituacaoAnterior() {
        return situacaoAnterior;
    }

    public void setSituacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
    }

    public String getMeioTransporte() {
        return meioTransporte;
    }

    public void setMeioTransporte(String meioTransporte) {
        this.meioTransporte = meioTransporte;
    }

    public String getEscolaAnterior() {
        return escolaAnterior;
    }

    public void setEscolaAnterior(String escolaAnterior) {
        this.escolaAnterior = escolaAnterior;
    }

    public Integer getClasseAnterior() {
        return classeAnterior;
    }

    public void setClasseAnterior(Integer classeAnterior) {
        this.classeAnterior = classeAnterior;
    }

    public String getCursoAnterior() {
        return cursoAnterior;
    }

    public void setCursoAnterior(String cursoAnterior) {
        this.cursoAnterior = cursoAnterior;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Boolean isTransferido() {
        return transferido;
    }

    public void setTransferido(Boolean transferido) {
        this.transferido = transferido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSituacaoAtual() {
        return situacaoAtual;
    }

    public void setSituacaoAtual(String situacaoAtual) {
        this.situacaoAtual = situacaoAtual;
    }

    public Long getEncarregadoId() {
        return encarregadoId;
    }

    public void setEncarregadoId(Long encarregadoEducaoId) {
        this.encarregadoId = encarregadoEducaoId;
    }

    public String getEncarregadoNome() {
        return encarregadoNome;
    }

    public void setEncarregadoNome(String encarregadoEducaoNome) {
        this.encarregadoNome = encarregadoEducaoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlunoDTO alunoDTO = (AlunoDTO) o;
        if (alunoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alunoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlunoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", fotografia='" + getFotografia() + "'" +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", nacionalidade='" + getNacionalidade() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", email='" + getEmail() + "'" +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", emissao='" + getEmissao() + "'" +
            ", validade='" + getValidade() + "'" +
            ", localEmissao='" + getLocalEmissao() + "'" +
            ", nif='" + getNif() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            ", fazEducacaoFisica='" + isFazEducacaoFisica() + "'" +
            ", grupoSanguinio='" + getGrupoSanguinio() + "'" +
            ", autorizaMedicamento='" + isAutorizaMedicamento() + "'" +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", nomeMedico='" + getNomeMedico() + "'" +
            ", contactoMedico='" + getContactoMedico() + "'" +
            ", desmaioConstante='" + isDesmaioConstante() + "'" +
            ", alergia='" + getAlergia() + "'" +
            ", dificiencia='" + getDificiencia() + "'" +
            ", anoConclusao=" + getAnoConclusao() +
            ", situacaoAnterior='" + getSituacaoAnterior() + "'" +
            ", meioTransporte='" + getMeioTransporte() + "'" +
            ", escolaAnterior='" + getEscolaAnterior() + "'" +
            ", classeAnterior=" + getClasseAnterior() +
            ", cursoAnterior='" + getCursoAnterior() + "'" +
            ", data='" + getData() + "'" +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", transferido='" + isTransferido() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", situacaoAtual='" + getSituacaoAtual() + "'" +
            ", encarregadoId=" + getEncarregadoId() +
            ", encarregadoNome='" + getEncarregadoNome() + "'" +
            "}";
    }
}
