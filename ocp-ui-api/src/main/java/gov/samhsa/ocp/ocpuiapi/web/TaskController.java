package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.service.dto.TaskDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class TaskController {
    @Autowired
    FisClient fisClient;

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("About to create a task");
        try {
            fisClient.createTask(taskDto);
            log.info("Successfully created a task.");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the activity definition was not created", ResourceType.TASK.name());
        }
    }

    @GetMapping("/tasks/search")
    public Object searchCareTeams(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                            @RequestParam(value = "searchType", required = false) String searchType,
                                            @RequestParam(value = "searchValue", required = false) String searchValue,
                                            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Tasks from FHIR server");
        try {
            Object tasks = fisClient.searchTasks(statusList, searchType, searchValue, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Tasks Search");
            return tasks;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Tasks were found in configured FHIR server for the given searchType and searchValue", ResourceType.TASK.name());
            return null;
        }
    }

}