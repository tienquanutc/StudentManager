package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.sql.Sql;
import io.sql.SqlConfig;
import io.txt.TxtConfig;
import io.xml.XmlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StudentService;
import util.FailSafe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

public class AppConfig extends Observable {
    protected static Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("text.config")
    private TxtConfig txtConfig;

    @JsonProperty("xml.config")
    private XmlConfig xmlConfig;

    @JsonProperty("sql.config")
    private SqlConfig sqlConfig;

    @JsonIgnore
    private StudentService mgmtService;

    private AppConfig() {

    }


    public static AppConfig newInstance(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory())
                .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper.readValue(file, AppConfig.class);
    }

    public void saveToFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory())
                .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.writeValue(new FileWriter(file), this);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        LOG.debug("Switch mode " + mode);
        this.mode = mode;
    }

    public SqlConfig getSqlConfig() {
        return sqlConfig;
    }

    public void setSqlConfig(SqlConfig config) {
        this.sqlConfig = config;
    }

    public TxtConfig getTxtConfig() {
        return txtConfig;
    }

    public void setTxtConfig(TxtConfig txtConfig) {
        this.txtConfig = txtConfig;
    }

    public XmlConfig getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(XmlConfig xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    public StudentService getMgmtService() {
        return mgmtService;
    }

    public void setMgmtService(StudentService mgmtService) {
        this.mgmtService = mgmtService;
        setChanged();
        notifyObservers();
    }

}
