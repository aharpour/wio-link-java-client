package nl.openweb.iot.dashboard.web.rest;

import javax.persistence.EntityManager;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import nl.openweb.iot.dashboard.DashboardApp;
import nl.openweb.iot.dashboard.repository.GroveRepository;
import nl.openweb.iot.data.JpaGroveBean;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GroveResource REST controller.
 *
 * @see GroveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardApp.class)
public class GroveResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PASSIVE = false;
    private static final Boolean UPDATED_PASSIVE = true;

    @Autowired
    private GroveRepository groveRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restGroveMockMvc;

    private JpaGroveBean grove;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroveResource groveResource = new GroveResource(groveRepository);
        this.restGroveMockMvc = MockMvcBuilders.standaloneSetup(groveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JpaGroveBean createEntity(EntityManager em) {
        JpaGroveBean grove = new JpaGroveBean();
        grove.setName(DEFAULT_NAME);
        grove.setType(DEFAULT_TYPE);
        grove.setPassive(DEFAULT_PASSIVE);
        return grove;
    }

    @Before
    public void initTest() {
        grove = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrove() throws Exception {
        int databaseSizeBeforeCreate = groveRepository.findAll().size();

        // Create the JpaGroveBean

        restGroveMockMvc.perform(post("/api/groves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grove)))
            .andExpect(status().isCreated());

        // Validate the JpaGroveBean in the database
        List<JpaGroveBean> groveList = groveRepository.findAll();
        assertThat(groveList).hasSize(databaseSizeBeforeCreate + 1);
        JpaGroveBean testGrove = groveList.get(groveList.size() - 1);
        assertThat(testGrove.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGrove.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGrove.isPassive()).isEqualTo(DEFAULT_PASSIVE);
    }

    @Test
    @Transactional
    public void createGroveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groveRepository.findAll().size();

        // Create the JpaGroveBean with an existing ID
        JpaGroveBean existingGrove = new JpaGroveBean();
        existingGrove.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroveMockMvc.perform(post("/api/groves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGrove)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JpaGroveBean> groveList = groveRepository.findAll();
        assertThat(groveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGroves() throws Exception {
        // Initialize the database
        groveRepository.saveAndFlush(grove);

        // Get all the groveList
        restGroveMockMvc.perform(get("/api/groves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grove.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].passive").value(hasItem(DEFAULT_PASSIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getGrove() throws Exception {
        // Initialize the database
        groveRepository.saveAndFlush(grove);

        // Get the grove
        restGroveMockMvc.perform(get("/api/groves/{id}", grove.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grove.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.passive").value(DEFAULT_PASSIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrove() throws Exception {
        // Get the grove
        restGroveMockMvc.perform(get("/api/groves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrove() throws Exception {
        // Initialize the database
        groveRepository.saveAndFlush(grove);
        int databaseSizeBeforeUpdate = groveRepository.findAll().size();

        // Update the grove
        JpaGroveBean updatedGrove = groveRepository.findOne(grove.getId());
        updatedGrove.setName(UPDATED_NAME);
        updatedGrove.setType(UPDATED_TYPE);
        updatedGrove.setPassive(UPDATED_PASSIVE);

        restGroveMockMvc.perform(put("/api/groves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrove)))
            .andExpect(status().isOk());

        // Validate the JpaGroveBean in the database
        List<JpaGroveBean> groveList = groveRepository.findAll();
        assertThat(groveList).hasSize(databaseSizeBeforeUpdate);
        JpaGroveBean testGrove = groveList.get(groveList.size() - 1);
        assertThat(testGrove.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGrove.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGrove.isPassive()).isEqualTo(UPDATED_PASSIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingGrove() throws Exception {
        int databaseSizeBeforeUpdate = groveRepository.findAll().size();

        // Create the JpaGroveBean

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroveMockMvc.perform(put("/api/groves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grove)))
            .andExpect(status().isCreated());

        // Validate the JpaGroveBean in the database
        List<JpaGroveBean> groveList = groveRepository.findAll();
        assertThat(groveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrove() throws Exception {
        // Initialize the database
        groveRepository.saveAndFlush(grove);
        int databaseSizeBeforeDelete = groveRepository.findAll().size();

        // Get the grove
        restGroveMockMvc.perform(delete("/api/groves/{id}", grove.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JpaGroveBean> groveList = groveRepository.findAll();
        assertThat(groveList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
