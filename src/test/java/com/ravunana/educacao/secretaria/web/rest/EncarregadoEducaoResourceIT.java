package com.ravunana.educacao.secretaria.web.rest;

import com.ravunana.educacao.secretaria.SecretariaApp;
import com.ravunana.educacao.secretaria.config.SecurityBeanOverrideConfiguration;
import com.ravunana.educacao.secretaria.domain.EncarregadoEducao;
import com.ravunana.educacao.secretaria.repository.EncarregadoEducaoRepository;
import com.ravunana.educacao.secretaria.repository.search.EncarregadoEducaoSearchRepository;
import com.ravunana.educacao.secretaria.service.EncarregadoEducaoService;
import com.ravunana.educacao.secretaria.service.dto.EncarregadoEducaoDTO;
import com.ravunana.educacao.secretaria.service.mapper.EncarregadoEducaoMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.ravunana.educacao.secretaria.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EncarregadoEducaoResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SecretariaApp.class})
public class EncarregadoEducaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IDENTIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTIFICACAO = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_PESSOAL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_PESSOAL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_TRABALHO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_RESIDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_RESIDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_TRABALHO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALARIO = new BigDecimal(1);

    private static final String DEFAULT_GRAU_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_GRAU_PARENTESCO = "BBBBBBBBBB";

    @Autowired
    private EncarregadoEducaoRepository encarregadoEducaoRepository;

    @Autowired
    private EncarregadoEducaoMapper encarregadoEducaoMapper;

    @Autowired
    private EncarregadoEducaoService encarregadoEducaoService;

    /**
     * This repository is mocked in the com.ravunana.educacao.secretaria.repository.search test package.
     *
     * @see com.ravunana.educacao.secretaria.repository.search.EncarregadoEducaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EncarregadoEducaoSearchRepository mockEncarregadoEducaoSearchRepository;

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

    private MockMvc restEncarregadoEducaoMockMvc;

    private EncarregadoEducao encarregadoEducao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncarregadoEducaoResource encarregadoEducaoResource = new EncarregadoEducaoResource(encarregadoEducaoService);
        this.restEncarregadoEducaoMockMvc = MockMvcBuilders.standaloneSetup(encarregadoEducaoResource)
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
    public static EncarregadoEducao createEntity(EntityManager em) {
        EncarregadoEducao encarregadoEducao = new EncarregadoEducao()
            .nome(DEFAULT_NOME)
            .sexo(DEFAULT_SEXO)
            .nif(DEFAULT_NIF)
            .numeroIdentificacao(DEFAULT_NUMERO_IDENTIFICACAO)
            .residencia(DEFAULT_RESIDENCIA)
            .contactoPessoal(DEFAULT_CONTACTO_PESSOAL)
            .contactoTrabalho(DEFAULT_CONTACTO_TRABALHO)
            .contactoResidencia(DEFAULT_CONTACTO_RESIDENCIA)
            .email(DEFAULT_EMAIL)
            .localTrabalho(DEFAULT_LOCAL_TRABALHO)
            .cargo(DEFAULT_CARGO)
            .salario(DEFAULT_SALARIO)
            .grauParentesco(DEFAULT_GRAU_PARENTESCO);
        return encarregadoEducao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncarregadoEducao createUpdatedEntity(EntityManager em) {
        EncarregadoEducao encarregadoEducao = new EncarregadoEducao()
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .nif(UPDATED_NIF)
            .numeroIdentificacao(UPDATED_NUMERO_IDENTIFICACAO)
            .residencia(UPDATED_RESIDENCIA)
            .contactoPessoal(UPDATED_CONTACTO_PESSOAL)
            .contactoTrabalho(UPDATED_CONTACTO_TRABALHO)
            .contactoResidencia(UPDATED_CONTACTO_RESIDENCIA)
            .email(UPDATED_EMAIL)
            .localTrabalho(UPDATED_LOCAL_TRABALHO)
            .cargo(UPDATED_CARGO)
            .salario(UPDATED_SALARIO)
            .grauParentesco(UPDATED_GRAU_PARENTESCO);
        return encarregadoEducao;
    }

    @BeforeEach
    public void initTest() {
        encarregadoEducao = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncarregadoEducao() throws Exception {
        int databaseSizeBeforeCreate = encarregadoEducaoRepository.findAll().size();

        // Create the EncarregadoEducao
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);
        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isCreated());

        // Validate the EncarregadoEducao in the database
        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeCreate + 1);
        EncarregadoEducao testEncarregadoEducao = encarregadoEducaoList.get(encarregadoEducaoList.size() - 1);
        assertThat(testEncarregadoEducao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEncarregadoEducao.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testEncarregadoEducao.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testEncarregadoEducao.getNumeroIdentificacao()).isEqualTo(DEFAULT_NUMERO_IDENTIFICACAO);
        assertThat(testEncarregadoEducao.getResidencia()).isEqualTo(DEFAULT_RESIDENCIA);
        assertThat(testEncarregadoEducao.getContactoPessoal()).isEqualTo(DEFAULT_CONTACTO_PESSOAL);
        assertThat(testEncarregadoEducao.getContactoTrabalho()).isEqualTo(DEFAULT_CONTACTO_TRABALHO);
        assertThat(testEncarregadoEducao.getContactoResidencia()).isEqualTo(DEFAULT_CONTACTO_RESIDENCIA);
        assertThat(testEncarregadoEducao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEncarregadoEducao.getLocalTrabalho()).isEqualTo(DEFAULT_LOCAL_TRABALHO);
        assertThat(testEncarregadoEducao.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testEncarregadoEducao.getSalario()).isEqualTo(DEFAULT_SALARIO);
        assertThat(testEncarregadoEducao.getGrauParentesco()).isEqualTo(DEFAULT_GRAU_PARENTESCO);

        // Validate the EncarregadoEducao in Elasticsearch
        verify(mockEncarregadoEducaoSearchRepository, times(1)).save(testEncarregadoEducao);
    }

    @Test
    @Transactional
    public void createEncarregadoEducaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encarregadoEducaoRepository.findAll().size();

        // Create the EncarregadoEducao with an existing ID
        encarregadoEducao.setId(1L);
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducao in the database
        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EncarregadoEducao in Elasticsearch
        verify(mockEncarregadoEducaoSearchRepository, times(0)).save(encarregadoEducao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducaoRepository.findAll().size();
        // set the field null
        encarregadoEducao.setNome(null);

        // Create the EncarregadoEducao, which fails.
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducaoRepository.findAll().size();
        // set the field null
        encarregadoEducao.setSexo(null);

        // Create the EncarregadoEducao, which fails.
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoPessoalIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducaoRepository.findAll().size();
        // set the field null
        encarregadoEducao.setContactoPessoal(null);

        // Create the EncarregadoEducao, which fails.
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrauParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = encarregadoEducaoRepository.findAll().size();
        // set the field null
        encarregadoEducao.setGrauParentesco(null);

        // Create the EncarregadoEducao, which fails.
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        restEncarregadoEducaoMockMvc.perform(post("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEncarregadoEducaos() throws Exception {
        // Initialize the database
        encarregadoEducaoRepository.saveAndFlush(encarregadoEducao);

        // Get all the encarregadoEducaoList
        restEncarregadoEducaoMockMvc.perform(get("/api/encarregado-educaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroIdentificacao").value(hasItem(DEFAULT_NUMERO_IDENTIFICACAO)))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].contactoPessoal").value(hasItem(DEFAULT_CONTACTO_PESSOAL)))
            .andExpect(jsonPath("$.[*].contactoTrabalho").value(hasItem(DEFAULT_CONTACTO_TRABALHO)))
            .andExpect(jsonPath("$.[*].contactoResidencia").value(hasItem(DEFAULT_CONTACTO_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].localTrabalho").value(hasItem(DEFAULT_LOCAL_TRABALHO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.intValue())))
            .andExpect(jsonPath("$.[*].grauParentesco").value(hasItem(DEFAULT_GRAU_PARENTESCO)));
    }
    
    @Test
    @Transactional
    public void getEncarregadoEducao() throws Exception {
        // Initialize the database
        encarregadoEducaoRepository.saveAndFlush(encarregadoEducao);

        // Get the encarregadoEducao
        restEncarregadoEducaoMockMvc.perform(get("/api/encarregado-educaos/{id}", encarregadoEducao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encarregadoEducao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.numeroIdentificacao").value(DEFAULT_NUMERO_IDENTIFICACAO))
            .andExpect(jsonPath("$.residencia").value(DEFAULT_RESIDENCIA))
            .andExpect(jsonPath("$.contactoPessoal").value(DEFAULT_CONTACTO_PESSOAL))
            .andExpect(jsonPath("$.contactoTrabalho").value(DEFAULT_CONTACTO_TRABALHO))
            .andExpect(jsonPath("$.contactoResidencia").value(DEFAULT_CONTACTO_RESIDENCIA))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.localTrabalho").value(DEFAULT_LOCAL_TRABALHO))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.intValue()))
            .andExpect(jsonPath("$.grauParentesco").value(DEFAULT_GRAU_PARENTESCO));
    }

    @Test
    @Transactional
    public void getNonExistingEncarregadoEducao() throws Exception {
        // Get the encarregadoEducao
        restEncarregadoEducaoMockMvc.perform(get("/api/encarregado-educaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncarregadoEducao() throws Exception {
        // Initialize the database
        encarregadoEducaoRepository.saveAndFlush(encarregadoEducao);

        int databaseSizeBeforeUpdate = encarregadoEducaoRepository.findAll().size();

        // Update the encarregadoEducao
        EncarregadoEducao updatedEncarregadoEducao = encarregadoEducaoRepository.findById(encarregadoEducao.getId()).get();
        // Disconnect from session so that the updates on updatedEncarregadoEducao are not directly saved in db
        em.detach(updatedEncarregadoEducao);
        updatedEncarregadoEducao
            .nome(UPDATED_NOME)
            .sexo(UPDATED_SEXO)
            .nif(UPDATED_NIF)
            .numeroIdentificacao(UPDATED_NUMERO_IDENTIFICACAO)
            .residencia(UPDATED_RESIDENCIA)
            .contactoPessoal(UPDATED_CONTACTO_PESSOAL)
            .contactoTrabalho(UPDATED_CONTACTO_TRABALHO)
            .contactoResidencia(UPDATED_CONTACTO_RESIDENCIA)
            .email(UPDATED_EMAIL)
            .localTrabalho(UPDATED_LOCAL_TRABALHO)
            .cargo(UPDATED_CARGO)
            .salario(UPDATED_SALARIO)
            .grauParentesco(UPDATED_GRAU_PARENTESCO);
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(updatedEncarregadoEducao);

        restEncarregadoEducaoMockMvc.perform(put("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isOk());

        // Validate the EncarregadoEducao in the database
        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeUpdate);
        EncarregadoEducao testEncarregadoEducao = encarregadoEducaoList.get(encarregadoEducaoList.size() - 1);
        assertThat(testEncarregadoEducao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEncarregadoEducao.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testEncarregadoEducao.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testEncarregadoEducao.getNumeroIdentificacao()).isEqualTo(UPDATED_NUMERO_IDENTIFICACAO);
        assertThat(testEncarregadoEducao.getResidencia()).isEqualTo(UPDATED_RESIDENCIA);
        assertThat(testEncarregadoEducao.getContactoPessoal()).isEqualTo(UPDATED_CONTACTO_PESSOAL);
        assertThat(testEncarregadoEducao.getContactoTrabalho()).isEqualTo(UPDATED_CONTACTO_TRABALHO);
        assertThat(testEncarregadoEducao.getContactoResidencia()).isEqualTo(UPDATED_CONTACTO_RESIDENCIA);
        assertThat(testEncarregadoEducao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEncarregadoEducao.getLocalTrabalho()).isEqualTo(UPDATED_LOCAL_TRABALHO);
        assertThat(testEncarregadoEducao.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testEncarregadoEducao.getSalario()).isEqualTo(UPDATED_SALARIO);
        assertThat(testEncarregadoEducao.getGrauParentesco()).isEqualTo(UPDATED_GRAU_PARENTESCO);

        // Validate the EncarregadoEducao in Elasticsearch
        verify(mockEncarregadoEducaoSearchRepository, times(1)).save(testEncarregadoEducao);
    }

    @Test
    @Transactional
    public void updateNonExistingEncarregadoEducao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducaoRepository.findAll().size();

        // Create the EncarregadoEducao
        EncarregadoEducaoDTO encarregadoEducaoDTO = encarregadoEducaoMapper.toDto(encarregadoEducao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncarregadoEducaoMockMvc.perform(put("/api/encarregado-educaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducao in the database
        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EncarregadoEducao in Elasticsearch
        verify(mockEncarregadoEducaoSearchRepository, times(0)).save(encarregadoEducao);
    }

    @Test
    @Transactional
    public void deleteEncarregadoEducao() throws Exception {
        // Initialize the database
        encarregadoEducaoRepository.saveAndFlush(encarregadoEducao);

        int databaseSizeBeforeDelete = encarregadoEducaoRepository.findAll().size();

        // Delete the encarregadoEducao
        restEncarregadoEducaoMockMvc.perform(delete("/api/encarregado-educaos/{id}", encarregadoEducao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncarregadoEducao> encarregadoEducaoList = encarregadoEducaoRepository.findAll();
        assertThat(encarregadoEducaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EncarregadoEducao in Elasticsearch
        verify(mockEncarregadoEducaoSearchRepository, times(1)).deleteById(encarregadoEducao.getId());
    }

    @Test
    @Transactional
    public void searchEncarregadoEducao() throws Exception {
        // Initialize the database
        encarregadoEducaoRepository.saveAndFlush(encarregadoEducao);
        when(mockEncarregadoEducaoSearchRepository.search(queryStringQuery("id:" + encarregadoEducao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(encarregadoEducao), PageRequest.of(0, 1), 1));
        // Search the encarregadoEducao
        restEncarregadoEducaoMockMvc.perform(get("/api/_search/encarregado-educaos?query=id:" + encarregadoEducao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroIdentificacao").value(hasItem(DEFAULT_NUMERO_IDENTIFICACAO)))
            .andExpect(jsonPath("$.[*].residencia").value(hasItem(DEFAULT_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].contactoPessoal").value(hasItem(DEFAULT_CONTACTO_PESSOAL)))
            .andExpect(jsonPath("$.[*].contactoTrabalho").value(hasItem(DEFAULT_CONTACTO_TRABALHO)))
            .andExpect(jsonPath("$.[*].contactoResidencia").value(hasItem(DEFAULT_CONTACTO_RESIDENCIA)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].localTrabalho").value(hasItem(DEFAULT_LOCAL_TRABALHO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.intValue())))
            .andExpect(jsonPath("$.[*].grauParentesco").value(hasItem(DEFAULT_GRAU_PARENTESCO)));
    }
}
