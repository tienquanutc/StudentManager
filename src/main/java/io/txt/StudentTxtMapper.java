package io.txt;

import model.Student;
import util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StudentTxtMapper implements TxtMapper<Student> {

    @Override
    public String toText(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.getId()).append("; ")
                .append(s.getFullName()).append(", ")
                .append(s.getCityId()).append(", ")
                .append(DateUtils.format(s.getBirthday())).append(" ,")
                .append(Boolean.valueOf(s.isGender())).append(", ")
                .append(s.getMathScore()).append(", ")
                .append(s.getPhysicScore()).append(", ")
                .append(s.getChemicalScore());
        return sb.toString();
    }


    @Override
    public Student toModel(String text) {
        String[] param = text.split("[;|,]");
        if (param.length != 8)
            throw new RuntimeException("Student line '" + text + "' not match rule ${student.id}; ${student.name}," +
                    " ${student.city_id), ${student.birth_day}, ${student.gender}, " +
                    "${student.math}, ${student.physic}, ${student.chemical}");

        trimParam(param);

        Student student = new Student();
        try {
            student.setId(Integer.valueOf(param[0]));
            student.setFullName(param[1]);
            student.setCityId(Integer.valueOf(param[2]));
            student.setBirthday(DateUtils.parse(param[3]));
            student.setGender(Boolean.valueOf(param[4]));
            student.setMathScore(Float.valueOf(param[5]));
            student.setPhysicScore(Float.valueOf(param[6]));
            student.setChemicalScore(Float.valueOf(param[7]));
        } catch (ParseException e) {
            throw new RuntimeException("in line '" + text + "' student birthday " + param[3] + " must be format dd/MM/yyyy");
        } catch (NumberFormatException e) {
            throw new RuntimeException("in line '" + text + "' id, cityId, mathScore, physicScore, chemicalScore must be a number");
        }
        return student;
    }
}
