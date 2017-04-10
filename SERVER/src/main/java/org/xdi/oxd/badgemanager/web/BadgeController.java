package org.xdi.oxd.badgemanager.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xdi.oxd.badgemanager.global.Global;
import org.xdi.oxd.badgemanager.ldap.service.GsonService;
import org.xdi.oxd.badgemanager.model.IssuerBadgeRequest;
import org.xdi.oxd.badgemanager.util.DisableSSLCertificateCheckUtil;
import org.xdi.oxd.badgemanager.util.Utils;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Arvind Tomar on 10/10/16.
 */
@CrossOrigin
@RestController
@Api(basePath = "/badges", description = "badges apis")
@RequestMapping("/badges")
public class BadgeController  {

    @RequestMapping(value = "listBadges/{accessToken:.+}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBadgesByOrganization(@PathVariable String accessToken, HttpServletResponse response) {
        JsonObject jsonResponse = new JsonObject();

        try {

            String[] split = accessToken.split("\\.");

            String decodeTokenBody = Utils.decodeBase64url(split[1]);

            JsonObject jsonObjectBody = new JsonParser().parse(decodeTokenBody).getAsJsonObject();

            String issuer= jsonObjectBody.get("iss").getAsString();

            IssuerBadgeRequest issuerBadgeRequest=new IssuerBadgeRequest(issuer);

            final String uri = Global.API_ENDPOINT + Global.getBadgeByOrganization;

            DisableSSLCertificateCheckUtil.disableChecks();
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri, issuerBadgeRequest, String.class);

            JsonArray jArrayResponse = new JsonParser().parse(result).getAsJsonArray();
            if (jArrayResponse.size() > 0){
                response.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.add("badges", GsonService.getGson().toJsonTree(jArrayResponse));
                jsonResponse.addProperty("error", false);
                return jsonResponse.toString();
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.addProperty("error", true);
                jsonResponse.addProperty("errorMsg", "No badges found");
                return jsonResponse.toString();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("error", "Please try after some time");
            return jsonResponse.toString();
        }
    }
}