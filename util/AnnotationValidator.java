
package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {

    public static String[] validate(Object obj) {

        List<String> errors = new ArrayList<>();

        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {

            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                try {

                    if (field.isAnnotationPresent(Positive.class)) {

                        Object value = field.get(obj);

                        if (value instanceof Number
                                && ((Number) value).doubleValue() <= 0) {

                            Positive annotation =
                                    field.getAnnotation(Positive.class);

                            errors.add(field.getName() + " "
                                    + annotation.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {

                        Object value = field.get(obj);

                        if (value instanceof String) {

                            MaxLength annotation =
                                    field.getAnnotation(MaxLength.class);

                            if (((String) value).length() > annotation.value()) {
                                errors.add(field.getName()
                                        + " must not exceed "
                                        + annotation.value() + " characters");
                            }
                        }
                    }

                    if (field.isAnnotationPresent(Id.class)) {

                        Object value = field.get(obj);

                        if (value == null || value.toString().isEmpty()) {
                            errors.add(field.getName()
                                    + " must not be empty");
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Unable to access field: " + field.getName());
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
