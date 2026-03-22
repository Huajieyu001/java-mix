//package top.huajieyu001.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import top.huajieyu001.datasource.DynamicRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author huajieyu
// * @Date 2026/3/22 15:39
// * @Version 1.0
// * @Description TODO
// */
//@Configuration
//public class DatasourceConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    @Bean("masterDatasource")
//    @Primary
//    public DataSource masterDatasource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    @Bean("slaveDatasource")
//    public DataSource slaveDatasource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean("dynamicDataSource")
//    public DataSource dynamicDataSource(@Qualifier("masterDatasource") DataSource masterDatasource, @Qualifier("slaveDatasource") DataSource slaveDatasource) {
//        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
//        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
//        targetDataSources.put("master", masterDatasource);
//        targetDataSources.put("slave", slaveDatasource);
//
//        dynamicRoutingDataSource.setTargetDataSources(targetDataSources);
//        // 默认数据源
//        dynamicRoutingDataSource.setDefaultTargetDataSource(masterDatasource);
//
//        return dynamicRoutingDataSource;
//    }
//}
