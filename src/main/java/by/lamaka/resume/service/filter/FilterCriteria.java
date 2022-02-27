package by.lamaka.resume.service.filter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.text.SimpleDateFormat;

/**
 * Class to store key, operation and value
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FilterCriteria {
    String key;
    String operation;
    Object value;

    /**
     * Constructor for FilterCritetia
     *
     * @param key       filter options, example firstName, secondName..
     * @param operation filter operation, example " : "
     * @param value     filter value, example Dima, Egorov
     */
    public FilterCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        setValue(value);
    }

    @SneakyThrows
    public void setValue(Object value) {
        String temp = (String) value;
        if (temp.matches("\\d{4}-\\d{2}-{2}")) {
            this.value = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
        } else {
            this.value = value;
        }
    }
}
