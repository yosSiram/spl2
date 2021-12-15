package bgu.spl.mics.application.objects;

import java.util.Set;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private String name;
    private int date;
    private Set<String> successfulModel;

    public ConfrenceInformation(String _name, int _date){
        name=_name;
        date=_date;
    }
    public int getDate(){return date;}

    public Set<String> getSuccessfulModel() {
        return successfulModel;
    }
}
