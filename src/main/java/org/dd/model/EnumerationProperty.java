package org.dd.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举的属性
 * @author SDD
 * @version $v: 1.0.0, $time:2015/10/9 14:30 Exp $
 */
public class EnumerationProperty {

    /** 名称 **/
    private String name;

    /** 说明 **/
    private String comment;

    public String getUpperName(){
        if(StringUtils.isNumeric(name)){
            return "A" + name.toUpperCase();
        }
        return name.toUpperCase();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
