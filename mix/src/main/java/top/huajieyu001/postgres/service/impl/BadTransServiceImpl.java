package top.huajieyu001.postgres.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import top.huajieyu001.postgres.domain.BadTrans;
import top.huajieyu001.mapper.BadTransMapper;
import top.huajieyu001.postgres.service.BadTransService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
* @author xanadu
* @description 针对表【bad_trans】的数据库操作Service实现
* @createDate 2026-03-22 17:21:30
*/
@Service
public class BadTransServiceImpl extends ServiceImpl<BadTransMapper, BadTrans>
    implements BadTransService {

    private static Random random = new Random();

    @Resource
    private BadTransService owner;

    @DS("master")
    @Override
    public List<BadTrans> listMaster() {
        return this.list();
    }

    @DS("slave")
    @Override
    public List<BadTrans> listSlave() {
        return this.list();
    }

    @DS("master")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMaster() {
        save(generateBadTrans("master"));
        // 事务成功
//        int i = 1 / 0;
    }

    @DS("slave")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSlave() {
        save(generateBadTrans("slave"));
        // 事务成功
//        int i = 1 / 0;
    }

    private BadTrans generateBadTrans(String prefix) {
        BadTrans badTrans = new BadTrans();

        badTrans.setId(100 + random.nextLong());
        badTrans.setName(prefix + UUID.randomUUID());
        badTrans.setCreateTime(LocalDateTime.now());

        return badTrans;
    }


    @Transactional(rollbackFor = Exception.class)
    public String m2() {
        owner.addMaster();
        int i = 1 / 0;
        owner.addSlave();
        return "m2 success";
    }

    @Transactional(rollbackFor = Exception.class)
    public String s2() {
        owner.addSlave();
        int i = 1 / 0;
        owner.addMaster();
        return "s2 success";
    }

    @Transactional(rollbackFor = Exception.class)
    public String addAll() {
        owner.addSlave();
        owner.addMaster();
        int i = 1 / 0;
        return "addAll success";
    }
}




