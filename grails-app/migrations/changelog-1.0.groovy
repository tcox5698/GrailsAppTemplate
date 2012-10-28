databaseChangeLog = {

    changeSet(author: "Tom (generated)", id: "1326643820203-1") {
        createTable(tableName: "person") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
            }

            column(name: "version", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "email_address", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Tom (generated)", id: "1326643820203-2") {
        createSequence(sequenceName: "hibernate_sequence")
    }
        
    changeSet(author: "Tom (generated)", id: "1326643820203-3") {    
        sql ("insert into person VALUES (nextval('hibernate_sequence'), 1, 'admin@admin.com','admin','565c1199262724902609410258cc480ab45057b84269436b45b814df18a5fa68')")    
        sql ("insert into person VALUES (nextval('hibernate_sequence'), 1, 'user@user.com','Bob Smith','565c1199262724902609410258cc480ab45057b84269436b45b814df18a5fa68')")    
    }
}
