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
import nl.openweb.iot.dashboard.domain.TaskHandler;
import nl.openweb.iot.dashboard.domain.enumeration.Language;
import nl.openweb.iot.dashboard.repository.TaskHandlerRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the TaskHandlerResource REST controller.
 *
 * @see TaskHandlerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardApp.class)
public class TaskHandlerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGAUGE = Language.GROOVYSCRIPT;
    private static final Language UPDATED_LANGAUGE = Language.JAVASCRIPT;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";


    @Autowired
    private TaskHandlerRepository taskHandlerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskHandlerMockMvc;

    private TaskHandler taskHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TaskHandlerResource taskHandlerResource = new TaskHandlerResource(taskHandlerRepository);
        this.restTaskHandlerMockMvc = MockMvcBuilders.standaloneSetup(taskHandlerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskHandler createEntity(EntityManager em) {
        TaskHandler taskHandler = new TaskHandler()
                .name(DEFAULT_NAME)
                .language(DEFAULT_LANGAUGE)
                .code(DEFAULT_CODE);
        return taskHandler;
    }

    @Before
    public void initTest() {
        taskHandler = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskHandler() throws Exception {
        int databaseSizeBeforeCreate = taskHandlerRepository.findAll().size();

        // Create the TaskHandler

        restTaskHandlerMockMvc.perform(post("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskHandler)))
            .andExpect(status().isCreated());

        // Validate the TaskHandler in the database
        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeCreate + 1);
        TaskHandler testTaskHandler = taskHandlerList.get(taskHandlerList.size() - 1);
        assertThat(testTaskHandler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskHandler.getLanguage()).isEqualTo(DEFAULT_LANGAUGE);
        assertThat(testTaskHandler.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createTaskHandlerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskHandlerRepository.findAll().size();

        // Create the TaskHandler with an existing ID
        TaskHandler existingTaskHandler = new TaskHandler();
        existingTaskHandler.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskHandlerMockMvc.perform(post("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTaskHandler)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskHandlerRepository.findAll().size();
        // set the field null
        taskHandler.setName(null);

        // Create the TaskHandler, which fails.

        restTaskHandlerMockMvc.perform(post("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskHandler)))
            .andExpect(status().isBadRequest());

        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskHandlerRepository.findAll().size();
        // set the field null
        taskHandler.setLanguage(null);

        // Create the TaskHandler, which fails.

        restTaskHandlerMockMvc.perform(post("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskHandler)))
            .andExpect(status().isBadRequest());

        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskHandlerRepository.findAll().size();
        // set the field null
        taskHandler.setCode(null);

        // Create the TaskHandler, which fails.

        restTaskHandlerMockMvc.perform(post("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskHandler)))
            .andExpect(status().isBadRequest());

        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskHandlers() throws Exception {
        // Initialize the database
        taskHandlerRepository.saveAndFlush(taskHandler);

        // Get all the taskHandlerList
        restTaskHandlerMockMvc.perform(get("/api/task-handlers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskHandler.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGAUGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getTaskHandler() throws Exception {
        // Initialize the database
        taskHandlerRepository.saveAndFlush(taskHandler);

        // Get the taskHandler
        restTaskHandlerMockMvc.perform(get("/api/task-handlers/{id}", taskHandler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskHandler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGAUGE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingTaskHandler() throws Exception {
        // Get the taskHandler
        restTaskHandlerMockMvc.perform(get("/api/task-handlers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskHandler() throws Exception {
        // Initialize the database
        taskHandlerRepository.saveAndFlush(taskHandler);
        int databaseSizeBeforeUpdate = taskHandlerRepository.findAll().size();

        // Update the taskHandler
        TaskHandler updatedTaskHandler = taskHandlerRepository.findOne(taskHandler.getId());
        updatedTaskHandler
                .name(UPDATED_NAME)
                .language(UPDATED_LANGAUGE)
                .code(UPDATED_CODE);

        restTaskHandlerMockMvc.perform(put("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskHandler)))
            .andExpect(status().isOk());

        // Validate the TaskHandler in the database
        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeUpdate);
        TaskHandler testTaskHandler = taskHandlerList.get(taskHandlerList.size() - 1);
        assertThat(testTaskHandler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskHandler.getLanguage()).isEqualTo(UPDATED_LANGAUGE);
        assertThat(testTaskHandler.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskHandler() throws Exception {
        int databaseSizeBeforeUpdate = taskHandlerRepository.findAll().size();

        // Create the TaskHandler

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskHandlerMockMvc.perform(put("/api/task-handlers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskHandler)))
            .andExpect(status().isCreated());

        // Validate the TaskHandler in the database
        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaskHandler() throws Exception {
        // Initialize the database
        taskHandlerRepository.saveAndFlush(taskHandler);
        int databaseSizeBeforeDelete = taskHandlerRepository.findAll().size();

        // Get the taskHandler
        restTaskHandlerMockMvc.perform(delete("/api/task-handlers/{id}", taskHandler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskHandler> taskHandlerList = taskHandlerRepository.findAll();
        assertThat(taskHandlerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskHandler.class);
    }
}
