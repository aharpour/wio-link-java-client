package nl.openweb.iot.dashboard.web.rest;

import nl.openweb.iot.dashboard.DashboardApp;

import nl.openweb.iot.dashboard.domain.EventHandler;
import nl.openweb.iot.dashboard.repository.EventHandlerRepository;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventHandlerResource REST controller.
 *
 * @see EventHandlerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardApp.class)
public class EventHandlerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBBBBBBB";

    @Autowired
    private EventHandlerRepository eventHandlerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restEventHandlerMockMvc;

    private EventHandler eventHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            EventHandlerResource eventHandlerResource = new EventHandlerResource(eventHandlerRepository);
        this.restEventHandlerMockMvc = MockMvcBuilders.standaloneSetup(eventHandlerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventHandler createEntity(EntityManager em) {
        EventHandler eventHandler = new EventHandler()
                .name(DEFAULT_NAME)
                .className(DEFAULT_CLASS_NAME);
        return eventHandler;
    }

    @Before
    public void initTest() {
        eventHandler = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventHandler() throws Exception {
        int databaseSizeBeforeCreate = eventHandlerRepository.findAll().size();

        // Create the EventHandler

        restEventHandlerMockMvc.perform(post("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventHandler)))
            .andExpect(status().isCreated());

        // Validate the EventHandler in the database
        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeCreate + 1);
        EventHandler testEventHandler = eventHandlerList.get(eventHandlerList.size() - 1);
        assertThat(testEventHandler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventHandler.getClassName()).isEqualTo(DEFAULT_CLASS_NAME);
    }

    @Test
    @Transactional
    public void createEventHandlerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventHandlerRepository.findAll().size();

        // Create the EventHandler with an existing ID
        EventHandler existingEventHandler = new EventHandler();
        existingEventHandler.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventHandlerMockMvc.perform(post("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEventHandler)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventHandlerRepository.findAll().size();
        // set the field null
        eventHandler.setName(null);

        // Create the EventHandler, which fails.

        restEventHandlerMockMvc.perform(post("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventHandler)))
            .andExpect(status().isBadRequest());

        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventHandlerRepository.findAll().size();
        // set the field null
        eventHandler.setClassName(null);

        // Create the EventHandler, which fails.

        restEventHandlerMockMvc.perform(post("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventHandler)))
            .andExpect(status().isBadRequest());

        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventHandlers() throws Exception {
        // Initialize the database
        eventHandlerRepository.saveAndFlush(eventHandler);

        // Get all the eventHandlerList
        restEventHandlerMockMvc.perform(get("/api/event-handlers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventHandler.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEventHandler() throws Exception {
        // Initialize the database
        eventHandlerRepository.saveAndFlush(eventHandler);

        // Get the eventHandler
        restEventHandlerMockMvc.perform(get("/api/event-handlers/{id}", eventHandler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventHandler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventHandler() throws Exception {
        // Get the eventHandler
        restEventHandlerMockMvc.perform(get("/api/event-handlers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventHandler() throws Exception {
        // Initialize the database
        eventHandlerRepository.saveAndFlush(eventHandler);
        int databaseSizeBeforeUpdate = eventHandlerRepository.findAll().size();

        // Update the eventHandler
        EventHandler updatedEventHandler = eventHandlerRepository.findOne(eventHandler.getId());
        updatedEventHandler
                .name(UPDATED_NAME)
                .className(UPDATED_CLASS_NAME);

        restEventHandlerMockMvc.perform(put("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventHandler)))
            .andExpect(status().isOk());

        // Validate the EventHandler in the database
        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeUpdate);
        EventHandler testEventHandler = eventHandlerList.get(eventHandlerList.size() - 1);
        assertThat(testEventHandler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventHandler.getClassName()).isEqualTo(UPDATED_CLASS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEventHandler() throws Exception {
        int databaseSizeBeforeUpdate = eventHandlerRepository.findAll().size();

        // Create the EventHandler

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventHandlerMockMvc.perform(put("/api/event-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventHandler)))
            .andExpect(status().isCreated());

        // Validate the EventHandler in the database
        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEventHandler() throws Exception {
        // Initialize the database
        eventHandlerRepository.saveAndFlush(eventHandler);
        int databaseSizeBeforeDelete = eventHandlerRepository.findAll().size();

        // Get the eventHandler
        restEventHandlerMockMvc.perform(delete("/api/event-handlers/{id}", eventHandler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EventHandler> eventHandlerList = eventHandlerRepository.findAll();
        assertThat(eventHandlerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventHandler.class);
    }
}
