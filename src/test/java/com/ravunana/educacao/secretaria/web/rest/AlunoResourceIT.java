package com.ravunana.educacao.secretaria.web.rest;

import com.ravunana.educacao.secretaria.SecretariaApp;
import com.ravunana.educacao.secretaria.config.SecurityBeanOverrideConfiguration;
import com.ravunana.educacao.secretaria.domain.Aluno;
import com.ravunana.educacao.secretaria.domain.EncarregadoEducao;
import com.ravunana.educacao.secretaria.repository.AlunoRepository;
import com.ravunana.educacao.secretaria.repository.search.AlunoSearchRepository;
import com.ravunana.educacao.secretaria.service.AlunoService;
import com.ravunana.educacao.secretaria.service.dto.AlunoDTO;
import com.ravunana.educacao.secretaria.service.mapper.AlunoMapper;
import com.ravunana.educacao.secretaria.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.ravunana.educacao.secretaria.web.rest.TestUtil.sameInstant;
import static com.ravunana.educacao.secretaria.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SecretariaApp.class})
public class AlunoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTOGRAFIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOGRAFIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOGRAFIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOGRAFIA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PAI = "AAAAAAAAAA";
    private static final String UPDATED_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_MAE = "AAAAAAAAAA";
    private static final String UPDATED_MAE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NACIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NATURALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NATURALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOCAL_EMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_EMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTEIRAO = "AAAAAAAAAA";
    private static final String UPDATED_QUARTEIRAO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PORTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PORTA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FAZ_EDUCACAO_FISICA = false;
    private static final Boolean UPDATED_FAZ_EDUCACAO_FISICA = true;

    private static final String DEFAULT_GRUPO_SANGUINIO = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_SANGUINIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTORIZA_MEDICAMENTO = false;
    private static final Boolean UPDATED_AUTORIZA_MEDICAMENTO = true;

    private static final Integer DEFAULT_ALTURA = 0;
    private static final Integer UPDATED_ALTURA = 1;

    private static final Double DEFAULT_PESO = 0D;
    private static final Double UPDATED_PESO = 1D;

    private static final String DEFAULT_NOME_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_MEDICO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESMAIO_CONSTANTE = false;
    private static final Boolean UPDATED_DESMAIO_CONSTANTE = true;

    private static final String DEFAULT_ALERGIA = "AAAAAAAAAA";
    private static final String UPDATED_ALERGIA = "BBBBBBBBBB";

    private static final String DEFAULT_DIFICIENCIA = "AAAAAAAAAA";
    private static final String UPDATED_DIFICIENCIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_CONCLUSAO = 2000;
    private static final Integer UPDATED_ANO_CONCLUSAO = 2001;

    private static final String DEFAULT_SITUACAO_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO_ANTERIOR = "BBBBBBBBBB";

    private static final String DEFAULT_MEIO_TRANSPORTE = "AAAAAAAAAA";
    private static final String UPDATED_MEIO_TRANSPORTE = "BBBBBBBBBB";

    private static final String DEFAULT_ESCOLA_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_ESCOLA_ANTERIOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSE_ANTERIOR = 1;
    private static final Integer UPDATED_CLASSE_ANTERIOR = 2;

    private static final String DEFAULT_CURSO_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_CURSO_ANTERIOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NUMERO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRANSFERIDO = false;
    private static final Boolean UPDATED_TRANSFERIDO = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACAO_ATUAL = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO_ATUAL = "BBBBBBBBBB";

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoService alunoService;

    /**
     * This repository is mocked in the com.ravunana.educacao.secretaria.repository.search test package.
     *
     * @see com.ravunana.educacao.secretaria.repository.search.AlunoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlunoSearchRepository mockAlunoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlunoResource alunoResource = new AlunoResource(alunoService);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(DEFAULT_NOME)
            .sexo(DEFAULT_SEXO)
            .fotografia(DEFAULT_FOTOGRAFIA)
            .fotografiaContentType(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)
            .pai(DEFAULT_PAI)
            .mae(DEFAULT_MAE)
            .nascimento(DEFAULT_NASCIMENTO)
            .nacionalidade(DEFAULT_NACIONALIDADE)
            .naturalidade(DEFAULT_NATURALIDADE)
            .contacto(DEFAULT_CONTACTO)
            .email(DEFAULT_EMAIL)
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .emissao(DEFAULT_EMISSAO)
            .validade(DEFAULT_VALIDADE)
            .localEmissao(DEFAULT_LOCAL_EMISSAO)
            .nif(DEFAULT_NIF)
            .provincia(DEFAULT_PROVINCIA)
            .municipio(DEFAULT_MUNICIPIO)
            .bairro(DEFAULT_BAIRRO)
            .rua(DEFAULT_RUA)
            .quarteirao(DEFAULT_QUARTEIRAO)
            .numeroPorta(DEFAULT_NUMERO_PORTA)
            .fazEducacaoFisica(DEFAULT_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(DEFAULT_GRUPO_SANGUINIO)
            .autorizaMedicamento(DEFAULT_AUTORIZA_MEDICAMENTO)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO)
            .nomeMedico(DEFAULT_NOME_MEDICO)
            .contactoMedico(DEFAULT_CONTACTO_MEDICO)
            .desmaioConstante(DEFAULT_DESMAIO_CONSTANTE)
            .alergia(DEFAULT_ALERGIA)
            .dificiencia(DEFAULT_DIFICIENCIA)
            .anoConclusao(DEFAULT_ANO_CONCLUSAO)
            .situacaoAnterior(DEFAULT_SITUACAO_ANTERIOR)
            .meioTransporte(DEFAULT_MEIO_TRANSPORTE)
            .escolaAnterior(DEFAULT_ESCOLA_ANTERIOR)
            .classeAnterior(DEFAULT_CLASSE_ANTERIOR)
            .cursoAnterior(DEFAULT_CURSO_ANTERIOR)
            .data(DEFAULT_DATA)
            .numeroProcesso(DEFAULT_NUMERO_PROCESSO)
            .transferido(DEFAULT_TRANSFERIDO)
            .observacao(DEFAULT_OBSERVACAO)
            .situacaoAtual(DEFAULT_SITUACAO_ATUAL);
        // Add required entity
        EncarregadoEducao encarregadoEducao;
        if (TestUtil.findAll(em, EncarregadoEducao.class).isEmpty()) {
            encarregadoEducao = EncarregadoEducaoResourceIT.createEntity(em);
            em.persist(encarregadoEducao);
            em.flush();
        } else {
            encarregadoEducao = TestUtil.findAll(em, EncarregadoEducao.class).get(0);
        }
        aluno.setEncarregado(encarregadoEducao);
        return aluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .nascimento(UPDATED_NASCIMENTO)
            .nacionalidade(UPDATED_NACIONALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .contacto(UPDATED_CONTACTO)
            .email(UPDATED_EMAIL)
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .emissao(UPDATED_EMISSAO)
            .validade(UPDATED_VALIDADE)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .nif(UPDATED_NIF)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .fazEducacaoFisica(UPDATED_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(UPDATED_GRUPO_SANGUINIO)
            .autorizaMedicamento(UPDATED_AUTORIZA_MEDICAMENTO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .contactoMedico(UPDATED_CONTACTO_MEDICO)
            .desmaioConstante(UPDATED_DESMAIO_CONSTANTE)
            .alergia(UPDATED_ALERGIA)
            .dificiencia(UPDATED_DIFICIENCIA)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .situacaoAnterior(UPDATED_SITUACAO_ANTERIOR)
            .meioTransporte(UPDATED_MEIO_TRANSPORTE)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .classeAnterior(UPDATED_CLASSE_ANTERIOR)
            .cursoAnterior(UPDATED_CURSO_ANTERIOR)
            .data(UPDATED_DATA)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .transferido(UPDATED_TRANSFERIDO)
            .observacao(UPDATED_OBSERVACAO)
            .situacaoAtual(UPDATED_SITUACAO_ATUAL);
        // Add required entity
        EncarregadoEducao encarregadoEducao;
        if (TestUtil.findAll(em, EncarregadoEducao.class).isEmpty()) {
            encarregadoEducao = EncarregadoEducaoResourceIT.createUpdatedEntity(em);
            em.persist(encarregadoEducao);
            em.flush();
        } else {
            encarregadoEducao = TestUtil.findAll(em, EncarregadoEducao.class).get(0);
        }
        aluno.setEncarregado(encarregadoEducao);
        return aluno;
    }

    @BeforeEach
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAluno.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAluno.getFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testAluno.getFotografiaContentType()).isEqualTo(DEFAULT_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testAluno.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testAluno.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testAluno.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testAluno.getNacionalidade()).isEqualTo(DEFAULT_NACIONALIDADE);
        assertThat(testAluno.getNaturalidade()).isEqualTo(DEFAULT_NATURALIDADE);
        assertThat(testAluno.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testAluno.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAluno.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testAluno.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testAluno.getEmissao()).isEqualTo(DEFAULT_EMISSAO);
        assertThat(testAluno.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testAluno.getLocalEmissao()).isEqualTo(DEFAULT_LOCAL_EMISSAO);
        assertThat(testAluno.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testAluno.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testAluno.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testAluno.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testAluno.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testAluno.getQuarteirao()).isEqualTo(DEFAULT_QUARTEIRAO);
        assertThat(testAluno.getNumeroPorta()).isEqualTo(DEFAULT_NUMERO_PORTA);
        assertThat(testAluno.isFazEducacaoFisica()).isEqualTo(DEFAULT_FAZ_EDUCACAO_FISICA);
        assertThat(testAluno.getGrupoSanguinio()).isEqualTo(DEFAULT_GRUPO_SANGUINIO);
        assertThat(testAluno.isAutorizaMedicamento()).isEqualTo(DEFAULT_AUTORIZA_MEDICAMENTO);
        assertThat(testAluno.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testAluno.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testAluno.getNomeMedico()).isEqualTo(DEFAULT_NOME_MEDICO);
        assertThat(testAluno.getContactoMedico()).isEqualTo(DEFAULT_CONTACTO_MEDICO);
        assertThat(testAluno.isDesmaioConstante()).isEqualTo(DEFAULT_DESMAIO_CONSTANTE);
        assertThat(testAluno.getAlergia()).isEqualTo(DEFAULT_ALERGIA);
        assertThat(testAluno.getDificiencia()).isEqualTo(DEFAULT_DIFICIENCIA);
        assertThat(testAluno.getAnoConclusao()).isEqualTo(DEFAULT_ANO_CONCLUSAO);
        assertThat(testAluno.getSituacaoAnterior()).isEqualTo(DEFAULT_SITUACAO_ANTERIOR);
        assertThat(testAluno.getMeioTransporte()).isEqualTo(DEFAULT_MEIO_TRANSPORTE);
        assertThat(testAluno.getEscolaAnterior()).isEqualTo(DEFAULT_ESCOLA_ANTERIOR);
        assertThat(testAluno.getClasseAnterior()).isEqualTo(DEFAULT_CLASSE_ANTERIOR);
        assertThat(testAluno.getCursoAnterior()).isEqualTo(DEFAULT_CURSO_ANTERIOR);
        assertThat(testAluno.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAluno.getNumeroProcesso()).isEqualTo(DEFAULT_NUMERO_PROCESSO);
        assertThat(testAluno.isTransferido()).isEqualTo(DEFAULT_TRANSFERIDO);
        assertThat(testAluno.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testAluno.getSituacaoAtual()).isEqualTo(DEFAULT_SITUACAO_ATUAL);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void createAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno with an existing ID
        aluno.setId(1L);
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNome(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setSexo(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setPai(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setMae(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNascimento(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNacionalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNacionalidade(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaturalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNaturalidade(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setContacto(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setTipoDocumento(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNumeroDocumento(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setEmissao(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setValidade(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setProvincia(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setMunicipio(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setBairro(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setRua(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarteiraoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setQuarteirao(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroPortaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNumeroPorta(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFazEducacaoFisicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setFazEducacaoFisica(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorizaMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setAutorizaMedicamento(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesmaioConstanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setDesmaioConstante(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoConclusaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setAnoConclusao(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setSituacaoAnterior(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEscolaAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setEscolaAnterior(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClasseAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setClasseAnterior(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCursoAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setCursoAnterior(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setData(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNumeroProcesso(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransferidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setTransferido(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)))
            .andExpect(jsonPath("$.[*].fazEducacaoFisica").value(hasItem(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].grupoSanguinio").value(hasItem(DEFAULT_GRUPO_SANGUINIO)))
            .andExpect(jsonPath("$.[*].autorizaMedicamento").value(hasItem(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].contactoMedico").value(hasItem(DEFAULT_CONTACTO_MEDICO)))
            .andExpect(jsonPath("$.[*].desmaioConstante").value(hasItem(DEFAULT_DESMAIO_CONSTANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].alergia").value(hasItem(DEFAULT_ALERGIA)))
            .andExpect(jsonPath("$.[*].dificiencia").value(hasItem(DEFAULT_DIFICIENCIA)))
            .andExpect(jsonPath("$.[*].anoConclusao").value(hasItem(DEFAULT_ANO_CONCLUSAO)))
            .andExpect(jsonPath("$.[*].situacaoAnterior").value(hasItem(DEFAULT_SITUACAO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].meioTransporte").value(hasItem(DEFAULT_MEIO_TRANSPORTE)))
            .andExpect(jsonPath("$.[*].escolaAnterior").value(hasItem(DEFAULT_ESCOLA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].classeAnterior").value(hasItem(DEFAULT_CLASSE_ANTERIOR)))
            .andExpect(jsonPath("$.[*].cursoAnterior").value(hasItem(DEFAULT_CURSO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].transferido").value(hasItem(DEFAULT_TRANSFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].situacaoAtual").value(hasItem(DEFAULT_SITUACAO_ATUAL)));
    }
    
    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.fotografiaContentType").value(DEFAULT_FOTOGRAFIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotografia").value(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA)))
            .andExpect(jsonPath("$.pai").value(DEFAULT_PAI))
            .andExpect(jsonPath("$.mae").value(DEFAULT_MAE))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE))
            .andExpect(jsonPath("$.naturalidade").value(DEFAULT_NATURALIDADE))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.emissao").value(DEFAULT_EMISSAO.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()))
            .andExpect(jsonPath("$.localEmissao").value(DEFAULT_LOCAL_EMISSAO))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.quarteirao").value(DEFAULT_QUARTEIRAO))
            .andExpect(jsonPath("$.numeroPorta").value(DEFAULT_NUMERO_PORTA))
            .andExpect(jsonPath("$.fazEducacaoFisica").value(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue()))
            .andExpect(jsonPath("$.grupoSanguinio").value(DEFAULT_GRUPO_SANGUINIO))
            .andExpect(jsonPath("$.autorizaMedicamento").value(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.nomeMedico").value(DEFAULT_NOME_MEDICO))
            .andExpect(jsonPath("$.contactoMedico").value(DEFAULT_CONTACTO_MEDICO))
            .andExpect(jsonPath("$.desmaioConstante").value(DEFAULT_DESMAIO_CONSTANTE.booleanValue()))
            .andExpect(jsonPath("$.alergia").value(DEFAULT_ALERGIA))
            .andExpect(jsonPath("$.dificiencia").value(DEFAULT_DIFICIENCIA))
            .andExpect(jsonPath("$.anoConclusao").value(DEFAULT_ANO_CONCLUSAO))
            .andExpect(jsonPath("$.situacaoAnterior").value(DEFAULT_SITUACAO_ANTERIOR))
            .andExpect(jsonPath("$.meioTransporte").value(DEFAULT_MEIO_TRANSPORTE))
            .andExpect(jsonPath("$.escolaAnterior").value(DEFAULT_ESCOLA_ANTERIOR))
            .andExpect(jsonPath("$.classeAnterior").value(DEFAULT_CLASSE_ANTERIOR))
            .andExpect(jsonPath("$.cursoAnterior").value(DEFAULT_CURSO_ANTERIOR))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.numeroProcesso").value(DEFAULT_NUMERO_PROCESSO))
            .andExpect(jsonPath("$.transferido").value(DEFAULT_TRANSFERIDO.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.situacaoAtual").value(DEFAULT_SITUACAO_ATUAL));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).get();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .fotografia(UPDATED_FOTOGRAFIA)
            .fotografiaContentType(UPDATED_FOTOGRAFIA_CONTENT_TYPE)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .nascimento(UPDATED_NASCIMENTO)
            .nacionalidade(UPDATED_NACIONALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .contacto(UPDATED_CONTACTO)
            .email(UPDATED_EMAIL)
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .emissao(UPDATED_EMISSAO)
            .validade(UPDATED_VALIDADE)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .nif(UPDATED_NIF)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .fazEducacaoFisica(UPDATED_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(UPDATED_GRUPO_SANGUINIO)
            .autorizaMedicamento(UPDATED_AUTORIZA_MEDICAMENTO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .contactoMedico(UPDATED_CONTACTO_MEDICO)
            .desmaioConstante(UPDATED_DESMAIO_CONSTANTE)
            .alergia(UPDATED_ALERGIA)
            .dificiencia(UPDATED_DIFICIENCIA)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .situacaoAnterior(UPDATED_SITUACAO_ANTERIOR)
            .meioTransporte(UPDATED_MEIO_TRANSPORTE)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .classeAnterior(UPDATED_CLASSE_ANTERIOR)
            .cursoAnterior(UPDATED_CURSO_ANTERIOR)
            .data(UPDATED_DATA)
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .transferido(UPDATED_TRANSFERIDO)
            .observacao(UPDATED_OBSERVACAO)
            .situacaoAtual(UPDATED_SITUACAO_ATUAL);
        AlunoDTO alunoDTO = alunoMapper.toDto(updatedAluno);

        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAluno.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAluno.getFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testAluno.getFotografiaContentType()).isEqualTo(UPDATED_FOTOGRAFIA_CONTENT_TYPE);
        assertThat(testAluno.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testAluno.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testAluno.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testAluno.getNacionalidade()).isEqualTo(UPDATED_NACIONALIDADE);
        assertThat(testAluno.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testAluno.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testAluno.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAluno.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testAluno.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testAluno.getEmissao()).isEqualTo(UPDATED_EMISSAO);
        assertThat(testAluno.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testAluno.getLocalEmissao()).isEqualTo(UPDATED_LOCAL_EMISSAO);
        assertThat(testAluno.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testAluno.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testAluno.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testAluno.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testAluno.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testAluno.getQuarteirao()).isEqualTo(UPDATED_QUARTEIRAO);
        assertThat(testAluno.getNumeroPorta()).isEqualTo(UPDATED_NUMERO_PORTA);
        assertThat(testAluno.isFazEducacaoFisica()).isEqualTo(UPDATED_FAZ_EDUCACAO_FISICA);
        assertThat(testAluno.getGrupoSanguinio()).isEqualTo(UPDATED_GRUPO_SANGUINIO);
        assertThat(testAluno.isAutorizaMedicamento()).isEqualTo(UPDATED_AUTORIZA_MEDICAMENTO);
        assertThat(testAluno.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testAluno.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testAluno.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testAluno.getContactoMedico()).isEqualTo(UPDATED_CONTACTO_MEDICO);
        assertThat(testAluno.isDesmaioConstante()).isEqualTo(UPDATED_DESMAIO_CONSTANTE);
        assertThat(testAluno.getAlergia()).isEqualTo(UPDATED_ALERGIA);
        assertThat(testAluno.getDificiencia()).isEqualTo(UPDATED_DIFICIENCIA);
        assertThat(testAluno.getAnoConclusao()).isEqualTo(UPDATED_ANO_CONCLUSAO);
        assertThat(testAluno.getSituacaoAnterior()).isEqualTo(UPDATED_SITUACAO_ANTERIOR);
        assertThat(testAluno.getMeioTransporte()).isEqualTo(UPDATED_MEIO_TRANSPORTE);
        assertThat(testAluno.getEscolaAnterior()).isEqualTo(UPDATED_ESCOLA_ANTERIOR);
        assertThat(testAluno.getClasseAnterior()).isEqualTo(UPDATED_CLASSE_ANTERIOR);
        assertThat(testAluno.getCursoAnterior()).isEqualTo(UPDATED_CURSO_ANTERIOR);
        assertThat(testAluno.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAluno.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testAluno.isTransferido()).isEqualTo(UPDATED_TRANSFERIDO);
        assertThat(testAluno.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testAluno.getSituacaoAtual()).isEqualTo(UPDATED_SITUACAO_ATUAL);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void updateNonExistingAluno() throws Exception {
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Delete the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).deleteById(aluno.getId());
    }

    @Test
    @Transactional
    public void searchAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        when(mockAlunoSearchRepository.search(queryStringQuery("id:" + aluno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(aluno), PageRequest.of(0, 1), 1));
        // Search the aluno
        restAlunoMockMvc.perform(get("/api/_search/alunos?query=id:" + aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fotografiaContentType").value(hasItem(DEFAULT_FOTOGRAFIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIA))))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)))
            .andExpect(jsonPath("$.[*].fazEducacaoFisica").value(hasItem(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].grupoSanguinio").value(hasItem(DEFAULT_GRUPO_SANGUINIO)))
            .andExpect(jsonPath("$.[*].autorizaMedicamento").value(hasItem(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].contactoMedico").value(hasItem(DEFAULT_CONTACTO_MEDICO)))
            .andExpect(jsonPath("$.[*].desmaioConstante").value(hasItem(DEFAULT_DESMAIO_CONSTANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].alergia").value(hasItem(DEFAULT_ALERGIA)))
            .andExpect(jsonPath("$.[*].dificiencia").value(hasItem(DEFAULT_DIFICIENCIA)))
            .andExpect(jsonPath("$.[*].anoConclusao").value(hasItem(DEFAULT_ANO_CONCLUSAO)))
            .andExpect(jsonPath("$.[*].situacaoAnterior").value(hasItem(DEFAULT_SITUACAO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].meioTransporte").value(hasItem(DEFAULT_MEIO_TRANSPORTE)))
            .andExpect(jsonPath("$.[*].escolaAnterior").value(hasItem(DEFAULT_ESCOLA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].classeAnterior").value(hasItem(DEFAULT_CLASSE_ANTERIOR)))
            .andExpect(jsonPath("$.[*].cursoAnterior").value(hasItem(DEFAULT_CURSO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].transferido").value(hasItem(DEFAULT_TRANSFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].situacaoAtual").value(hasItem(DEFAULT_SITUACAO_ATUAL)));
    }
}
