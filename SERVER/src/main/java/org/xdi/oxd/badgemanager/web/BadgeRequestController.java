package org.xdi.oxd.badgemanager.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xdi.oxd.badgemanager.global.Global;
import org.xdi.oxd.badgemanager.ldap.LDAPInitializer;
import org.xdi.oxd.badgemanager.ldap.commands.BadgeInstancesCommands;
import org.xdi.oxd.badgemanager.ldap.commands.BadgeRequestCommands;
import org.xdi.oxd.badgemanager.ldap.models.BadgeInstances;
import org.xdi.oxd.badgemanager.ldap.models.BadgeRequests;
import org.xdi.oxd.badgemanager.ldap.service.GsonService;
import org.xdi.oxd.badgemanager.util.DisableSSLCertificateCheckUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Arvind Tomar on 14/10/16.
 */
@CrossOrigin
@RestController
@RequestMapping("/badges/request")
public class BadgeRequestController {

    boolean isConnected = false;
    LdapEntryManager ldapEntryManager;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createBadgeRequest(@RequestBody BadgeRequests badgeRequest, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();
//        Static
//        try {
//            badgeRequest = BadgeRequestCommands.createBadgeRequest(ldapEntryManager, badgeRequest);
//            jsonResponse.add("badgeRequest", GsonService.getGson().toJsonTree(badgeRequest));
//            jsonResponse.addProperty("error", false);
//            response.setStatus(HttpServletResponse.SC_OK);
//            return jsonResponse.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            jsonResponse.addProperty("error", true);
//            jsonResponse.addProperty("errorMsg", e.getMessage());
//            return jsonResponse.toString();
//        }

        //Dynamic
        LDAPInitializer ldapInitializer = new LDAPInitializer((isConnected, ldapEntryManager) -> {
            this.isConnected = isConnected;
            this.ldapEntryManager = ldapEntryManager;
        });

        if (isConnected) {
            try {
                badgeRequest = BadgeRequestCommands.createBadgeRequest(ldapEntryManager, badgeRequest);
                jsonResponse.add("badgeRequest", GsonService.getGson().toJsonTree(badgeRequest));
                jsonResponse.addProperty("error", false);
                response.setStatus(HttpServletResponse.SC_OK);
                return jsonResponse.toString();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.addProperty("error", true);
                jsonResponse.addProperty("errorMsg", e.getMessage());
                System.out.print("Exception is adding badge request entry:"+e.getMessage());
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            System.out.print("Problem in connecting database:");
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "listPending/{id:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPendingBadgeRequestsByParticipant(@PathVariable String id, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();
        //Static
//        List<BadgeRequests> lstBadgeRequests=new ArrayList<>();
//        BadgeRequests obj1=new BadgeRequests();
//        obj1.setInum("@!4301.2A50.9A09.7688!1002!BA48.6F40");
//        obj1.setTemplateBadgeId("58dfa009a016c8832d9b7ea9");
//        obj1.setTemplateBadgeTitle("Emergency Medical Technician-Basic");
//        obj1.setParticipant("58e1dfaf159139ee277d7ab7");
//        obj1.setStatus("Pending");
//        obj1.setGluuBadgeRequester("test@test.com");
//        obj1.setDn("inum=@!4301.2A50.9A09.7688!1002!BA48.6F40,ou=badgeRequests,ou=badges,o=@!C460.F7DA.F3E9.4A62!0001!5EE3.2D5C,o=gluu");
//
//        BadgeRequests obj2=new BadgeRequests();
//        obj2.setInum("@!4301.2A50.9A09.7688!1002!D79C.9514");
//        obj2.setTemplateBadgeId("58dfa009a016c8832d9b7ea9");
//        obj2.setTemplateBadgeTitle("Entry-Level Firefighter");
//        obj2.setParticipant("58e1dfaf159139ee277d7ab7");
//        obj2.setStatus("Pending");
//        obj2.setGluuBadgeRequester("test@test.com");
//        obj2.setDn("inum=@!4301.2A50.9A09.7688!1002!D79C.9514,ou=badgeRequests,ou=badges,o=@!C460.F7DA.F3E9.4A62!0001!5EE3.2D5C,o=gluu");
//
//        lstBadgeRequests.add(obj1);
//        lstBadgeRequests.add(obj2);
//
//        try {
//            jsonResponse.add("badgeRequests", GsonService.getGson().toJsonTree(lstBadgeRequests));
//            jsonResponse.addProperty("error", false);
//            response.setStatus(HttpServletResponse.SC_OK);
//            return jsonResponse.toString();
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            jsonResponse.addProperty("error", true);
//            jsonResponse.addProperty("errorMsg", e.getMessage());
//            return jsonResponse.toString();
//        }

        //Dynamic
        LDAPInitializer ldapInitializer = new LDAPInitializer((isConnected, ldapEntryManager) -> {
            this.isConnected = isConnected;
            this.ldapEntryManager = ldapEntryManager;
        });

        if (isConnected) {
            try {
                List<BadgeRequests> lstBadgeRequests = BadgeRequestCommands.getPendingBadgeRequests(ldapEntryManager, id, "Pending");
                jsonResponse.add("badgeRequests", GsonService.getGson().toJsonTree(lstBadgeRequests));
                jsonResponse.addProperty("error", false);
                response.setStatus(HttpServletResponse.SC_OK);
                return jsonResponse.toString();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.addProperty("error", true);
                jsonResponse.addProperty("errorMsg", e.getMessage());
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "approve/{inum:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String approveBadgeRequest(@PathVariable String inum, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();
        //Static
//        try {
//            jsonResponse.addProperty("responseMsg", "Badge request approved successfully");
//            jsonResponse.addProperty("error", false);
//            response.setStatus(HttpServletResponse.SC_OK);
//            return jsonResponse.toString();
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            jsonResponse.addProperty("error", true);
//            jsonResponse.addProperty("responseMsg", e.getMessage());
//            return jsonResponse.toString();
//        }

        //Dynamic
        LDAPInitializer ldapInitializer = new LDAPInitializer((isConnected, ldapEntryManager) -> {
            this.isConnected = isConnected;
            this.ldapEntryManager = ldapEntryManager;
        });

        if (isConnected) {
            boolean isUpdated;
            try {
                isUpdated = BadgeRequestCommands.updateBadgeRequestByInum(ldapEntryManager, inum);
                if (isUpdated) {
                    jsonResponse.addProperty("responseMsg", "Badge request approved successfully");
                } else {
                    jsonResponse.addProperty("responseMsg", "Badge request approved failed");
                }
                jsonResponse.addProperty("error", false);
                response.setStatus(HttpServletResponse.SC_OK);
                return jsonResponse.toString();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.addProperty("error", true);
                jsonResponse.addProperty("responseMsg", e.getMessage());
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "listApproved/{accessToken:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getApprovedBadgeRequests(@PathVariable String accessToken, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();
        //Static
//        List<BadgeRequests> lstBadgeRequests=new ArrayList<>();
//        BadgeRequests obj1=new BadgeRequests();
//        obj1.setInum("@!4301.2A50.9A09.7688!1002!BA48.6F40");
//        obj1.setTemplateBadgeId("58dfa009a016c8832d9b7ea9");
//        obj1.setTemplateBadgeTitle("Emergency Medical Technician-Basic");
//        obj1.setParticipant("58e1dfaf159139ee277d7ab7");
//        obj1.setStatus("Approved");
//        obj1.setGluuBadgeRequester("test@test.com");
//        obj1.setDn("inum=@!4301.2A50.9A09.7688!1002!BA48.6F40,ou=badgeRequests,ou=badges,o=@!C460.F7DA.F3E9.4A62!0001!5EE3.2D5C,o=gluu");
//
//        BadgeRequests obj2=new BadgeRequests();
//        obj2.setInum("@!4301.2A50.9A09.7688!1002!D79C.9514");
//        obj2.setTemplateBadgeId("58dfa009a016c8832d9b7ea9");
//        obj2.setTemplateBadgeTitle("Entry-Level Firefighter");
//        obj2.setParticipant("58e1dfaf159139ee277d7ab7");
//        obj2.setStatus("Approved");
//        obj2.setGluuBadgeRequester("test@test.com");
//        obj2.setDn("inum=@!4301.2A50.9A09.7688!1002!D79C.9514,ou=badgeRequests,ou=badges,o=@!C460.F7DA.F3E9.4A62!0001!5EE3.2D5C,o=gluu");
//
//        lstBadgeRequests.add(obj1);
//        lstBadgeRequests.add(obj2);
//
//        try {
//            jsonResponse.add("badgeRequests", GsonService.getGson().toJsonTree(lstBadgeRequests));
//            jsonResponse.addProperty("error", false);
//            response.setStatus(HttpServletResponse.SC_OK);
//            return jsonResponse.toString();
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            jsonResponse.addProperty("error", true);
//            jsonResponse.addProperty("errorMsg", e.getMessage());
//            return jsonResponse.toString();
//        }

        //Dynamic
        LDAPInitializer ldapInitializer = new LDAPInitializer((isConnected, ldapEntryManager) -> {
            this.isConnected = isConnected;
            this.ldapEntryManager = ldapEntryManager;
        });

        if (isConnected) {
            try {
                String email = accessToken;
                List<BadgeRequests> lstBadgeRequests = BadgeRequestCommands.getApprovedBadgeRequests(ldapEntryManager, email, "Approved");
                jsonResponse.add("badgeRequests", GsonService.getGson().toJsonTree(lstBadgeRequests));
                jsonResponse.addProperty("error", false);
                response.setStatus(HttpServletResponse.SC_OK);
                return jsonResponse.toString();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.addProperty("error", true);
                jsonResponse.addProperty("errorMsg", e.getMessage());
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }

    public String createBadgeInstance(BadgeInstances badges, HttpServletResponse response) {
        JsonObject jsonResponse = new JsonObject();
        if (isConnected) {
            try {
                badges = BadgeInstancesCommands.createNewBadgeInstances(ldapEntryManager, badges);
                jsonResponse.add("badge", GsonService.getGson().toJsonTree(badges));
                return jsonResponse.toString();
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse.addProperty("error", e.getMessage());
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }
}