package entity;

import java.util.ArrayList;
import java.util.List;

public class ST {
    private Integer id;
    private String firstName, middleName, lastName;
    List<String> virtGR = new ArrayList<>();

    public ST(Integer id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void addVirtGR(String s) {
        virtGR.add(s);
    }


    @Override
    public String toString() {
        StringBuilder svrGR = new StringBuilder();
        for (String s : virtGR)
            svrGR.append(s);
        return "ST{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", virtGR=" + svrGR +
                '}';
    }
}
