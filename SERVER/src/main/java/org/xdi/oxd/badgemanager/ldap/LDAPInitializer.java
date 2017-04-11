package org.xdi.oxd.badgemanager.ldap;

import org.gluu.site.ldap.LDAPConnectionProvider;
import org.gluu.site.ldap.OperationsFacade;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.xdi.model.ldap.GluuLdapConfiguration;
import org.xdi.util.StringHelper;
import org.xdi.util.properties.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Arvind Tomar on 9/26/16.
 */
public class LDAPInitializer {

    private static String fileName = "src/main/resources/ecs-oxauth-ldap.properties";

    private static String configId = "";
    private static String userName = "cn=directory manager,o=gluu";
    private static String bindDN = "cn=directory manager,o=gluu";
    private static String bindPassword = "admin";
    private static String servers = "localhost:1636";
    private static List<String> serversStringsList = new ArrayList<String>() {{
        add("localhost:1636");
    }};
    private static int maxConnections = 3;
    private static boolean useSSL = true;
    private static String primaryKey = "";
    private static String localPrimaryKey = "";
    private static boolean useAnonymousBind = false;
    private static boolean enabled;
    private static int version;
    private static List<String> baseDNsStringsList = new ArrayList<String>() {{
        add("cn=directory manager");
    }};
    private static LdapEntryManager ldapEntryManager;

    public LDAPInitializer(ldapConnectionListner ldapConnection) {
        GluuLdapConfiguration gluuLdapConfiguration = new GluuLdapConfiguration(configId, bindDN, bindPassword, serversStringsList, maxConnections, useSSL, baseDNsStringsList, primaryKey, localPrimaryKey, useAnonymousBind);
        LDAPConnectionProvider ldapConnectionProvider = new LDAPConnectionProvider(prepareAuthConnectionProperties((gluuLdapConfiguration)));
        OperationsFacade operationsFacade = new OperationsFacade(ldapConnectionProvider);
        ldapEntryManager = new LdapEntryManager(operationsFacade);
        if (ldapEntryManager.authenticate(userName, bindPassword)) {
            System.out.println("Connection Success");
            ldapConnection.ldapConnected(true, ldapEntryManager);
        } else {
            System.out.println("Connection fail");
            ldapConnection.ldapConnected(false, ldapEntryManager);
        }
    }

    public static LdapEntryManager getLdapEntryManager() {
        return ldapEntryManager;
    }

    private static Properties prepareAuthConnectionProperties(GluuLdapConfiguration ldapAuthConfig) {

        FileConfiguration configuration = new FileConfiguration(fileName);

        Properties properties = (Properties) configuration.getProperties().clone();
        if (ldapAuthConfig != null) {
                properties.setProperty("servers", servers); //"localhost:1636");//buildServersString(ldapAuthConfig.getServers()));

            String bindDn = ldapAuthConfig.getBindDN();
            if (StringHelper.isNotEmpty(bindDn)) {
                properties.setProperty("bindDN", bindDn);
                properties.setProperty("bindPassword", ldapAuthConfig.getBindPassword());
            }
            properties.setProperty("useSSL", Boolean.toString(ldapAuthConfig.isUseSSL()));
            properties.setProperty("maxconnections", Integer.toString(ldapAuthConfig.getMaxConnections()));
        }


        return properties;
    }

    public interface ldapConnectionListner {
        public void ldapConnected(boolean isConnected, LdapEntryManager ldapEntryManager);
    }
}
