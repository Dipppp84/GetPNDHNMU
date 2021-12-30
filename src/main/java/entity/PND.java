package entity;

public class PND {
    private Integer id;
    private String abvD;
    private String nameD;
    private String numberGroup;
    private String nameTeacher;
    private String numberPND;
    private String DateCreation;

    public PND(Integer id, String abvD, String nameD, String numberGroup, String nameTeacher, String numberPND, String dateCreation) {
        this.id = id;
        this.abvD = abvD;
        this.nameD = nameD;
        this.numberGroup = numberGroup;
        this.nameTeacher = nameTeacher;
        this.numberPND = numberPND;
        DateCreation = dateCreation;
    }
}
