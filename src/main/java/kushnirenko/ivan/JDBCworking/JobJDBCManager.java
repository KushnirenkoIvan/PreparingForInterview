package kushnirenko.ivan.JDBCworking;

import java.util.List;

/**
 * This interface describes CRUD API for working with DB.
 */
public interface JobJDBCManager {

    String create(Job job);

    Job read(String id);

    boolean update(Job job);

    boolean delete(Job job);

    List<Job> findAll();

    void finish();
}
