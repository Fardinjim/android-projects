package sathish.alumnijson.dto;

import java.io.Serializable;

/**
 * Created by Sathish Mun on 13-08-2015.
 */
public class AlumniJsonDTO implements Serializable {

    private String name;
    private String registerNo;
    private String department;
    private String imageUrl;

    public String getName() { return name; }
    public String getRegisterNo() { return registerNo; }
    public String getDepartment() { return department; }
    public String getImageUrl() { return imageUrl; }


    public void setName(String name){ this.name = name; }
    public void setRegisterNo(String registerNo){ this.registerNo = registerNo; }
    public void setDepartment(String department){ this.department = department; }
    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }

}
