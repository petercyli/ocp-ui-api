package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.UaaGroupService;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UaaGroupController {

    @Autowired
    UaaGroupService uaaGroupService;

    @GetMapping("/groups")
    public Object getAllGroups() {
        return uaaGroupService.getAllGroups();
    }

    @GetMapping("/scopes")
    public Object getAllScopes() {
        return uaaGroupService.getAllScopes();
    }

    @GetMapping("/users")
    public Object getUsersByOrganizationId(@RequestParam(value="organizationId", required = true) String organizationId) {
        return uaaGroupService.getAllUsersByOrganizationId(organizationId);
    }

    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@Valid @RequestBody GroupRequestDto groupDto) {
        uaaGroupService.createGroup(groupDto);
    }

    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGroup(@PathVariable("groupId") String groupId, @Valid @RequestBody GroupRequestDto groupDto) {
        uaaGroupService.updateGroup(groupId, groupDto);
    }

    @PutMapping("/users/{userId}/groups/{groupsId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignRoleToUser(@PathVariable("userId") String userId, @PathVariable("groupsId") String groupsId ) {
        uaaGroupService.assignRoleToUser(RoleToUserDto.builder().groupId(groupsId).userId(userId).build());
    }

}
