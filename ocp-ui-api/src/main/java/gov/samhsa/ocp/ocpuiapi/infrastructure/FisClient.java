package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.service.dto.IdentifierSystemDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ValueSetDto;
import gov.samhsa.ocp.ocpuiapi.web.OrganizationController;
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

    //LOCATIONS - START

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
                                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    LocationDto getLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/locations/{locationId}/child-location", method = RequestMethod.GET)
    LocationDto getChildLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/organization/{organizationId}/location", method = RequestMethod.POST)
    void createLocation(@PathVariable("organizationId") String organizationId,
                               @RequestParam(value = "parentLocationId", required = false) String parentLocationId,
                               @Valid @RequestBody LocationDto locationDto);

    //LOCATIONS - END

    @RequestMapping(value = "/practitioners", method = RequestMethod.GET)
    PageDto<PractitionerDto> getAllPractitioners(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/practitioners/search", method = RequestMethod.GET)
    PageDto<PractitionerDto> searchPractitioners(@RequestParam(value = "searchType", required = false) PractitionerController.SearchType searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    PageDto<OrganizationDto> getAllOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/search", method = RequestMethod.GET)
    PageDto<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) OrganizationController.SearchType searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);


    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    Object getPatients();

    @RequestMapping(value = "/patients/search", method = RequestMethod.GET)
    Object getPatientsByValue(@RequestParam(value = "value") String value,
                              @RequestParam(value = "type") String type,
                              @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size);

    //LOOKUP - START
    @RequestMapping(value = "/lookups/usps-states", method = RequestMethod.GET)
    List<ValueSetDto> getUspsStates();

    @RequestMapping(value = "/lookups/identifier-types", method = RequestMethod.GET)
    List<ValueSetDto> getIdentifierTypes(@RequestParam(value = "resourceType", required = false) String resourceType);

    @RequestMapping(value = "/lookups/identifier-systems", method = RequestMethod.GET)
    List<IdentifierSystemDto> getIdentifierSystems(@RequestParam(value = "identifierType", required = false) String identifierType);

    @RequestMapping(value = "/lookups/identifier-uses", method = RequestMethod.GET)
    List<ValueSetDto> getIdentifierUses();

    @RequestMapping(value = "/lookups/location-modes", method = RequestMethod.GET)
    List<ValueSetDto> getLocationModes();

    @RequestMapping(value = "/lookups/location-statuses", method = RequestMethod.GET)
    List<ValueSetDto> getLocationStatuses();

    @RequestMapping(value = "/lookups/location-types", method = RequestMethod.GET)
    List<ValueSetDto> getLocationTypes();

    @RequestMapping(value = "/lookups/address-types", method = RequestMethod.GET)
    List<ValueSetDto> getAddressTypes();

    @RequestMapping(value = "/lookups/address-uses", method = RequestMethod.GET)
    List<ValueSetDto> getAddressUses();

    @RequestMapping(value = "/lookups/telecom-uses", method = RequestMethod.GET)
    List<ValueSetDto> getTelecomUses();

    @RequestMapping(value = "/lookups/telecom-systems", method = RequestMethod.GET)
    List<ValueSetDto> getTelecomSystems();

    //LOOKUP - END

}
