package top.huajieyu001.postgres.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.huajieyu001.postgres.domain.BadTrans;

import java.util.List;

/**
* @author xanadu
* @description 针对表【bad_trans】的数据库操作Service
* @createDate 2026-03-22 17:21:30
*/
public interface BadTransService extends IService<BadTrans> {

    List<BadTrans> listMaster();

    List<BadTrans> listSlave();

    void addMaster();

    void addSlave();

    String s2();

    String m2();

    String addAll();
}
