package nl.openweb.iot.dashboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.repository.TaskRepository;
import nl.openweb.iot.dashboard.service.TaskService;

@Component
public class Initializer implements CommandLineRunner {

    private TaskRepository taskRepository;
    private TaskService taskService;

    public Initializer(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Override
    public void run(String... strings) throws Exception {
        for (Task task : taskRepository.findAll()) {
            taskService.startTask(task);
        }


    }
}
