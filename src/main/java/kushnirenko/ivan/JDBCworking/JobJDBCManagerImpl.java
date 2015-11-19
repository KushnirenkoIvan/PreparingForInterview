package kushnirenko.ivan.JDBCworking;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class JobJDBCManagerImpl implements JobJDBCManager {

    private Connection connection;

    private static final Logger log = Logger.getLogger(JobJDBCManager.class);

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public JobJDBCManagerImpl() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "hr");
            log.info("DB manager was created.");
        } catch (ClassNotFoundException exp) {
            log.fatal("Cannot create driver.");
            exp.printStackTrace();
        } catch (SQLException exp) {
            log.fatal("Cannot connect to db.");
            exp.printStackTrace();
        }
    }


    public String create(Job job) {
        if (job == null || job.getId() == null || job.getId().length() == 0) {
            log.error("Input parameters must be non-null and non-empty.");
            throw new IllegalArgumentException("Input parameters must be non-null and non-empty.");
        }
        String query = "INSERT INTO JOBS" +
                "(JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY)" +
                "VALUES(?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, job.getId());
            statement.setString(2, job.getTitle());
            statement.setLong(3, job.getMinSalary());
            statement.setLong(4, job.getMaxSalary());
            int result = statement.executeUpdate();
            if (result > 0) {
                log.info(job + " successfully added to DB.");
                return job.getId();
            }
        } catch (SQLException exp) {
            log.error("Cannot create new job:" + job);
            exp.printStackTrace();
        } finally {
            try {
                statement.close();
                log.info("Statement closed.");
            } catch (SQLException e) {
                log.error("Cannot close statement.");
                e.printStackTrace();
            }
        }
        return null;
    }

    public Job read(String id) {
        if (id == null || id.length() == 0) {
            log.error("Input parameters must be non-null and non-empty.");
            throw new IllegalArgumentException("Input parameters must be non-null and non-empty.");
        }
        Job job = new Job();
        String query = "SELECT JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY FROM JOBS WHERE " +
                "JOB_ID = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            /**
             * Operator <b>if</> is used because 'job_id' is unique identifier in db,
             * and resultSet can contains only one entity.
             * In other cases (method 'findAll') <b>while</> is used.
             */
            if (resultSet.next()) {
                job.setId(resultSet.getString("JOB_ID"));
                job.setTitle(resultSet.getString("JOB_TITLE"));
                job.setMinSalary(resultSet.getLong("MIN_SALARY"));
                job.setMaxSalary(resultSet.getLong("MAX_SALARY"));
            }
            log.info("Entity " + job + "was read from DB.");
        } catch (SQLException exp) {
            log.error("Cannot read job from db by id: " + id + ".");
            exp.printStackTrace();
        } finally {
            try {
                statement.close();
                log.info("Statement closed.");
            } catch (SQLException e) {
                log.error("Cannot close statement.");
                e.printStackTrace();
            }
        }
        return job;
    }

    public boolean update(Job job) {
        if (job == null || job.getId() == null || job.getId().length() == 0) {
            log.error("Input parameters must be non-null and non-empty.");
            throw new IllegalArgumentException("Input parameters must be non-null and non-empty.");
        }
        String query = "UPDATE JOBS SET JOB_TITLE= ?, MIN_SALARY=?, MAX_SALARY=?  WHERE JOB_ID=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, job.getTitle());
            statement.setLong(2, job.getMinSalary());
            statement.setLong(3, job.getMaxSalary());
            statement.setString(4, job.getId());
            int result = statement.executeUpdate();
            if (result > 0) {
                log.info(job + " updated successfully.");
                return true;
            }
        } catch (SQLException exp) {
            log.error("Cannot update: " + job + ".");
            exp.printStackTrace();
        } finally {
            try {
                statement.close();
                log.info("Statement closed.");
            } catch (SQLException e) {
                log.error("Cannot close statement.");
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean delete(Job job) {
        if (job == null || job.getId() == null || job.getId().length() == 0) {
            log.error("Input parameters must be non-null and non-empty.");
            throw new IllegalArgumentException("Input parameters must be non-null and non-empty.");
        }
        String query = "DELETE FROM JOBS WHERE JOB_ID = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, job.getId());
            int result = statement.executeUpdate();
            if (result > 0) {
                log.info(job + " successfully deleted from DB.");
                return true;
            }
        } catch (SQLException e) {
            log.error("Cannot delete " + job + " from DB.");
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                log.info("Statement closed.");
            } catch (SQLException e) {
                log.error("Cannot close statement.");
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<Job> findAll() {
        ArrayList<Job> jobs = new ArrayList<Job>();
        String query = "SELECT * FROM JOBS";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getString("JOB_ID"), resultSet.getString("JOB_TITLE"),
                        resultSet.getLong("MIN_SALARY"), resultSet.getLong("MAX_SALARY")));
            }
            if (jobs.size() > 0) {
                log.info("All fields successfully got from DB.");
                return jobs;
            }
        } catch (SQLException e) {
            log.error("Cannot get all fields from DB.");
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                log.info("Statement closed.");
            } catch (SQLException e) {
                log.error("Cannot close statement.");
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * <p>This method close connection to DB.</>
     */
    public void finish() {
        try {
            connection.close();
            log.info("Connection to DB successfully closed.");
        } catch (SQLException e) {
            log.error("Cannot close connection to DB.");
            e.printStackTrace();
        }
    }

    /**
     * This main method explains, how does application works.
     * After all operations we must invoke method <b>finish(); </>
     * for closing connection to data base.
     */
    public static void main(String[] args) {
        Job job = new Job("G_Coll", "Garbage_Collector", 500l, 900l);
        JobJDBCManager jdbcManager = new JobJDBCManagerImpl();
        jdbcManager.create(job);
        Job fManager = jdbcManager.read("FI_MGR");
        fManager.setTitle("Cowboy");
        jdbcManager.update(fManager);
        jdbcManager.delete(job);
        System.out.println(jdbcManager.findAll());
        jdbcManager.finish();
    }

}
