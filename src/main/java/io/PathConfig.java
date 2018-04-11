package io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathConfig {
    @JsonProperty("student")
    private String studentPath;
    @JsonProperty("city")
    private String cityPath;

    public PathConfig() {
    }

    public String getStudentPath() {
        return studentPath;
    }

    public void setStudentPath(String studentPath) {
        this.studentPath = studentPath;
    }

    public String getCityPath() {
        return cityPath;
    }

    public void setCityPath(String cityPath) {
        this.cityPath = cityPath;
    }
}
