package io.sql.ulti;

import io.sql.anonation.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class ResultSetMapper {
    private static Logger LOG = LoggerFactory.getLogger(ResultSetMapper.class);

    public static <T> T map(ResultSet rs, Class<T> klazz) {
        T instance = null;
        try {
            instance = klazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to call newInstance() in class " + klazz.getName() + " may be default constructor not found or inaccessible");
        }
        Field[] declaredFields = klazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Mapper annotationMapper = (Mapper) field.getAnnotation(Mapper.class);
            if (annotationMapper == null)
                continue;
            String mapToColumnName = annotationMapper.Column();
            try {
                field.set(instance, rs.getObject(mapToColumnName));
            } catch (Exception e) {
                LOG.warn("Failed to result set mapper: " + field.getName() + " to column " + annotationMapper + " because: " + e.getMessage());
                //ignored
            }
        }
        return instance;
    }
}
