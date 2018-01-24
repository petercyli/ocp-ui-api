package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ocp-fis/patients")
@Slf4j
public class PatientController {

    private final FisClient fisClient;

    public PatientController(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @GetMapping
    public Object getPatients() {
        log.debug("Call to Feign Client: START");
        try {
            Object patientDtos = fisClient.getPatients();
            log.debug("Call to Feign Client: END");
            return patientDtos;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Patient  found in the configured FHIR server for the given searchType and searchValue", ResourceType.PATIENT.name());
            return fe;
        }
    }

    @GetMapping("/search")
    public Object searchPatientsByValue(@RequestParam(value = "value") String value,
                                        @RequestParam(value = "type", defaultValue = "name") String type,
                                        @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive,
                                        @RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size) {
        try {
            Object patientDtos = fisClient.getPatientsByValue(value, type, showInactive, page,size);
            log.debug("Call to Feign Client: END");
            return patientDtos;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Patient found in the configured FHIR server for the given searchType and searchValue", ResourceType.PATIENT.name());
            return fe;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPatient(@Valid @RequestBody PatientDto patientDto) {
        try {
            fisClient.createPatient(patientDto);
            log.debug("Successfully created a patient");
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, "Patient could not be created in FHIR server", ResourceType.PATIENT.name());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@Valid @RequestBody PatientDto patientDto) {
        try {
            fisClient.updatePatient(patientDto);
            log.debug("Successfully updated a patient");
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, "Patient could not be updated in FHIR server", ResourceType.PATIENT.name());
        }
    }


}