package sathish.sjbitalumni.dto;

import java.io.Serializable;

/**
 * Created by Sathish Mun on 18-08-2015.
 */
public class BrowseAlumniDTO  implements Serializable {

    private String name;
    private String registerNo;
    private String department;
    private String imageUrl;
    private String year;
    private String phone;
    private String password;
    private String email;


    public String getName() { return name; }
    public String getRegisterNo() { return registerNo; }
    public String getDepartment() { return department; }
    public String getImageUrl() { return imageUrl; }
    public String getYear() { return year; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }



    public void setName(String name){ this.name = name; }
    public void setRegisterNo(String registerNo){ this.registerNo = registerNo; }
    public void setDepartment(String department){ this.department = department; }
    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
    public void setYear(String year) { this.year = year; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

}
