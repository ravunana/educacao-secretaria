package com.ravunana.educacao.secretaria.web.rest;

import com.ravunana.educacao.secretaria.SecretariaApp;
import com.ravunana.educacao.secretaria.config.SecurityBeanOverrideConfiguration;
import com.ravunana.educacao.secretaria.domain.Ocorrencia;
import com.ravunana.educacao.secretaria.domain.Aluno;
import com.ravunana.educacao.secretaria.repository.OcorrenciaRepository;
import com.ravunana.educacao.secretaria.repository.search.OcorrenciaSearchRepository;
import com.ravunana.educacao.secretaria.service.OcorrenciaService;
import com.ravunana.educacao.secretaria.service.dto.OcorrenciaDTO;
import com.ravunana.educacao.secretaria.service.mapper.OcorrenciaMapper;
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
 * Integration tests for the {@link OcorrenciaResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SecretariaApp.class})
public class OcorrenciaResourceIT {

    private static final String DEFAULT_TIPO_OCORRENCIA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_OCORRENCIA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REPORTAR_ENCARREGADO = false;
    private static final Boolean UPDATED_REPORTAR_ENCARREGADO = true;

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private OcorrenciaMapper ocorrenciaMapper;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    /**
     * This repository is mocked in the com.ravunana.educacao.secretaria.repository.search test package.
     *
     * @see com.ravunana.educacao.secretaria.repository.search.OcorrenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private OcorrenciaSearchRepository mockOcorrenciaSearchRepository;

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

    private MockMvc restOcorrenciaMockMvc;

    private Ocorrencia ocorrencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OcorrenciaResource ocorrenciaResource = new OcorrenciaResource(ocorrenciaService);
        this.restOcorrenciaMockMvc = MockMvcBuilders.standaloneSetup(ocorrenciaResource)
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
    public static Ocorrencia createEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .tipoOcorrencia(DEFAULT_TIPO_OCORRENCIA)
            .data(DEFAULT_DATA)
            .numero(DEFAULT_NUMERO)
            .reportarEncarregado(DEFAULT_REPORTAR_ENCARREGADO)
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        ocorrencia.setMatricula(aluno);
        return ocorrencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocorrencia createUpdatedEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .tipoOcorrencia(UPDATED_TIPO_OCORRENCIA)
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO)
            .reportarEncarregado(UPDATED_REPORTAR_ENCARREGADO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        ocorrencia.setMatricula(aluno);
        return ocorrencia;
    }

    @BeforeEach
    public void initTest() {
        ocorrencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcorrencia() throws Exception {
        int databaseSizeBeforeCreate = ocorrenciaRepository.findAll().size();

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);
        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getTipoOcorrencia()).isEqualTo(DEFAULT_TIPO_OCORRENCIA);
        assertThat(testOcorrencia.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testOcorrencia.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testOcorrencia.isReportarEncarregado()).isEqualTo(DEFAULT_REPORTAR_ENCARREGADO);
        assertThat(testOcorrencia.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testOcorrencia.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Ocorrencia in Elasticsearch
        verify(mockOcorrenciaSearchRepository, times(1)).save(testOcorrencia);
    }

    @Test
    @Transactional
    public void createOcorrenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocorrenciaRepository.findAll().size();

        // Create the Ocorrencia with an existing ID
        ocorrencia.setId(1L);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ocorrencia in Elasticsearch
        verify(mockOcorrenciaSearchRepository, times(0)).save(ocorrencia);
    }


    @Test
    @Transactional
    public void checkTipoOcorrenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocorrenciaRepository.findAll().size();
        // set the field null
        ocorrencia.setTipoOcorrencia(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocorrenciaRepository.findAll().size();
        // set the field null
        ocorrencia.setData(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocorrenciaRepository.findAll().size();
        // set the field null
        ocorrencia.setNumero(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReportarEncarregadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocorrenciaRepository.findAll().size();
        // set the field null
        ocorrencia.setReportarEncarregado(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc.perform(post("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOcorrencias() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList
        restOcorrenciaMockMvc.perform(get("/api/ocorrencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoOcorrencia").value(hasItem(DEFAULT_TIPO_OCORRENCIA)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].reportarEncarregado").value(hasItem(DEFAULT_REPORTAR_ENCARREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get the ocorrencia
        restOcorrenciaMockMvc.perform(get("/api/ocorrencias/{id}", ocorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ocorrencia.getId().intValue()))
            .andExpect(jsonPath("$.tipoOcorrencia").value(DEFAULT_TIPO_OCORRENCIA))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.reportarEncarregado").value(DEFAULT_REPORTAR_ENCARREGADO.booleanValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOcorrencia() throws Exception {
        // Get the ocorrencia
        restOcorrenciaMockMvc.perform(get("/api/ocorrencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();

        // Update the ocorrencia
        Ocorrencia updatedOcorrencia = ocorrenciaRepository.findById(ocorrencia.getId()).get();
        // Disconnect from session so that the updates on updatedOcorrencia are not directly saved in db
        em.detach(updatedOcorrencia);
        updatedOcorrencia
            .tipoOcorrencia(UPDATED_TIPO_OCORRENCIA)
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO)
            .reportarEncarregado(UPDATED_REPORTAR_ENCARREGADO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .descricao(UPDATED_DESCRICAO);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(updatedOcorrencia);

        restOcorrenciaMockMvc.perform(put("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);
        Ocorrencia testOcorrencia = ocorrenciaList.get(ocorrenciaList.size() - 1);
        assertThat(testOcorrencia.getTipoOcorrencia()).isEqualTo(UPDATED_TIPO_OCORRENCIA);
        assertThat(testOcorrencia.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testOcorrencia.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testOcorrencia.isReportarEncarregado()).isEqualTo(UPDATED_REPORTAR_ENCARREGADO);
        assertThat(testOcorrencia.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testOcorrencia.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testOcorrencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Ocorrencia in Elasticsearch
        verify(mockOcorrenciaSearchRepository, times(1)).save(testOcorrencia);
    }

    @Test
    @Transactional
    public void updateNonExistingOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = ocorrenciaRepository.findAll().size();

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc.perform(put("/api/ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ocorrencia in Elasticsearch
        verify(mockOcorrenciaSearchRepository, times(0)).save(ocorrencia);
    }

    @Test
    @Transactional
    public void deleteOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);

        int databaseSizeBeforeDelete = ocorrenciaRepository.findAll().size();

        // Delete the ocorrencia
        restOcorrenciaMockMvc.perform(delete("/api/ocorrencias/{id}", ocorrencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ocorrencia> ocorrenciaList = ocorrenciaRepository.findAll();
        assertThat(ocorrenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ocorrencia in Elasticsearch
        verify(mockOcorrenciaSearchRepository, times(1)).deleteById(ocorrencia.getId());
    }

    @Test
    @Transactional
    public void searchOcorrencia() throws Exception {
        // Initialize the database
        ocorrenciaRepository.saveAndFlush(ocorrencia);
        when(mockOcorrenciaSearchRepository.search(queryStringQuery("id:" + ocorrencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ocorrencia), PageRequest.of(0, 1), 1));
        // Search the ocorrencia
        restOcorrenciaMockMvc.perform(get("/api/_search/ocorrencias?query=id:" + ocorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoOcorrencia").value(hasItem(DEFAULT_TIPO_OCORRENCIA)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].reportarEncarregado").value(hasItem(DEFAULT_REPORTAR_ENCARREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
}
