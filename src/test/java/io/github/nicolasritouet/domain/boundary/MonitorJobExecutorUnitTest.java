package io.github.nicolasritouet.domain.boundary;


import io.github.nicolasritouet.domain.control.MonitorJobRepository;
import io.github.nicolasritouet.domain.entity.MonitorJob;
import io.github.nicolasritouet.util.PropertyFactory;
import io.github.nicolasritouet.util.annotations.Property;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MonitorJobExecutorUnitTest {

    static MonitorJobExecutor monitorJobExecutor = new MonitorJobExecutor() {
        @Override
        protected void sendEmail(MonitorJob monitorJob) {
            // Do nothing
        }
    };

    @Mock
    static MonitorJobRepository monitorJobRepositoryMock;

    @Mock
    Transport transportMock;


    @BeforeClass
    public static void init() {
        Whitebox.setInternalState(monitorJobExecutor, "email", "nicolas@ritouet.com");
        Whitebox.setInternalState(monitorJobExecutor, "smtpHostServer", "smtp.example.com");
        Whitebox.setInternalState(monitorJobExecutor, "logger", Logger.getLogger(MonitorJobExecutor.class.getName()));
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        monitorJobExecutor.monitorJobRepository = monitorJobRepositoryMock;
    }

    /**
     * Assert the the job started and notices the change of content.
     */
    @Test
    public void testStartJob() {
        // Set a specific date for the last change
        DateTime originalDate = new DateTime(2014, 12, 23, 0, 0, 0);
        List<MonitorJob> listMonitorJobs = new ArrayList<>();
        String url = "http://www.google.com";
        MonitorJob monitorJob = new MonitorJob();
        monitorJob.setUrl(url);
        monitorJob.setElement("body");
        monitorJob.setLastChange(originalDate.toDate());
        monitorJob.setBody("empty body");

        listMonitorJobs.add(monitorJob);

        when(monitorJobRepositoryMock.getAllMonitorJobs())
                .thenReturn(listMonitorJobs);

        monitorJobExecutor.startCheckJobs();

        // Assert that the specific date has been changed because content of page changed
        Assert.assertNotEquals(monitorJob.getLastChange(), originalDate.toDate());

    }
}
