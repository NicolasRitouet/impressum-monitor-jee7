package io.github.nicolasritouet.domain;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public abstract class AbstractServiceTest {

    @Deployment
    public static WebArchive createTestArchive() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, Filters.exclude(".*UnitTest.*"), "io.github.nicolasritouet")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsResource("environment.properties", "environment.properties")
                .addAsResource("arquillian-persistence.xml", "META-INF/persistence.xml");

        // archive.as(ZipExporter.class).exportTo(
        //         new File("/Users/nicolas/Development/temp/impressum.war"), true);
        return archive;
    }
}
