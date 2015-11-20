package kushnirenko.ivan.springJDBC.dao;


import kushnirenko.ivan.springJDBC.domain.Region;

import java.util.List;

public interface RegionDao {

    Long create(Region region);

    Region read(Long id);

    boolean update(Region region);

    boolean delete(Region region);

    List<Region> findAll();

}
