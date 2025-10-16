module com.example.weeklyplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires ormlite.jdbc;
    requires org.xerial.sqlitejdbc;
    requires junit;
    // 👇 ORMLite needs reflective access to your entities
    opens com.example.weeklyplanner.persistence.entity to ormlite.jdbc;
    opens com.example.weeklyplanner to javafx.fxml;
    opens com.example.weeklyplanner.test.persistence.db to junit;
    exports com.example.weeklyplanner;
}