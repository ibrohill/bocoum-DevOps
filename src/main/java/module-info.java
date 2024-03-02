module sn.dev.demo_du_02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens sn.dev.demo_du_02 to javafx.fxml;
    exports sn.dev.demo_du_02;

    opens sn.dev.demo_du_02.entitie to org.hibernate.orm.core;
}
