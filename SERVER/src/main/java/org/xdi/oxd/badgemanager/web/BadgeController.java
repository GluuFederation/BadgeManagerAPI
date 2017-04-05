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
import org.xdi.oxd.badgemanager.util.DisableSSLCertificateCheckUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Arvind Tomar on 10/10/16.
 */
@CrossOrigin
@RestController
@Api(basePath = "/badges", description = "badges apis")
@RequestMapping("/badges")
public class BadgeController  {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public static String HelloWorld(){
        return "Hello World Finally!!!";
    }

    @RequestMapping(value = "listBadges/{id:.+}/{status:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBadgesByOrganization(@PathVariable String id, @PathVariable String status, HttpServletResponse response) {
        JsonObject jsonResponse = new JsonObject();

        try {

            final String uri = Global.API_ENDPOINT + Global.getBadgeByOrganization+"/"+id+"/"+status;

            DisableSSLCertificateCheckUtil.disableChecks();
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);

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