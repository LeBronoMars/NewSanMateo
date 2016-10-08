package sanmateo.com.profileapp.models.others;

import java.util.ArrayList;

/**
 * Created by rsbulanon on 7/14/16.
 */

public class Hotlines {

    private String header;
    private String subHeader;
    private ArrayList<String> numbers;

    public Hotlines(String header, String subHeader, ArrayList<String> numbers) {
        this.header = header;
        this.subHeader = subHeader;
        this.numbers = numbers;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<String> numbers) {
        this.numbers = numbers;
    }
}
