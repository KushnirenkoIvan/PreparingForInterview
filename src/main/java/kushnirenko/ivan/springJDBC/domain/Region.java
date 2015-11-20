package kushnirenko.ivan.springJDBC.domain;


import org.springframework.stereotype.Component;

@Component
public class Region {

    private Long regionId;
    private String regionName;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Region() {
    }

    public Region(Long regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
