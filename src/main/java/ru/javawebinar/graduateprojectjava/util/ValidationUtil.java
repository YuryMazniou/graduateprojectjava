package ru.javawebinar.graduateprojectjava.util;


import ru.javawebinar.graduateprojectjava.HasId;
import ru.javawebinar.graduateprojectjava.model.AbstractBaseEntity;
import ru.javawebinar.graduateprojectjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;
import ru.javawebinar.graduateprojectjava.util.exception.WrongTimeException;

public class ValidationUtil {

    private ValidationUtil() {
    }
    public static void checkTime(boolean check){
        if(!check)throw new WrongTimeException("Wrong time to show Dish for Vote");
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}