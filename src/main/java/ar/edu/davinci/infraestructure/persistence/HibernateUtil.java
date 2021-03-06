package ar.edu.davinci.infraestructure.persistence;

import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.domain.model.user.detail.UserExperience;
import ar.edu.davinci.domain.model.user.detail.UserGoal;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.infraestructure.security.session.FitmeUser;
import com.github.racc.tscg.TypesafeConfig;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class HibernateUtil {

    @Getter(AccessLevel.PUBLIC)
    private SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    @Inject
    public HibernateUtil(
            @TypesafeConfig("hibernate.connection.url") String url,
            @TypesafeConfig("hibernate.connection.username") String username,
            @TypesafeConfig("hibernate.connection.password") String password,
            @TypesafeConfig("hibernate.hikari.connectionTimeout") String connectionTimeout,
            @TypesafeConfig("hibernate.hikari.minimumIdle") String minimumIdle,
            @TypesafeConfig("hibernate.hikari.idleTimeout") String idleTimeout,
            @TypesafeConfig("hibernate.hikari.maximumPoolSize") String maximumPoolSize,
            @TypesafeConfig("hibernate.hbm2ddl.auto") String hb2ddl,
            @TypesafeConfig("hibernate.show_sql") Boolean showSQL
    ) {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

        Map<String, Object> settings = new HashMap<>();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        settings.put(Environment.URL, url);
        settings.put(Environment.USER, username);
        settings.put(Environment.PASS, password);
        settings.put(Environment.DEFAULT_SCHEMA, "public");
        settings.put(Environment.HBM2DDL_AUTO, hb2ddl);
        settings.put(Environment.SHOW_SQL, showSQL);
        settings.put(Environment.FORMAT_SQL, showSQL);
        settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, true);
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.hibernate.context.internal.ManagedSessionContext");

        // HikariCP settings
        settings.put("hibernate.hikari.connectionTimeout", connectionTimeout);
        settings.put("hibernate.hikari.minimumIdle", minimumIdle);
        settings.put("hibernate.hikari.maximumPoolSize", maximumPoolSize);
        settings.put("hibernate.hikari.idleTimeout", idleTimeout);

        registryBuilder.applySettings(settings);

        registry = registryBuilder.build();
        MetadataSources sources = new MetadataSources(registry)
                .addAnnotatedClass(RoutineTemplate.class)
                .addAnnotatedClass(WorkoutExercise.class)
                .addAnnotatedClass(MealNutrition.class)
                .addAnnotatedClass(UserRoutine.class)
                .addAnnotatedClass(UserExperience.class)
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(UserInfo.class)
                .addAnnotatedClass(UserGoal.class)
                .addAnnotatedClass(TrainingSession.class)
                .addAnnotatedClass(FitmeUser.class);


        Metadata metadata = sources.getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

}
