package io.github.nicolasritouet.domain.boundary;

import io.github.nicolasritouet.domain.AbstractServiceTest;
import io.github.nicolasritouet.domain.entity.MonitorJob;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class MonitorJobServiceTest extends AbstractServiceTest {

    @Inject
    MonitorJobService monitorJobService;

    @Test
    public void shouldCRUDaMonitorJob() {

        MonitorJob monitorJob = new MonitorJob();
        monitorJob.setUrl("http://global.sap.com/corporate-en/legal/impressum.epx");
        Assert.assertNull(monitorJob.getId());
        monitorJobService.create(monitorJob);

        Assert.assertNotNull(monitorJob.getId());

        // TODO NRT: check the rest of the CRUD ...

    }
}
