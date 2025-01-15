package senla.dao;

import senla.model.Analytics;

import java.util.List;

public interface AnalyticsDao extends ParentDao<Analytics, Integer> {
    List<Analytics> findByPropertyId(Integer id);
}
