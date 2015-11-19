package kushnirenko.ivan.JDBCworking;

/**
 * This class was created for training with JDBC driver.
 * Based on entity 'Job' from oracle example data base called HR (Oracle 10g XE).
 */
public class Job {

    private String id;

    private String title;

    private Long minSalary;

    private Long maxSalary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Long getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Job() {
    }

    public Job(String id, String title, Long minSalary, Long maxSalary) {
        this.id = id;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }
}
