package sathish.alumnijson.dto;

import java.io.Serializable;

/**
 * Created by Sathish Mun on 13-08-2015.
 */
public class BrowseAlumniJsonDTO implements Serializable {

    AlumniJsonDTO firstItemInfo;
    AlumniJsonDTO secondItemInfo;

    //constructor
    public BrowseAlumniJsonDTO() {
        firstItemInfo = new AlumniJsonDTO();
        secondItemInfo = new AlumniJsonDTO();
    }

    public AlumniJsonDTO getFirstItemInfo() { return firstItemInfo; }
    public AlumniJsonDTO getSecondItemInfo() {
        return secondItemInfo;
    }


    public void setFirstItemInfo(AlumniJsonDTO firstItemInfo) { this.firstItemInfo = firstItemInfo; }
    public void setSecondItemInfo(AlumniJsonDTO secondItemInfo) { this.secondItemInfo = secondItemInfo; }
}
