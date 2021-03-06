package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.CredentialDto;
import gov.samhsa.ocp.ocpuiapi.service.DateRangeEnum;
import gov.samhsa.ocp.ocpuiapi.service.dto.ActivityDefinitionDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ActivityReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.AppointmentDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.AppointmentParticipantReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CommunicationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CoverageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.HealthcareServiceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OutlookCalendarDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OutsideParticipant;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantSearchDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.RelatedPersonDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.TaskDto;
import gov.samhsa.ocp.ocpuiapi.web.PractitionerController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "ocp-fis", url = "${ribbon.listOfServers}")
public interface FisClient {
    //Location

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getAllLocations(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                         @RequestParam(value = "searchKey", required = false) String searchKey,
                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organizations/{organizationId}/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getLocationsByOrganization(@PathVariable("organizationId") String organizationId,
                                                    @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                    @RequestParam(value = "searchKey", required = false) String searchKey,
                                                    @RequestParam(value = "searchValue", required = false) String searchValue,
                                                    @RequestParam(value = "assignedToPractitioner", required = false) String assignedToPractitioner,
                                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    LocationDto getLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/locations/{locationId}/child-location", method = RequestMethod.GET)
    LocationDto getChildLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/organizations/{organizationId}/locations", method = RequestMethod.POST)
    void createLocation(@PathVariable("organizationId") String organizationId,
                        @Valid @RequestBody LocationDto locationDto,
                        @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/organizations/{organizationId}/locations/{locationId}", method = RequestMethod.PUT)
    void updateLocation(@PathVariable("organizationId") String organizationId,
                        @PathVariable("locationId") String locationId,
                        @Valid @RequestBody LocationDto locationDto,
                        @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/locations/{locationId}/inactive", method = RequestMethod.PUT)
    void inactivateLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/location-references", method = RequestMethod.GET)
    List<ReferenceDto> getAllLocationReferences(@RequestParam(value = "healthcareService") String healthcareService);

    //Practitioner
    @RequestMapping(value = "/practitioners/search", method = RequestMethod.GET)
    PageDto<PractitionerDto> searchPractitioners(@RequestParam(value = "searchType", required = false) PractitionerController.SearchType searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "organization", required = false) String organization,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size,
                                                 @RequestParam(value = "showAll", required = false) Boolean showAll);

    @RequestMapping(value = "/practitioners", method = RequestMethod.POST)
    void createPractitioner(@Valid @RequestBody PractitionerDto practitionerDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/practitioners/{practitionerId}", method = RequestMethod.PUT)
    void updatePractitioner(@PathVariable("practitionerId") String practitionerId, @Valid @RequestBody PractitionerDto practitionerDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/practitioners/{practitionerId}", method = RequestMethod.GET)
    PractitionerDto getPractitioner(@PathVariable("practitionerId") String practitionerId);

    @RequestMapping(value = "/practitioners/practitioner-references", method = RequestMethod.GET)
    List<ReferenceDto> getPractitionersInOrganizationByPractitionerId(@RequestParam(value = "practitioner", required = false) String practitioner,
                                                                      @RequestParam(value = "organization", required = false) String organization,
                                                                      @RequestParam(value = "location", required = false) String location,
                                                                      @RequestParam(value = "role", required = false) String role);

    @RequestMapping(value = "/practitioners")
    PageDto<PractitionerDto> getPractitionersByOrganizationAndRole(@RequestParam(value = "organization") String organization,
                                                                   @RequestParam(value = "role", required = false) String role,
                                                                   @RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/practitioners/{practitionerId}/assign", method = RequestMethod.PUT)
    void assignLocationToPractitioner(@PathVariable("practitionerId") String practitionerId,
                                      @RequestParam(value = "organizationId") String organizationId,
                                      @RequestParam(value = "locationId") String locationId);

    @RequestMapping(value = "/practitioners/{practitionerId}/unassign", method = RequestMethod.PUT)
    void unassignLocationToPractitioner(@PathVariable("practitionerId") String practitionerId,
                                        @RequestParam(value = "organizationId") String organizationId,
                                        @RequestParam(value = "locationId") String locationId);


    @RequestMapping(value = "practitioners/find", method = RequestMethod.GET)
    public PractitionerDto findPractitioner(@RequestParam(value = "organization", required = false) String organization,
                                            @RequestParam(value = "firstName") String firstName,
                                            @RequestParam(value = "middleName", required = false) String middleName,
                                            @RequestParam(value = "lastName") String lastName,
                                            @RequestParam(value = "identifierType") String identifierType,
                                            @RequestParam(value = "identifier") String identifier);

    //Organization
    @RequestMapping(value = "/organizations/all", method = RequestMethod.GET)
    PageDto<OrganizationDto> getOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET)
    OrganizationDto getOrganization(@PathVariable("organizationId") String organizationId);

    @RequestMapping(value = "/organizations/search", method = RequestMethod.GET)
    PageDto<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) String searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size,
                                                 @RequestParam(value = "showAll", required = false) boolean showAll);

    @RequestMapping(value = "/organizations", method = RequestMethod.POST)
    void createOrganization(@Valid @RequestBody OrganizationDto organizationDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.PUT)
    void updateOrganization(@PathVariable("organizationId") String organizationId, @Valid @RequestBody OrganizationDto organizationDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/organizations/{organizationId}/inactive", method = RequestMethod.PUT)
    void inactivateOrganization(@PathVariable("organizationId") String organizationId);

    @RequestMapping(value = "/organizations")
    List<ReferenceDto> getOrganizationsByPractitioner(@RequestParam(value = "practitioner") String practitioner);

    //Patient

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    Object getPatients(@RequestParam(value = "practitioner", required = false) String practitioner,
                       @RequestParam(value = "searchKey", required = false) String searchKey,
                       @RequestParam(value = "searchValue", required = false) String searchValue,
                       @RequestParam(value = "showInActive", required = false) Boolean showInactive,
                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/patients/search", method = RequestMethod.GET)
    PageDto<PatientDto> getPatientsByValue(@RequestParam(value = "type", required = false) String key,
                                           @RequestParam(value = "value", required = false) String value,
                                           @RequestParam(value = "filterBy", required = false) String filterBy,
                                           @RequestParam(value = "organization", required = false) String organization,
                                           @RequestParam(value = "practitioner", required = false) String practitioner,
                                           @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size,
                                           @RequestParam(value = "showAll", required = false) Boolean showAll);

    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    void createPatient(@Valid @RequestBody PatientDto patientDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/patients", method = RequestMethod.PUT)
    void updatePatient(@Valid @RequestBody PatientDto patientDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
    PatientDto getPatientById(@PathVariable("patientId") String patientId);

    @RequestMapping(value = "/participants/search", method = RequestMethod.GET)
    PageDto<ParticipantSearchDto> getAllParticipants(@RequestParam(value = "patientId") String patientId,
                                                     @RequestParam(value = "member") String member,
                                                     @RequestParam(value = "value", required = false) String value,
                                                     @RequestParam(value = "organization", required = false) String organization,
                                                     @RequestParam(value = "participantForCareTeam", required = false) Boolean forCareTeam,
                                                     @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size,
                                                     @RequestParam(value = "showAll", required = false) Boolean showAll);

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    List<ParticipantReferenceDto> getCareTeamParticipants(@RequestParam(value = "patient") String patient,
                                                          @RequestParam(value = "roles", required = false) List<String> roles,
                                                          @RequestParam(value = "value", required = false) String name,
                                                          @RequestParam(value = "communication", required = false) String communication);

    @RequestMapping(value = "/communications", method = RequestMethod.GET)
    public PageDto<CommunicationDto> getCommunicationsByTopic(@RequestParam(value = "patient") String patient,
                                                              @RequestParam(value = "topic") String topic,
                                                              @RequestParam(value = "resourceType") String resourceType,
                                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize);


    //HealthcareService

    @RequestMapping(value = "/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServices(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                           @RequestParam(value = "searchKey", required = false) String searchKey,
                                                           @RequestParam(value = "searchValue", required = false) String searchValue,
                                                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organizations/{organizationId}/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServicesByOrganization(@PathVariable("organizationId") String organizationId,
                                                                         @RequestParam(value = "assignedToLocationId", required = false) String assignedToLocationId,
                                                                         @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                         @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}", method = RequestMethod.GET)
    HealthcareServiceDto getHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId);

    @RequestMapping(value = "/organizations/{organizationId}/locations/{locationId}/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServicesByLocation(@PathVariable("organizationId") String organizationId,
                                                                     @PathVariable("locationId") String locationId,
                                                                     @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                     @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/assign", method = RequestMethod.PUT)
    void assignLocationsToHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId,
                                            @RequestParam(value = "organizationId") String organizationId,
                                            @RequestParam(value = "locationIdList") List<String> locationIdList);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/unassign", method = RequestMethod.PUT)
    void unassignLocationFromHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId,
                                               @RequestParam(value = "organizationId") String organizationId,
                                               @RequestParam(value = "locationIdList") List<String> locationIdList);

    @RequestMapping(value = "/organization/{organizationId}/healthcare-services", method = RequestMethod.POST)
    void createHealthcareService(@PathVariable("organizationId") String organizationId,
                                 @Valid @RequestBody HealthcareServiceDto healthcareServiceDto,
                                 @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/organization/{organizationId}/healthcare-services/{healthcareServiceId}", method = RequestMethod.PUT)
    void updateHealthcareService(@PathVariable("organizationId") String organizationId,
                                 @PathVariable("healthcareServiceId") String healthcareServiceId,
                                 @Valid @RequestBody HealthcareServiceDto healthcareServiceDto,
                                 @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/inactive", method = RequestMethod.PUT)
    void inactivateHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId);

    @RequestMapping(value = "/healthcare-service-references", method = RequestMethod.GET)
    List<ReferenceDto> getAllHealthcareServicesReferences(@RequestParam(value = "organization", required = false) String organization);

    //CareTeam
    @RequestMapping(value = "/care-teams", method = RequestMethod.POST)
    void createCareTeam(@Valid @RequestBody CareTeamDto createTeamDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/care-teams/{careTeamId}", method = RequestMethod.PUT)
    void updateCareTeam(@PathVariable("careTeamId") String careTeamId, @Valid @RequestBody CareTeamDto careTeamDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/care-teams/{careTeamId}/add-related-person", method = RequestMethod.PUT)
    void addRelatedPerson(@PathVariable("careTeamId") String careTeamId, @Valid @RequestBody ParticipantDto participantDto);

    @RequestMapping(value = "/care-teams/{careTeamId}/remove-related-person", method = RequestMethod.PUT)
    void removeRelatedPerson(@PathVariable("careTeamId") String careTeamId, @Valid @RequestBody ParticipantDto participantDto);

    @RequestMapping(value = "/care-teams/search", method = RequestMethod.GET)
    PageDto<CareTeamDto> searchCareTeams(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                         @RequestParam(value = "searchType", required = false) String searchType,
                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping("/care-teams/{careTeamId}")
    CareTeamDto getCareTeamById(@PathVariable("careTeamId") String careTeamId);

    @RequestMapping(value = "/care-teams", method = RequestMethod.GET)
    PageDto<CareTeamDto> getCareTeamsByPatient(@RequestParam(value = "patient") String patient,
                                               @RequestParam(value = "organization", required = false) String organization,
                                               @RequestParam(value = "status", required = false) List<String> status,
                                               @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/care-teams/{careTeamId}/related-persons/search", method = RequestMethod.GET)
    PageDto<ParticipantDto> getRelatedPersonsForEdit(@PathVariable("careTeamId") String careTeamId,
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @RequestMapping("/care-teams/participant-references")
    List<ReferenceDto> getParticipantMemberFromCareTeam(@RequestParam(value = "patient") String patient);

    //Activity Definition

    @RequestMapping(value = "/organizations/{organizationId}/activity-definitions", method = RequestMethod.GET)
    Object getAllActivityDefinitionsByOrganization(@PathVariable("organizationId") String organizationId,
                                                   @RequestParam(value = "searchKey", required = false) String searchKey,
                                                   @RequestParam(value = "searchValue", required = false) String searchValue,
                                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organizations/{organizationId}/activity-definitions", method = RequestMethod.POST)
    void createActivityDefinition(@PathVariable("organizationId") String organizationId,
                                  @Valid @RequestBody ActivityDefinitionDto activityDefinitionDto,
                                  @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/organizations/{organizationId}/activity-definitions/{activityDefinitionId}", method = RequestMethod.PUT)
    void updateActivityDefinition(@PathVariable("organizationId") String organizationId,
                                  @PathVariable("activityDefinitionId") String activityDefinitionId,
                                  @Valid @RequestBody ActivityDefinitionDto activityDefinitionDto,
                                  @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/activity-definitions", method = RequestMethod.GET)
    List<ActivityReferenceDto> getActivityDefinitionsByPractitioner(@RequestParam(value = "practitioner") String practitioner);

    @RequestMapping(value = "/activity-definitions/{activityDefinitionId}", method = RequestMethod.GET)
    ActivityDefinitionDto getActivityDefinitionById(@PathVariable("activityDefinitionId") String activityDefinitionId);

    //Task

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    void createTask(@Valid @RequestBody TaskDto taskDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/tasks/search", method = RequestMethod.GET)
    Object searchTasks(@RequestParam(value = "statusList", required = false) List<String> statusList,
                       @RequestParam(value = "searchType", required = false) String searchType,
                       @RequestParam(value = "searchValue", required = false) String searchValue,
                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @RequestMapping(value = "/tasks/subtasks", method = RequestMethod.GET)
    List<TaskDto> getSubTasks(@RequestParam(value = "practitionerId", required = false) String practitionerId,
                              @RequestParam(value = "patientId", required = false) String patientId,
                              @RequestParam(value = "definition", required = false) String definition,
                              @RequestParam(value = "isUpcomingTasks", required = false) Boolean isUpcomingTasks);

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.PUT)
    void updateTask(@PathVariable("taskId") String taskId, @Valid @RequestBody TaskDto taskDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/tasks/{taskId}/deactivate", method = RequestMethod.PUT)
    void deactivateTask(@PathVariable("taskId") String taskId);

    @RequestMapping(value = "/tasks/{taskId}")
    Object getTaskById(@PathVariable("taskId") String taskId);

    @RequestMapping(value = "/tasks/task-references", method = RequestMethod.GET)
    List<ReferenceDto> getRelatedTasks(@RequestParam(value = "patient") String patient,
                                       @RequestParam(value = "definition", required = false) String definition,
                                       @RequestParam(value = "practitioner", required = false) String practitioner,
                                       @RequestParam(value = "organization", required = false) String organization);

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    List<TaskDto> getMainAndSubTasks(@RequestParam(value = "practitioner", required = false) String practitioner,
                                     @RequestParam(value = "patient", required = false) String patient,
                                     @RequestParam(value = "organization", required = false) String organization,
                                     @RequestParam(value = "definition", required = false) String definition,
                                     @RequestParam(value = "partOf", required = false) String partOf,
                                     @RequestParam(value = "isUpcomingTasks", required = false) Boolean isUpcomingTasks,
                                     @RequestParam(value = "isTodoList", required = false) Boolean isTodoList,
                                     @RequestParam(value = "filterDate", required = false) DateRangeEnum filterDate,
                                     @RequestParam(value = "statusList", required = false) List<String> statusList);

    @RequestMapping(value = "/tasks/upcoming-task-search", method = RequestMethod.GET)
    Object getUpcomingTasksByPractitionerAndRole(@RequestParam(value = "practitioner") String practitioner,
                                                 @RequestParam(value = "searchKey", required = false) String searchKey,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "pageNumber", required = false) String pageNumber,
                                                 @RequestParam(value = "pageSize", required = false) String pageSize);

    //RelatedPerson

    @RequestMapping(value = "/related-persons", method = RequestMethod.POST)
    void createRelatedPerson(@Valid @RequestBody RelatedPersonDto relatedPersonDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/related-persons/{relatedPersonId}", method = RequestMethod.PUT)
    void updateRelatedPerson(@PathVariable("relatedPersonId") String relatedPersonId, @Valid @RequestBody RelatedPersonDto relatedPersonDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/related-persons/search", method = RequestMethod.GET)
    PageDto<RelatedPersonDto> searchRelatedPersons(
            @RequestParam(value = "patientId") String patientId,
            @RequestParam(value = "searchKey", required = false) String searchKey,
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "showInActive", required = false) Boolean showInActive,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "showAll", required = false) Boolean showAll);

    @RequestMapping(value = "/related-persons/{relatedPersonId}")
    RelatedPersonDto getRelatedPersonById(@PathVariable("relatedPersonId") String relatedPersonId);

    @RequestMapping(value = "/episode-of-cares", method = RequestMethod.GET)
    List<ReferenceDto> getEpisodeOfCares(@RequestParam(value = "patient") String patient,
                                         @RequestParam(value = "organization", required = false) String organization,
                                         @RequestParam(value = "status", required = false) String status);

    //Appointment

    @RequestMapping(value = "/appointments/search", method = RequestMethod.GET)
    Object getAppointments(@RequestParam(value = "statusList", required = false) List<String> statusList,
                           @RequestParam(value = "requesterReference", required = false) String requesterReference,
                           @RequestParam(value = "patientId", required = false) String patientId,
                           @RequestParam(value = "practitionerId", required = false) String practitionerId,
                           @RequestParam(value = "searchKey", required = false) String searchKey,
                           @RequestParam(value = "searchValue", required = false) String searchValue,
                           @RequestParam(value = "showPastAppointments", required = false) Boolean showPastAppointments,
                           @RequestParam(value = "filterDateOption", required = false) String filterDateOption,
                           @RequestParam(value = "sortByStartTimeAsc", required = false) Boolean sortByStartTimeAsc,
                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/appointments/not-declined-and-not-paginated", method = RequestMethod.GET)
    List<AppointmentDto> getNonDeclinedAppointmentsWithNoPagination(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                    @RequestParam(value = "patientId", required = false) String patientId,
                                                                    @RequestParam(value = "practitionerId", required = false) String practitionerId,
                                                                    @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                    @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                    @RequestParam(value = "showPastAppointments", required = false) Boolean showPastAppointments,
                                                                    @RequestParam(value = "sortByStartTimeAsc", required = false) Boolean sortByStartTimeAsc);

    @RequestMapping(value = "/appointments/Practitioner/{practitionerId}/include-care-team-patient", method = RequestMethod.GET)
    Object getAppointmentsByPractitionerAndAssignedCareTeamPatients(@PathVariable("practitionerId") String practitionerId,
                                                                    @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                    @RequestParam(value = "requesterReference", required = false) String requesterReference,
                                                                    @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                    @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                    @RequestParam(value = "showPastAppointments", required = false) Boolean showPastAppointments,
                                                                    @RequestParam(value = "filterDateOption", required = false) String filterDateOption,
                                                                    @RequestParam(value = "sortByStartTimeAsc", required = false) Boolean sortByStartTimeAsc,
                                                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/appointments", method = RequestMethod.POST)
    void createAppointment(@Valid @RequestBody AppointmentDto appointmentDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/appointments/{appointmentId}/cancel", method = RequestMethod.PUT)
    void cancelAppointment(@PathVariable("appointmentId") String appointmentId);

    @RequestMapping(value = "/appointments/{appointmentId}/accept", method = RequestMethod.PUT)
    void acceptAppointment(@PathVariable("appointmentId") String appointmentId,
                           @RequestParam(value = "actorReference") String actorReference);

    @RequestMapping(value = "/appointments/{appointmentId}/decline", method = RequestMethod.PUT)
    void declineAppointment(@PathVariable("appointmentId") String appointmentId,
                            @RequestParam(value = "actorReference") String actorReference);

    @RequestMapping(value = "/appointments/{appointmentId}/tentative", method = RequestMethod.PUT)
    void tentativelyAcceptAppointment(@PathVariable("appointmentId") String appointmentId,
                                      @RequestParam(value = "actorReference") String actorReference);

    @RequestMapping(value = "/appointments/{appointmentId}", method = RequestMethod.PUT)
    void updateAppointment(@PathVariable("appointmentId") String appointmentId, @Valid @RequestBody AppointmentDto appointmentDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/appointments/{appointmentId}", method = RequestMethod.GET)
    AppointmentDto getAppointmentById(@PathVariable("appointmentId") String appointmentId);

    @RequestMapping(value = "/patients/{patientId}/appointmentParticipants", method = RequestMethod.GET)
    List<ParticipantReferenceDto> getAppointmentParticipants(@PathVariable("patientId") String patientId,
                                                             @RequestParam(value = "roles", required = false) List<String> roles,
                                                             @RequestParam(value = "appointmentId", required = false) String appointmentId);

    @RequestMapping(value = "/appointments/healthcare-service-references", method = RequestMethod.GET)
    List<AppointmentParticipantReferenceDto> getHealthcareServiceReferences(@RequestParam(value = "resourceType") String resourceType,
                                                                            @RequestParam(value = "resourceValue") String resourceValue);


    @RequestMapping(value = "/appointments/location-references", method = RequestMethod.GET)
    List<AppointmentParticipantReferenceDto> getAllLocationReferences(@RequestParam(value = "resourceType") String resourceType,
                                                                      @RequestParam(value = "resourceValue") String resourceValue);

    @RequestMapping(value = "/appointments/practitioner-references", method = RequestMethod.GET)
    List<AppointmentParticipantReferenceDto> getPractitionersReferences(@RequestParam(value = "resourceType") String resourceType,
                                                                        @RequestParam(value = "resourceValue") String resourceValue);

    //EWS Calendar
    @RequestMapping(value = "/outlook/calendar", method = RequestMethod.GET)
    List<OutlookCalendarDto> getOutlookCalendarAppointments(@RequestParam(value = "emailAddress") String emailAddress,
                                                            @RequestParam(value = "password") String password);

    @RequestMapping(value = "/outlook/login", method = RequestMethod.POST)
    Object loginToOutlook(@RequestBody CredentialDto credentials);

    //Communication
    @RequestMapping(value = "/communications/search", method = RequestMethod.GET)
    Object getCommunications(@RequestParam(value = "statusList", required = false) List<String> statusList,
                             @RequestParam(value = "searchKey", required = false) String searchKey,
                             @RequestParam(value = "searchValue", required = false) String searchValue,
                             @RequestParam(value = "organization", required = false) String organization,
                             @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/communications", method = RequestMethod.POST)
    void createCommunication(@Valid @RequestBody CommunicationDto communicationDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/communications/{communicationsId}", method = RequestMethod.PUT)
    void updateCommunication(@PathVariable("communicationsId") String communicationsId, @Valid @RequestBody CommunicationDto communicationDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    //Resource
    @RequestMapping(value = "/delete/{resource}/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable("resource") String resource, @PathVariable("id") String id);

    //Coverage
    @RequestMapping(value = "/coverage", method = RequestMethod.POST)
    void createCoverage(@Valid @RequestBody CoverageDto coverageDto, @RequestParam(value = "loggedInUser", required = false) String loggedInUser);

    @RequestMapping(value = "/patients/{patientId}/subscriber-options", method = RequestMethod.GET)
    List<ReferenceDto> getSubscriberOptions(@PathVariable("patientId") String patientId);

    @RequestMapping(value = "/patients/{patientId}/coverages", method = RequestMethod.GET)
    Object getCoverages(@PathVariable("patientId") String patientId,
                        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                        @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/participants/outside-organization-participants", method = RequestMethod.GET)
    List<OutsideParticipant> retrieveOutsideParticipants(@RequestParam(value = "patient") String patient,
                                                         @RequestParam(value = "participantType") String participantType,
                                                         @RequestParam(value = "name") String name,
                                                         @RequestParam(value = "organization") String organization,
                                                         @RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "size", required = false) Integer size,
                                                         @RequestParam(value = "showAll", required = false) Boolean showAll);

    @RequestMapping(value = "/appointments/outside-organization-participants", method = RequestMethod.GET)
    List<OutsideParticipant> searchOutsideParticipants(@RequestParam(value = "patient") String patient,
                                                       @RequestParam(value = "participantType") String participantType,
                                                       @RequestParam(value = "name") String name,
                                                       @RequestParam(value = "organization") String organization,
                                                       @RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size,
                                                       @RequestParam(value = "showAll", required = false) Boolean showAll);
}
