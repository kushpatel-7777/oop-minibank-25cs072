
package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

public class Repository<T> {

    private HashMap<String, T> storage = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        try {
            Class<?> currentClass = entity.getClass();

            while (currentClass != null) {
                for (Field field : currentClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);

                        Object idValue = field.get(entity);

                        if (idValue == null) {
                            throw new IllegalArgumentException("ID cannot be null.");
                        }

                        String id = String.valueOf(idValue);
                        storage.put(id, entity);

                        return entity;
                    }
                }

                currentClass = currentClass.getSuperclass();
            }

            throw new IllegalArgumentException("No @Id field found.");

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access ID field.", e);
        }
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        double sum = 0;

        for (N value : values) {
            sum += value.doubleValue();
        }

        return sum / values.length;
    }
}
