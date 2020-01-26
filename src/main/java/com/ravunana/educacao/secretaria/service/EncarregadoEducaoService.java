package com.ravunana.educacao.secretaria.service;

import com.ravunana.educacao.secretaria.domain.EncarregadoEducao;
import com.ravunana.educacao.secretaria.repository.EncarregadoEducaoRepository;
import com.ravunana.educacao.secretaria.repository.search.EncarregadoEducaoSearchRepository;
import com.ravunana.educacao.secretaria.service.dto.EncarregadoEducaoDTO;
import com.ravunana.educacao.secretaria.service.mapper.EncarregadoEducaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EncarregadoEducao}.
 */
@Service
@Transactional
public class EncarregadoEducaoService {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducaoService.class);

    private final EncarregadoEducaoRepository encarregadoEducaoRepository;

    private final EncarregadoEducaoMapper encarregadoEducaoMapper;

    private final EncarregadoEducaoSearchRepository encarregadoEducaoSearchRepository;

    public EncarregadoEducaoService(EncarregadoEducaoRepository encarregadoEducaoRepository, EncarregadoEducaoMapper encarregadoEducaoMapper, EncarregadoEducaoSearchRepository encarregadoEducaoSearchRepository) {
        this.encarregadoEducaoRepository = encarregadoEducaoRepository;
        this.encarregadoEducaoMapper = encarregadoEducaoMapper;
        this.encarregadoEducaoSearchRepository = encarregadoEducaoSearchRepository;
    }

    /**
     * Save a encarregadoEducao.
     *
     * @param encarregadoEducaoDTO the entity to save.
     * @return the persisted entity.
     */
    public EncarregadoEducaoDTO save(EncarregadoEducaoDTO encarregadoEducaoDTO) {
        log.debug("Request to save EncarregadoEducao : {}", encarregadoEducaoDTO);
        EncarregadoEducao encarregadoEducao = encarregadoEducaoMapper.toEntity(encarregadoEducaoDTO);
        encarregadoEducao = encarregadoEducaoRepository.save(encarregadoEducao);
        EncarregadoEducaoDTO result = encarregadoEducaoMapper.toDto(encarregadoEducao);
        encarregadoEducaoSearchRepository.save(encarregadoEducao);
        return result;
    }

    /**
     * Get all the encarregadoEducaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncarregadoEducaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EncarregadoEducaos");
        return encarregadoEducaoRepository.findAll(pageable)
            .map(encarregadoEducaoMapper::toDto);
    }


    /**
     * Get one encarregadoEducao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EncarregadoEducaoDTO> findOne(Long id) {
        log.debug("Request to get EncarregadoEducao : {}", id);
        return encarregadoEducaoRepository.findById(id)
            .map(encarregadoEducaoMapper::toDto);
    }

    /**
     * Delete the encarregadoEducao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EncarregadoEducao : {}", id);
        encarregadoEducaoRepository.deleteById(id);
        encarregadoEducaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the encarregadoEducao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncarregadoEducaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EncarregadoEducaos for query {}", query);
        return encarregadoEducaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(encarregadoEducaoMapper::toDto);
    }
}
