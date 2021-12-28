package entity;

public class PND {
    private String abvD;
    private String nameD;
    private String numberGroup;
    private String nameTeacher;
    private String numberPND;
    private String DateCreation;

    public PND(String abvD, String nameD, String numberGroup, String nameTeacher, String numberPND, String dateCreation) {
        this.abvD = abvD;
        this.nameD = nameD;
        this.numberGroup = numberGroup;
        this.nameTeacher = nameTeacher;
        this.numberPND = numberPND;
        DateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "PND{" +
                "abvD='" + abvD + '\'' +
                ", nameD='" + nameD + '\'' +
                ", numberGroup='" + numberGroup + '\'' +
                ", nameTeacher='" + nameTeacher + '\'' +
                ", numberPND='" + numberPND + '\'' +
                ", DateCreation='" + DateCreation + '\'' +
                '}';
    }
}
