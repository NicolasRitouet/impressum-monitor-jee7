package io.github.nicolasritouet.domain.control;

import io.github.nicolasritouet.domain.entity.MonitorJob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MonitorJobRepository {

    @PersistenceContext(unitName = "ImpressumMonitor", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void addMonitorJob(MonitorJob monitorJob) {
        entityManager.persist(monitorJob);
    }

    public void updateMonitorJob(MonitorJob monitorJob) {
        entityManager.merge(monitorJob);
    }

    public List<MonitorJob> getAllMonitorJobs() {
        TypedQuery<MonitorJob> query = entityManager.createQuery("SELECT m from MonitorJob as m", MonitorJob.class);
        return query.getResultList();
    }

    public MonitorJob getMonitorJob(@NotNull Long monitorJobId) {
        return entityManager.find(MonitorJob.class, monitorJobId);
    }


}
