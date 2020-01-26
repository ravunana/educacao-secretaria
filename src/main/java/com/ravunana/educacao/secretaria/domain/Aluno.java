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
import java.util.HashSet;
import java.util.Set;

/**
 * A Aluno.
 */
@Entity
@Table(name = "sec_aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aluno")
public class Aluno implements Serializable {

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

    @Lob
    @Column(name = "fotografia")
    private byte[] fotografia;

    @Column(name = "fotografia_content_type")
    private String fotografiaContentType;

    @NotNull
    @Column(name = "pai", nullable = false)
    private String pai;

    @NotNull
    @Column(name = "mae", nullable = false)
    private String mae;

    @NotNull
    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @NotNull
    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @NotNull
    @Column(name = "naturalidade", nullable = false)
    private String naturalidade;

    @NotNull
    @Column(name = "contacto", nullable = false, unique = true)
    private String contacto;

    
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @NotNull
    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento;

    @NotNull
    @Column(name = "emissao", nullable = false)
    private LocalDate emissao;

    @NotNull
    @Column(name = "validade", nullable = false)
    private LocalDate validade;

    @Column(name = "local_emissao")
    private String localEmissao;

    
    @Column(name = "nif", unique = true)
    private String nif;

    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "municipio", nullable = false)
    private String municipio;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 200)
    @Column(name = "rua", length = 200, nullable = false)
    private String rua;

    @NotNull
    @Size(max = 10)
    @Column(name = "quarteirao", length = 10, nullable = false)
    private String quarteirao;

    @NotNull
    @Size(max = 10)
    @Column(name = "numero_porta", length = 10, nullable = false)
    private String numeroPorta;

    @NotNull
    @Column(name = "faz_educacao_fisica", nullable = false)
    private Boolean fazEducacaoFisica;

    @Column(name = "grupo_sanguinio")
    private String grupoSanguinio;

    @NotNull
    @Column(name = "autoriza_medicamento", nullable = false)
    private Boolean autorizaMedicamento;

    @Min(value = 0)
    @Column(name = "altura")
    private Integer altura;

    @DecimalMin(value = "0")
    @Column(name = "peso")
    private Double peso;

    @Column(name = "nome_medico")
    private String nomeMedico;

    @Column(name = "contacto_medico")
    private String contactoMedico;

    @NotNull
    @Column(name = "desmaio_constante", nullable = false)
    private Boolean desmaioConstante;

    @Column(name = "alergia")
    private String alergia;

    @Column(name = "dificiencia")
    private String dificiencia;

    @NotNull
    @Min(value = 2000)
    @Column(name = "ano_conclusao", nullable = false)
    private Integer anoConclusao;

    @NotNull
    @Column(name = "situacao_anterior", nullable = false)
    private String situacaoAnterior;

    @Column(name = "meio_transporte")
    private String meioTransporte;

    @NotNull
    @Column(name = "escola_anterior", nullable = false)
    private String escolaAnterior;

    @NotNull
    @Column(name = "classe_anterior", nullable = false)
    private Integer classeAnterior;

    @NotNull
    @Column(name = "curso_anterior", nullable = false)
    private String cursoAnterior;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "numero_processo", nullable = false, unique = true)
    private String numeroProcesso;

    @NotNull
    @Column(name = "transferido", nullable = false)
    private Boolean transferido;

    @Lob
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "situacao_atual")
    private String situacaoAtual;

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("alunos")
    private EncarregadoEducao encarregado;

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

    public Aluno nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public Aluno sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public Aluno fotografia(byte[] fotografia) {
        this.fotografia = fotografia;
        return this;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotografiaContentType() {
        return fotografiaContentType;
    }

    public Aluno fotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
        return this;
    }

    public void setFotografiaContentType(String fotografiaContentType) {
        this.fotografiaContentType = fotografiaContentType;
    }

    public String getPai() {
        return pai;
    }

    public Aluno pai(String pai) {
        this.pai = pai;
        return this;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public Aluno mae(String mae) {
        this.mae = mae;
        return this;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public Aluno nascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
        return this;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public Aluno nacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public Aluno naturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
        return this;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getContacto() {
        return contacto;
    }

    public Aluno contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public Aluno email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public Aluno tipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public Aluno numeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public Aluno emissao(LocalDate emissao) {
        this.emissao = emissao;
        return this;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public Aluno validade(LocalDate validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public Aluno localEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
        return this;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getNif() {
        return nif;
    }

    public Aluno nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getProvincia() {
        return provincia;
    }

    public Aluno provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Aluno municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public Aluno bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public Aluno rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public Aluno quarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
        return this;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public Aluno numeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
        return this;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public Boolean isFazEducacaoFisica() {
        return fazEducacaoFisica;
    }

    public Aluno fazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
        return this;
    }

    public void setFazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
    }

    public String getGrupoSanguinio() {
        return grupoSanguinio;
    }

    public Aluno grupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
        return this;
    }

    public void setGrupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
    }

    public Boolean isAutorizaMedicamento() {
        return autorizaMedicamento;
    }

    public Aluno autorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
        return this;
    }

    public void setAutorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
    }

    public Integer getAltura() {
        return altura;
    }

    public Aluno altura(Integer altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public Aluno peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public Aluno nomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
        return this;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getContactoMedico() {
        return contactoMedico;
    }

    public Aluno contactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
        return this;
    }

    public void setContactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
    }

    public Boolean isDesmaioConstante() {
        return desmaioConstante;
    }

    public Aluno desmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
        return this;
    }

    public void setDesmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
    }

    public String getAlergia() {
        return alergia;
    }

    public Aluno alergia(String alergia) {
        this.alergia = alergia;
        return this;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getDificiencia() {
        return dificiencia;
    }

    public Aluno dificiencia(String dificiencia) {
        this.dificiencia = dificiencia;
        return this;
    }

    public void setDificiencia(String dificiencia) {
        this.dificiencia = dificiencia;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public Aluno anoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
        return this;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getSituacaoAnterior() {
        return situacaoAnterior;
    }

    public Aluno situacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
        return this;
    }

    public void setSituacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
    }

    public String getMeioTransporte() {
        return meioTransporte;
    }

    public Aluno meioTransporte(String meioTransporte) {
        this.meioTransporte = meioTransporte;
        return this;
    }

    public void setMeioTransporte(String meioTransporte) {
        this.meioTransporte = meioTransporte;
    }

    public String getEscolaAnterior() {
        return escolaAnterior;
    }

    public Aluno escolaAnterior(String escolaAnterior) {
        this.escolaAnterior = escolaAnterior;
        return this;
    }

    public void setEscolaAnterior(String escolaAnterior) {
        this.escolaAnterior = escolaAnterior;
    }

    public Integer getClasseAnterior() {
        return classeAnterior;
    }

    public Aluno classeAnterior(Integer classeAnterior) {
        this.classeAnterior = classeAnterior;
        return this;
    }

    public void setClasseAnterior(Integer classeAnterior) {
        this.classeAnterior = classeAnterior;
    }

    public String getCursoAnterior() {
        return cursoAnterior;
    }

    public Aluno cursoAnterior(String cursoAnterior) {
        this.cursoAnterior = cursoAnterior;
        return this;
    }

    public void setCursoAnterior(String cursoAnterior) {
        this.cursoAnterior = cursoAnterior;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Aluno data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public Aluno numeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
        return this;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Boolean isTransferido() {
        return transferido;
    }

    public Aluno transferido(Boolean transferido) {
        this.transferido = transferido;
        return this;
    }

    public void setTransferido(Boolean transferido) {
        this.transferido = transferido;
    }

    public String getObservacao() {
        return observacao;
    }

    public Aluno observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSituacaoAtual() {
        return situacaoAtual;
    }

    public Aluno situacaoAtual(String situacaoAtual) {
        this.situacaoAtual = situacaoAtual;
        return this;
    }

    public void setSituacaoAtual(String situacaoAtual) {
        this.situacaoAtual = situacaoAtual;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Aluno matriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
        return this;
    }

    public Aluno addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setAluno(this);
        return this;
    }

    public Aluno removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setAluno(null);
        return this;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public Aluno ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
        return this;
    }

    public Aluno addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setMatricula(this);
        return this;
    }

    public Aluno removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setMatricula(null);
        return this;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public EncarregadoEducao getEncarregado() {
        return encarregado;
    }

    public Aluno encarregado(EncarregadoEducao encarregadoEducao) {
        this.encarregado = encarregadoEducao;
        return this;
    }

    public void setEncarregado(EncarregadoEducao encarregadoEducao) {
        this.encarregado = encarregadoEducao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aluno)) {
            return false;
        }
        return id != null && id.equals(((Aluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", fotografia='" + getFotografia() + "'" +
            ", fotografiaContentType='" + getFotografiaContentType() + "'" +
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
            "}";
    }
}
