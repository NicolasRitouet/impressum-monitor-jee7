package io.github.nicolasritouet.domain.boundary;

import io.github.nicolasritouet.domain.control.MonitorJobRepository;
import io.github.nicolasritouet.domain.entity.MonitorJob;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;


@Stateless
public class MonitorJobService {

    @Inject
    private transient Logger logger;

    @Inject
    MonitorJobRepository monitorJobRepository;

    public MonitorJob create(MonitorJob monitorJob) {
        monitorJobRepository.addMonitorJob(monitorJob);
        return monitorJob;
    }

    public MonitorJob get(Long id) {
        return monitorJobRepository.getMonitorJob(id);
    }
}
