package senla.dao;

import senla.model.Application;

import java.util.List;

public interface ApplicationDao extends ParentDao<Application, Integer> {
    List<Application> findByPropertyId(Integer id);
}
