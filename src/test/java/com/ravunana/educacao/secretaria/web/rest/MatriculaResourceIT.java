package com.ravunana.educacao.secretaria.web.rest;

import com.ravunana.educacao.secretaria.SecretariaApp;
import com.ravunana.educacao.secretaria.config.SecurityBeanOverrideConfiguration;
import com.ravunana.educacao.secretaria.domain.Matricula;
import com.ravunana.educacao.secretaria.domain.Aluno;
import com.ravunana.educacao.secretaria.domain.CategoriaAluno;
import com.ravunana.educacao.secretaria.repository.MatriculaRepository;
import com.ravunana.educacao.secretaria.repository.search.MatriculaSearchRepository;
import com.ravunana.educacao.secretaria.service.MatriculaService;
import com.ravunana.educacao.secretaria.service.dto.MatriculaDTO;
import com.ravunana.educacao.secretaria.service.mapper.MatriculaMapper;
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
 * Integration tests for the {@link MatriculaResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SecretariaApp.class})
public class MatriculaResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NUMERO_CHAMADA = 1;
    private static final Integer UPDATED_NUMERO_CHAMADA = 2;

    private static final Integer DEFAULT_ANO_LECTIVO = 1;
    private static final Integer UPDATED_ANO_LECTIVO = 2;

    private static final Boolean DEFAULT_FOTOGRAFIA = false;
    private static final Boolean UPDATED_FOTOGRAFIA = true;

    private static final Boolean DEFAULT_CERTIFICADO = false;
    private static final Boolean UPDATED_CERTIFICADO = true;

    private static final Boolean DEFAULT_DOCUMENTO_IDENTIFICACAO = false;
    private static final Boolean UPDATED_DOCUMENTO_IDENTIFICACAO = true;

    private static final Boolean DEFAULT_RESENCIAMENTO_MILITAR = false;
    private static final Boolean UPDATED_RESENCIAMENTO_MILITAR = true;

    private static final Boolean DEFAULT_DOCUMENTO_SAUDE = false;
    private static final Boolean UPDATED_DOCUMENTO_SAUDE = true;

    private static final Boolean DEFAULT_FICHA_TRANSFERENCIA = false;
    private static final Boolean UPDATED_FICHA_TRANSFERENCIA = true;

    private static final Boolean DEFAULT_HISTORICO_ESCOLAR = false;
    private static final Boolean UPDATED_HISTORICO_ESCOLAR = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIRMACAO = false;
    private static final Boolean UPDATED_CONFIRMACAO = true;

    private static final String DEFAULT_PERIODO_LECTIVO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_LECTIVO = "BBBBBBBBBB";

    private static final String DEFAULT_TURMA = "AAAAAAAAAA";
    private static final String UPDATED_TURMA = "BBBBBBBBBB";

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String DEFAULT_TURNO = "AAAAAAAAAA";
    private static final String UPDATED_TURNO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALA = 1;
    private static final Integer UPDATED_SALA = 2;

    private static final Integer DEFAULT_CLASSE = 1;
    private static final Integer UPDATED_CLASSE = 2;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private MatriculaMapper matriculaMapper;

    @Autowired
    private MatriculaService matriculaService;

    /**
     * This repository is mocked in the com.ravunana.educacao.secretaria.repository.search test package.
     *
     * @see com.ravunana.educacao.secretaria.repository.search.MatriculaSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatriculaSearchRepository mockMatriculaSearchRepository;

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

    private MockMvc restMatriculaMockMvc;

    private Matricula matricula;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatriculaResource matriculaResource = new MatriculaResource(matriculaService);
        this.restMatriculaMockMvc = MockMvcBuilders.standaloneSetup(matriculaResource)
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
    public static Matricula createEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .data(DEFAULT_DATA)
            .numeroChamada(DEFAULT_NUMERO_CHAMADA)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .fotografia(DEFAULT_FOTOGRAFIA)
            .certificado(DEFAULT_CERTIFICADO)
            .documentoIdentificacao(DEFAULT_DOCUMENTO_IDENTIFICACAO)
            .resenciamentoMilitar(DEFAULT_RESENCIAMENTO_MILITAR)
            .documentoSaude(DEFAULT_DOCUMENTO_SAUDE)
            .fichaTransferencia(DEFAULT_FICHA_TRANSFERENCIA)
            .historicoEscolar(DEFAULT_HISTORICO_ESCOLAR)
            .observacao(DEFAULT_OBSERVACAO)
            .confirmacao(DEFAULT_CONFIRMACAO)
            .periodoLectivo(DEFAULT_PERIODO_LECTIVO)
            .turma(DEFAULT_TURMA)
            .curso(DEFAULT_CURSO)
            .turno(DEFAULT_TURNO)
            .sala(DEFAULT_SALA)
            .classe(DEFAULT_CLASSE);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        matricula.setAluno(aluno);
        // Add required entity
        CategoriaAluno categoriaAluno;
        if (TestUtil.findAll(em, CategoriaAluno.class).isEmpty()) {
            categoriaAluno = CategoriaAlunoResourceIT.createEntity(em);
            em.persist(categoriaAluno);
            em.flush();
        } else {
            categoriaAluno = TestUtil.findAll(em, CategoriaAluno.class).get(0);
        }
        matricula.setCategoria(categoriaAluno);
        return matricula;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matricula createUpdatedEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .data(UPDATED_DATA)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .fotografia(UPDATED_FOTOGRAFIA)
            .certificado(UPDATED_CERTIFICADO)
            .documentoIdentificacao(UPDATED_DOCUMENTO_IDENTIFICACAO)
            .resenciamentoMilitar(UPDATED_RESENCIAMENTO_MILITAR)
            .documentoSaude(UPDATED_DOCUMENTO_SAUDE)
            .fichaTransferencia(UPDATED_FICHA_TRANSFERENCIA)
            .historicoEscolar(UPDATED_HISTORICO_ESCOLAR)
            .observacao(UPDATED_OBSERVACAO)
            .confirmacao(UPDATED_CONFIRMACAO)
            .periodoLectivo(UPDATED_PERIODO_LECTIVO)
            .turma(UPDATED_TURMA)
            .curso(UPDATED_CURSO)
            .turno(UPDATED_TURNO)
            .sala(UPDATED_SALA)
            .classe(UPDATED_CLASSE);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        matricula.setAluno(aluno);
        // Add required entity
        CategoriaAluno categoriaAluno;
        if (TestUtil.findAll(em, CategoriaAluno.class).isEmpty()) {
            categoriaAluno = CategoriaAlunoResourceIT.createUpdatedEntity(em);
            em.persist(categoriaAluno);
            em.flush();
        } else {
            categoriaAluno = TestUtil.findAll(em, CategoriaAluno.class).get(0);
        }
        matricula.setCategoria(categoriaAluno);
        return matricula;
    }

    @BeforeEach
    public void initTest() {
        matricula = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatricula() throws Exception {
        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);
        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isCreated());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate + 1);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(DEFAULT_NUMERO_CHAMADA);
        assertThat(testMatricula.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testMatricula.isFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testMatricula.isCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testMatricula.isDocumentoIdentificacao()).isEqualTo(DEFAULT_DOCUMENTO_IDENTIFICACAO);
        assertThat(testMatricula.isResenciamentoMilitar()).isEqualTo(DEFAULT_RESENCIAMENTO_MILITAR);
        assertThat(testMatricula.isDocumentoSaude()).isEqualTo(DEFAULT_DOCUMENTO_SAUDE);
        assertThat(testMatricula.isFichaTransferencia()).isEqualTo(DEFAULT_FICHA_TRANSFERENCIA);
        assertThat(testMatricula.isHistoricoEscolar()).isEqualTo(DEFAULT_HISTORICO_ESCOLAR);
        assertThat(testMatricula.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testMatricula.isConfirmacao()).isEqualTo(DEFAULT_CONFIRMACAO);
        assertThat(testMatricula.getPeriodoLectivo()).isEqualTo(DEFAULT_PERIODO_LECTIVO);
        assertThat(testMatricula.getTurma()).isEqualTo(DEFAULT_TURMA);
        assertThat(testMatricula.getCurso()).isEqualTo(DEFAULT_CURSO);
        assertThat(testMatricula.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testMatricula.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testMatricula.getClasse()).isEqualTo(DEFAULT_CLASSE);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).save(testMatricula);
    }

    @Test
    @Transactional
    public void createMatriculaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();

        // Create the Matricula with an existing ID
        matricula.setId(1L);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(0)).save(matricula);
    }


    @Test
    @Transactional
    public void checkNumeroChamadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setNumeroChamada(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setAnoLectivo(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFotografiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setFotografia(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertificadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setCertificado(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentoIdentificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setDocumentoIdentificacao(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfirmacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setConfirmacao(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setPeriodoLectivo(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setTurma(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setCurso(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setTurno(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setSala(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setClasse(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatriculas() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList
        restMatriculaMockMvc.perform(get("/api/matriculas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numeroChamada").value(hasItem(DEFAULT_NUMERO_CHAMADA)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(DEFAULT_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].certificado").value(hasItem(DEFAULT_CERTIFICADO.booleanValue())))
            .andExpect(jsonPath("$.[*].documentoIdentificacao").value(hasItem(DEFAULT_DOCUMENTO_IDENTIFICACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].resenciamentoMilitar").value(hasItem(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue())))
            .andExpect(jsonPath("$.[*].documentoSaude").value(hasItem(DEFAULT_DOCUMENTO_SAUDE.booleanValue())))
            .andExpect(jsonPath("$.[*].fichaTransferencia").value(hasItem(DEFAULT_FICHA_TRANSFERENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].historicoEscolar").value(hasItem(DEFAULT_HISTORICO_ESCOLAR.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].confirmacao").value(hasItem(DEFAULT_CONFIRMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)));
    }
    
    @Test
    @Transactional
    public void getMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get the matricula
        restMatriculaMockMvc.perform(get("/api/matriculas/{id}", matricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matricula.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.numeroChamada").value(DEFAULT_NUMERO_CHAMADA))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO))
            .andExpect(jsonPath("$.fotografia").value(DEFAULT_FOTOGRAFIA.booleanValue()))
            .andExpect(jsonPath("$.certificado").value(DEFAULT_CERTIFICADO.booleanValue()))
            .andExpect(jsonPath("$.documentoIdentificacao").value(DEFAULT_DOCUMENTO_IDENTIFICACAO.booleanValue()))
            .andExpect(jsonPath("$.resenciamentoMilitar").value(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue()))
            .andExpect(jsonPath("$.documentoSaude").value(DEFAULT_DOCUMENTO_SAUDE.booleanValue()))
            .andExpect(jsonPath("$.fichaTransferencia").value(DEFAULT_FICHA_TRANSFERENCIA.booleanValue()))
            .andExpect(jsonPath("$.historicoEscolar").value(DEFAULT_HISTORICO_ESCOLAR.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.confirmacao").value(DEFAULT_CONFIRMACAO.booleanValue()))
            .andExpect(jsonPath("$.periodoLectivo").value(DEFAULT_PERIODO_LECTIVO))
            .andExpect(jsonPath("$.turma").value(DEFAULT_TURMA))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE));
    }

    @Test
    @Transactional
    public void getNonExistingMatricula() throws Exception {
        // Get the matricula
        restMatriculaMockMvc.perform(get("/api/matriculas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Update the matricula
        Matricula updatedMatricula = matriculaRepository.findById(matricula.getId()).get();
        // Disconnect from session so that the updates on updatedMatricula are not directly saved in db
        em.detach(updatedMatricula);
        updatedMatricula
            .data(UPDATED_DATA)
            .numeroChamada(UPDATED_NUMERO_CHAMADA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .fotografia(UPDATED_FOTOGRAFIA)
            .certificado(UPDATED_CERTIFICADO)
            .documentoIdentificacao(UPDATED_DOCUMENTO_IDENTIFICACAO)
            .resenciamentoMilitar(UPDATED_RESENCIAMENTO_MILITAR)
            .documentoSaude(UPDATED_DOCUMENTO_SAUDE)
            .fichaTransferencia(UPDATED_FICHA_TRANSFERENCIA)
            .historicoEscolar(UPDATED_HISTORICO_ESCOLAR)
            .observacao(UPDATED_OBSERVACAO)
            .confirmacao(UPDATED_CONFIRMACAO)
            .periodoLectivo(UPDATED_PERIODO_LECTIVO)
            .turma(UPDATED_TURMA)
            .curso(UPDATED_CURSO)
            .turno(UPDATED_TURNO)
            .sala(UPDATED_SALA)
            .classe(UPDATED_CLASSE);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(updatedMatricula);

        restMatriculaMockMvc.perform(put("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isOk());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMatricula.getNumeroChamada()).isEqualTo(UPDATED_NUMERO_CHAMADA);
        assertThat(testMatricula.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testMatricula.isFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testMatricula.isCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testMatricula.isDocumentoIdentificacao()).isEqualTo(UPDATED_DOCUMENTO_IDENTIFICACAO);
        assertThat(testMatricula.isResenciamentoMilitar()).isEqualTo(UPDATED_RESENCIAMENTO_MILITAR);
        assertThat(testMatricula.isDocumentoSaude()).isEqualTo(UPDATED_DOCUMENTO_SAUDE);
        assertThat(testMatricula.isFichaTransferencia()).isEqualTo(UPDATED_FICHA_TRANSFERENCIA);
        assertThat(testMatricula.isHistoricoEscolar()).isEqualTo(UPDATED_HISTORICO_ESCOLAR);
        assertThat(testMatricula.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testMatricula.isConfirmacao()).isEqualTo(UPDATED_CONFIRMACAO);
        assertThat(testMatricula.getPeriodoLectivo()).isEqualTo(UPDATED_PERIODO_LECTIVO);
        assertThat(testMatricula.getTurma()).isEqualTo(UPDATED_TURMA);
        assertThat(testMatricula.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testMatricula.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testMatricula.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testMatricula.getClasse()).isEqualTo(UPDATED_CLASSE);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).save(testMatricula);
    }

    @Test
    @Transactional
    public void updateNonExistingMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatriculaMockMvc.perform(put("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(0)).save(matricula);
    }

    @Test
    @Transactional
    public void deleteMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeDelete = matriculaRepository.findAll().size();

        // Delete the matricula
        restMatriculaMockMvc.perform(delete("/api/matriculas/{id}", matricula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).deleteById(matricula.getId());
    }

    @Test
    @Transactional
    public void searchMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);
        when(mockMatriculaSearchRepository.search(queryStringQuery("id:" + matricula.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matricula), PageRequest.of(0, 1), 1));
        // Search the matricula
        restMatriculaMockMvc.perform(get("/api/_search/matriculas?query=id:" + matricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numeroChamada").value(hasItem(DEFAULT_NUMERO_CHAMADA)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(DEFAULT_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].certificado").value(hasItem(DEFAULT_CERTIFICADO.booleanValue())))
            .andExpect(jsonPath("$.[*].documentoIdentificacao").value(hasItem(DEFAULT_DOCUMENTO_IDENTIFICACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].resenciamentoMilitar").value(hasItem(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue())))
            .andExpect(jsonPath("$.[*].documentoSaude").value(hasItem(DEFAULT_DOCUMENTO_SAUDE.booleanValue())))
            .andExpect(jsonPath("$.[*].fichaTransferencia").value(hasItem(DEFAULT_FICHA_TRANSFERENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].historicoEscolar").value(hasItem(DEFAULT_HISTORICO_ESCOLAR.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].confirmacao").value(hasItem(DEFAULT_CONFIRMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)));
    }
}
