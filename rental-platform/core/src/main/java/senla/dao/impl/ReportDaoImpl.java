package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ReportDao;
import senla.model.Report;

public class ReportDaoImpl extends AbstractDao<Report, Integer> implements ReportDao {
    @Override
    protected Class<Report> getEntityClass() {
        return Report.class;
    }
}