package com.ravunana.educacao.secretaria.web.rest;

import com.ravunana.educacao.secretaria.service.EncarregadoEducaoService;
import com.ravunana.educacao.secretaria.web.rest.errors.BadRequestAlertException;
import com.ravunana.educacao.secretaria.service.dto.EncarregadoEducaoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ravunana.educacao.secretaria.domain.EncarregadoEducao}.
 */
@RestController
@RequestMapping("/api")
public class EncarregadoEducaoResource {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducaoResource.class);

    private static final String ENTITY_NAME = "secretariaEncarregadoEducao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncarregadoEducaoService encarregadoEducaoService;

    public EncarregadoEducaoResource(EncarregadoEducaoService encarregadoEducaoService) {
        this.encarregadoEducaoService = encarregadoEducaoService;
    }

    /**
     * {@code POST  /encarregado-educaos} : Create a new encarregadoEducao.
     *
     * @param encarregadoEducaoDTO the encarregadoEducaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encarregadoEducaoDTO, or with status {@code 400 (Bad Request)} if the encarregadoEducao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encarregado-educaos")
    public ResponseEntity<EncarregadoEducaoDTO> createEncarregadoEducao(@Valid @RequestBody EncarregadoEducaoDTO encarregadoEducaoDTO) throws URISyntaxException {
        log.debug("REST request to save EncarregadoEducao : {}", encarregadoEducaoDTO);
        if (encarregadoEducaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new encarregadoEducao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncarregadoEducaoDTO result = encarregadoEducaoService.save(encarregadoEducaoDTO);
        return ResponseEntity.created(new URI("/api/encarregado-educaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encarregado-educaos} : Updates an existing encarregadoEducao.
     *
     * @param encarregadoEducaoDTO the encarregadoEducaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encarregadoEducaoDTO,
     * or with status {@code 400 (Bad Request)} if the encarregadoEducaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encarregadoEducaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encarregado-educaos")
    public ResponseEntity<EncarregadoEducaoDTO> updateEncarregadoEducao(@Valid @RequestBody EncarregadoEducaoDTO encarregadoEducaoDTO) throws URISyntaxException {
        log.debug("REST request to update EncarregadoEducao : {}", encarregadoEducaoDTO);
        if (encarregadoEducaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EncarregadoEducaoDTO result = encarregadoEducaoService.save(encarregadoEducaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encarregadoEducaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encarregado-educaos} : get all the encarregadoEducaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encarregadoEducaos in body.
     */
    @GetMapping("/encarregado-educaos")
    public ResponseEntity<List<EncarregadoEducaoDTO>> getAllEncarregadoEducaos(Pageable pageable) {
        log.debug("REST request to get a page of EncarregadoEducaos");
        Page<EncarregadoEducaoDTO> page = encarregadoEducaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encarregado-educaos/:id} : get the "id" encarregadoEducao.
     *
     * @param id the id of the encarregadoEducaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encarregadoEducaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encarregado-educaos/{id}")
    public ResponseEntity<EncarregadoEducaoDTO> getEncarregadoEducao(@PathVariable Long id) {
        log.debug("REST request to get EncarregadoEducao : {}", id);
        Optional<EncarregadoEducaoDTO> encarregadoEducaoDTO = encarregadoEducaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encarregadoEducaoDTO);
    }

    /**
     * {@code DELETE  /encarregado-educaos/:id} : delete the "id" encarregadoEducao.
     *
     * @param id the id of the encarregadoEducaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encarregado-educaos/{id}")
    public ResponseEntity<Void> deleteEncarregadoEducao(@PathVariable Long id) {
        log.debug("REST request to delete EncarregadoEducao : {}", id);
        encarregadoEducaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/encarregado-educaos?query=:query} : search for the encarregadoEducao corresponding
     * to the query.
     *
     * @param query the query of the encarregadoEducao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/encarregado-educaos")
    public ResponseEntity<List<EncarregadoEducaoDTO>> searchEncarregadoEducaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EncarregadoEducaos for query {}", query);
        Page<EncarregadoEducaoDTO> page = encarregadoEducaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
