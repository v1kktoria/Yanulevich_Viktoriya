package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Report;

@Component
public class ReportDAOImpl extends AbstractDAO<Report, Integer> {
    @Override
    protected Class<Report> getEntityClass() {
        return Report.class;
    }
}