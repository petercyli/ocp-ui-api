package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ocp-fis/care-teams")
@Slf4j
public class CareTeamController {

    @Autowired
    private FisClient fisClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCareTeam(@Valid @RequestBody CareTeamDto careTeamDto) {
        try {
            fisClient.createCareTeam(careTeamDto);
            log.debug("Successfully created a CareTeam");

        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, "Care Team could not be created in FHIR server", ResourceType.CARE_TEAM.name());
        }
    }

    @PutMapping("/{careTeamId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCareTeam(@PathVariable String careTeamId, @Valid @RequestBody CareTeamDto careTeamDto) {
        try {
            fisClient.updateCareTeam(careTeamId, careTeamDto);
            log.debug("Successfully updated a CareTeam");

        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe,"Care Team could not be updated in FHIR server", ResourceType.CARE_TEAM.name());
        }
    }

    @GetMapping("/search")
    public PageDto<CareTeamDto> searchCareTeams(@RequestParam(value="statusList",required = false) List<String> statusList,
                                                @RequestParam(value="searchType",required = false) String searchType,
                                                @RequestParam(value="searchValue",required = false) String searchValue,
                                                @RequestParam(value="pageNumber",required = false) Integer pageNumber,
                                                @RequestParam(value="pageSize",required = false) Integer pageSize){
        log.info("Searching Care Teams from FHIR server");
        try{
            PageDto<CareTeamDto> careTeams=fisClient.searchCareTeams(statusList,searchType,searchValue,pageNumber,pageSize);
            log.info("Got Response from FHIR server for Care Team Search");
            return careTeams;
        }catch(FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe,"No care Teams were found in configured FHIR server for the given searchType and searchValue", ResourceType.CARE_TEAM.name());
            return null;
        }

    }

}