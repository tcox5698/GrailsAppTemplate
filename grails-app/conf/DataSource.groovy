System.out.println("App name is ${appName}.  Create databases like ${appName}_dev for dev, test and stage.")

dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://localhost/${appName}_dev"
            username = "postgres"
            password = "password"
        }
    }
    test {
        dataSource {
            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://localhost/${appName}_test"
            username = "postgres"
            password = "password"
        }
    }  
    
    stage {
        dataSource {
            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://localhost/${appName}_stage"
            username = "postgres"
            password = "password"
        }
    }    
//    //Assuming heroku plugin overwrites this set of values?
//    production {
//        dataSource {
//            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
//            driverClassName = "org.postgresql.Driver"
//            dialect = "org.hibernate.dialect.PostgreSQLDialect"  
//                      
//            uri = new URI("postgres://ojkbubwpyj:3Z0iu5W1uRuVvv2ltyhh@ec2-50-17-236-208.compute-1.amazonaws.com/ojkbubwpyj")   
//            url = "jdbc:postgresql://"+uri.host+uri.path
//            username = uri.userInfo.split(":")[0]
//            password = uri.userInfo.split(":")[1]            
//        }
//    }
}
