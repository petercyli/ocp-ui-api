package gov.samhsa.ocp.ocpuiapi.service;

import java.util.Arrays;

public enum LookUpTypeEnum {
    //In alphabetical order
    ACTION_PARTICIPANT_ROLE,
    ACTION_PARTICIPANT_TYPE,
    ADDRESSTYPE,
    ADDRESSUSE,
    ADMINISTRATIVEGENDER,
    CARETEAMCATEGORY,
    CARETEAMREASON,
    CARETEAMSTATUS,
    DEFINITION_TOPIC,
    HEALTHCARESERVICECATEGORY,
    HEALTHCARESERVICETYPE,
    HEALTHCARESERVICEREFERRALMETHOD,
    HEALTHCARESERVICESPECIALITY,
    LANGUAGE,
    LOCATIONIDENTIFIERSYSTEM,
    LOCATIONPHYSICALTYPE,
    LOCATIONSTATUS,
    LOCATIONTYPE,
    ORGANIZATIONIDENTIFIERSYSTEM,
    ORGANIZATIONSTATUS,
    PARTICIPANTROLE,
    PARTICIPANTTYPE,
    PATIENTIDENTIFIERSYSTEM,
    PRACTITIONERIDENTIFIERSYSTEM,
    PRACTITIONERROLES,
    PUBLICATION_STATUS,
    RESOURCE_TYPE,
    RELATEDPERSONPATIENTRELATIONSHIPTYPES,
    REQUEST_INTENT,
    REQUEST_PRIORITY,
    TASK_PERFORMER_TYPE,
    TASK_STATUS,
    TELECOMSYSTEM,
    TELECOMUSE,
    USCOREBIRTHSEX,
    USCOREETHNICITY,
    USCORERACE,
    USPSSTATES;

    public static boolean contains(String s) {
        return Arrays.stream(values()).anyMatch(key -> key.name().equalsIgnoreCase(s));
    }

}
