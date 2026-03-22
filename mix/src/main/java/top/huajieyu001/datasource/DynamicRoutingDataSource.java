//package top.huajieyu001.datasource;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import top.huajieyu001.holder.DatasourceContextHolder;
//
///**
// * @Author huajieyu
// * @Date 2026/3/22 18:12
// * @Version 1.0
// * @Description TODO
// */
//public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return DatasourceContextHolder.getDatasourceType();
//    }
//}
