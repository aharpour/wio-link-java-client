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
