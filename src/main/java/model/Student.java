package model;

import io.sql.anonation.Mapper;

import java.util.Date;
import java.util.Objects;
import java.util.Observable;

public class Student extends Observable {
    public static final String TABLE_STUDENT = "Student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_MATH_SCORE = "math_score";
    public static final String COLUMN_PHYSIC_SCORE = "physic_score";
    public static final String COLUMN_CHEMICAL_SCORE = "chemical_score";
    public static final String COLUMN_CITY_ID = "city_id";

    @Mapper(Column = COLUMN_ID)
    protected Integer id;
    @Mapper(Column = COLUMN_FULL_NAME)
    protected String fullName;
    @Mapper(Column = COLUMN_GENDER)
    protected boolean gender;
    @Mapper(Column = COLUMN_BIRTHDAY)
    protected Date birthday;
    @Mapper(Column = COLUMN_MATH_SCORE)
    protected Float mathScore;
    @Mapper(Column = COLUMN_PHYSIC_SCORE)
    protected Float physicScore;
    @Mapper(Column = COLUMN_CHEMICAL_SCORE)
    protected Float chemicalScore;
    @Mapper(Column = COLUMN_CITY_ID)
    protected Integer cityId;

    protected City city;


    public Student() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public int getGender() {
        return gender ? 1 : 0;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getMathScore() {
        return mathScore;
    }

    public void setMathScore(Float mathScore) {
        this.mathScore = mathScore;
    }

    public Float getPhysicScore() {
        return physicScore;
    }

    public void setPhysicScore(Float physicScore) {
        this.physicScore = physicScore;
    }

    public Float getChemicalScore() {
        return chemicalScore;
    }

    public void setChemicalScore(Float chemicalScore) {
        this.chemicalScore = chemicalScore;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
        this.cityId = city.getId();
    }

    public void updateTo(Student student) {
        this.id = student.id;
        this.fullName = student.fullName;
        this.gender = student.gender;
        this.cityId = student.cityId;
        this.city = student.city;
        this.birthday = student.birthday;
        this.mathScore = student.mathScore;
        this.physicScore = student.physicScore;
        this.chemicalScore = student.chemicalScore;
        this.setChanged();
    }

    public float getTotalScore() {
        return mathScore + physicScore + chemicalScore;
    }

    public void fireDataChange() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Student && this.id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", fullName='" + fullName + '\'' + ", city=" + city + '}';
    }
}