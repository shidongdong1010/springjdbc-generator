package org.dd.util;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class TemplateService{

	private static String tplFolder = "templates/";
    
    @Autowired  
    private VelocityEngine velocityEngine;  
      
    public String mergeTemplateIntoString(String vm, Map<String, Object> map) {  
        String tpl = tplFolder + vm;  
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, tpl, "utf-8", map);
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
