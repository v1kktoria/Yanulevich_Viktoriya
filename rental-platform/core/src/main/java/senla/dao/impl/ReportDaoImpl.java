package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.ReportDao;
import senla.model.Report;

@Repository
public class ReportDaoImpl extends AbstractDao<Report, Integer> implements ReportDao {
    @Override
    protected Class<Report> getEntityClass() {
        return Report.class;
    }
}