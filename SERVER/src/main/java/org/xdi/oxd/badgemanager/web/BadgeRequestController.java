package org.xdi.oxd.badgemanager.web;

import com.google.gson.JsonObject;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xdi.oxd.badgemanager.ldap.LDAPInitializer;
import org.xdi.oxd.badgemanager.ldap.commands.BadgeRequestCommands;
import org.xdi.oxd.badgemanager.ldap.models.BadgeRequests;
import org.xdi.oxd.badgemanager.ldap.models.Organizations;
import org.xdi.oxd.badgemanager.ldap.models.Person;
import org.xdi.oxd.badgemanager.ldap.service.GsonService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Arvind Tomar on 14/10/16.
 */
@CrossOrigin
@RestController
@RequestMapping("/badges/request")
public class BadgeRequestController  {

    boolean isConnected = false;
    LdapEntryManager ldapEntryManager;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createBadgeRequest(@RequestBody BadgeRequests badgeRequest, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();

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
                return jsonResponse.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "pendingBadges/{id:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPendingBadgeRequest(@PathVariable String id, HttpServletResponse response) {

        JsonObject jsonResponse = new JsonObject();

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
}