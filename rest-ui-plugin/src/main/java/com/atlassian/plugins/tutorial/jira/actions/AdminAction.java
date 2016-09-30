package com.atlassian.plugins.tutorial.jira.actions;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugins.tutorial.jira.Constants;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * Action class to support Admin Configuration screen for Admin 
 * @author asamsonov@luxoft.com
 *
 */
public class AdminAction extends JiraWebActionSupport {

    private static final long serialVersionUID = -2155666655384660401L;

    private static final Logger log = LoggerFactory.getLogger(AdminAction.class);
    
    private PluginSettings pluginSettings;
    
    public AdminAction(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettings = pluginSettingsFactory.createGlobalSettings();
    }    
    
    public String getNameTextValue() {
        Object result = pluginSettings.get(Constants.PLUGIN_STORAGE_KEY + ".name-text");
        return (result != null)?result.toString():"";
    }

    public String getSelectOptionValue() {
        return pluginSettings.get(Constants.PLUGIN_STORAGE_KEY + ".select-option") + "";
    }
    
    public boolean getCheckboxOneValue() {
        log.debug("AdminAction.getCheckboxOneValue:" + pluginSettings.get(Constants.PLUGIN_STORAGE_KEY + ".checkbox-1"));
        Object result = pluginSettings.get(Constants.PLUGIN_STORAGE_KEY + ".checkbox-1");
        return (result != null && result.toString().length() >0);
    }
    
    public boolean getCheckboxTwoValue() {
        Object result = pluginSettings.get(Constants.PLUGIN_STORAGE_KEY + ".checkbox-2");
        return (result != null && result.toString().length() >0);
    }    
    
    public String doSave() throws Exception {
        log.debug("AdminInitAction.doExecute() on Save");
        
        Map<String,String[]> requestParams = getHttpRequest().getParameterMap();
        
        pluginSettings.put(Constants.PLUGIN_STORAGE_KEY + ".name-text", getFirstArrayValue(requestParams.get("name-text")));
        pluginSettings.put(Constants.PLUGIN_STORAGE_KEY + ".select-option", getFirstArrayValue(requestParams.get("select-option")));

        pluginSettings.put(Constants.PLUGIN_STORAGE_KEY + ".checkbox-1", getFirstArrayValue(requestParams.get("checkbox-1")));
        pluginSettings.put(Constants.PLUGIN_STORAGE_KEY + ".checkbox-2", getFirstArrayValue(requestParams.get("checkbox-2")));
        
        getHttpResponse().sendRedirect("secure/admin_action.jspa");
        
        return NONE;
    }
    
    private String getFirstArrayValue(String[] vals){
        String result = "";
        
        if (vals != null && vals.length > 0)
            result = vals[0];
        return result;
    }
   
    
}
