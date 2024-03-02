module sn.dev.demo_du_02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core; // pour regler lerreur ou le package n es pas exposer
    requires java.sql;

    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;

    opens sn.dev.demo_du_02 to javafx.fxml;
    exports sn.dev.demo_du_02;

    opens sn.dev.demo_du_02.entitie to org.hibernate.orm.core;
}
