package io.github.nicolasritouet.util.annotations;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    @Nonbinding String key() default "";
    @Nonbinding String defaultValue() default "";
    @Nonbinding boolean mandatory() default false;
}
