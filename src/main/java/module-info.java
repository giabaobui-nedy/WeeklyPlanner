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
    opens com.example.weeklyplanner.ui.controller to javafx.fxml;
    opens com.example.weeklyplanner.test.persistence.db to junit;
    opens com.example.weeklyplanner.test.application to junit;
    opens com.example.weeklyplanner.test.persistence.mapper to junit;
    opens com.example.weeklyplanner.test.persistence.repository to junit;
    exports com.example.weeklyplanner;
}