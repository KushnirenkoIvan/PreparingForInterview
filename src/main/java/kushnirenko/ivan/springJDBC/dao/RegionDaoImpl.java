package kushnirenko.ivan.springJDBC.dao;


import kushnirenko.ivan.springJDBC.domain.Region;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RegionDaoImpl implements RegionDao {

    private static final Logger log = Logger.getLogger(RegionDaoImpl.class);

    public RegionDaoImpl() {
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long create(Region region) {
        if (region == null && region.getRegionId() == null) {
            log.error("Invalid input arguments.");
            return null;
        }
        int result = jdbcTemplate.update("INSERT INTO REGIONS(REGION_ID, REGION_NAME) VALUES (?, ?)",
                region.getRegionId(), region.getRegionName());
        if (result > 0) {
            log.info(region + " successfully added.");
            return region.getRegionId();
        }
        log.error("Cannot add to DB " + region);
        return null;
    }

    @Override
    public Region read(Long id) {
        if (id == null) {
            log.error("Invalid input arguments.");
            return null;
        }
        Region region = jdbcTemplate.queryForObject("SELECT REGION_ID, REGION_NAME " +
                "from REGIONS where REGION_ID = ?", new RowMapper<Region>() {
            @Override
            public Region mapRow(ResultSet resultSet, int i) throws SQLException {
                Region region = new Region(resultSet.getLong("REGION_ID"), resultSet.getString("REGION_NAME"));
                return region;
            }
        }, id);
        if (region != null) {
            log.info(region + "successfully read from DB.");
            return region;
        }
        log.error("Cannot read from DB region with id: " + id + ".");
        return region;
    }

    @Override
    public boolean update(Region region) {
        if (region == null || region.getRegionId() == null) {
            log.error("Invalid input arguments.");
            return false;
        }
        int result = jdbcTemplate.update("UPDATE REGIONS SET REGION_NAME = ? WHERE REGION_ID = ?", region.getRegionName(), region.getRegionId());
        if (result > 0) {
            log.info(region + " successfully updated.");
            return true;
        }
        log.error("Cannot update " + region);
        return false;
    }

    @Override
    public boolean delete(Region region) {
        if (region == null || region.getRegionId() == null) {
            log.error("Invalid input arguments.");
            return false;
        }
        int result = jdbcTemplate.update("DELETE FROM REGIONS WHERE REGION_ID = ?", region.getRegionId());
        if (result > 0) {
            log.info(region + "deleted from DB successfully.");
            return true;
        }
        log.error("Cannot delete " + region + " from DB.");
        return false;
    }

    @Override
    public List<Region> findAll() {
        return null;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
        RegionDaoImpl regionDao = (RegionDaoImpl) ac.getBean("regionDaoImpl");
        regionDao.update(new Region(4l, "Middle East"));

//        System.out.println(region);
//        regionDao.create(new Region(5l, "Australia"));
//        int i = regionDao.jdbcTemplate.queryForObject("select count(*) from COUNTRIES WHERE REGION_ID = ?", Integer.class, 1);
//        System.out.println("There are a " + i + " rows in table Countries.");
    }
}
