package fita;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "net.ucanaccess.jdbc.UcanaccessDriver");
                settings.put(Environment.URL, "jdbc:ucanaccess://C:/Users/nguye/Documents/Database 13 4 2025.accdb.");
                settings.put(Environment.USER, "");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");

                // Hiển thị SQL để debug
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");

                // Tự động cập nhật schema
                settings.put(Environment.HBM2DDL_AUTO, "update");

                // Cấu hình connection pool
                settings.put("hibernate.c3p0.min_size", "5");
                settings.put("hibernate.c3p0.max_size", "20");
                settings.put("hibernate.c3p0.timeout", "300");
                settings.put("hibernate.c3p0.max_statements", "50");
                settings.put("hibernate.c3p0.idle_test_period", "3000");

                configuration.setProperties(settings);

                // Đăng ký các entity classes
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Subject.class);
                configuration.addAnnotatedClass(UserSubject.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Lỗi khởi tạo SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}