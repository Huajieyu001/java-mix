package top.huajieyu001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.huajieyu001.domain.BadTransaction;
import top.huajieyu001.mapper.BadTransactionMapper;
import top.huajieyu001.service.BadTransactionService;

import java.util.UUID;

/**
* @author xanadu
* @description 针对表【bad_transaction】的数据库操作Service实现
* @createDate 2026-03-22 00:58:29
*/
@Service
public class BadTransactionServiceImpl extends ServiceImpl<BadTransactionMapper, BadTransaction>
    implements BadTransactionService {

    @Autowired
    private BadTransactionServiceImpl owner;

    @Transactional(rollbackFor = Exception.class)
    public void insertOne(BadTransaction badTransaction) {
        this.save(badTransaction);
        int i = 1 / 0;
    }

    // 失效
    public void bad() {
        insertOne(generateBadTransaction("Bad ++++"));
    }

    // 失效
    @Transactional(rollbackFor = Exception.class)
    public void propagation() {
        owner.invokeP();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void invokeP(){
        this.save(generateBadTransaction("invokeP"));
        int i = 1 / 0;
    }

    public void good() {
        owner.insertOne(generateBadTransaction("Good................"));
    }

    // 失效
    public void invokeSelf(){
        self();
    }

    // 失效
    @Transactional(rollbackFor = Exception.class)
    public void multiThread(){
        new Thread(()->{
            this.save(generateBadTransaction("multiThread"));
            int i = 1 / 0;
        }).start();
    }

    @Transactional(rollbackFor = Exception.class)
    private void self(){
        this.save(generateBadTransaction("Selfffff"));
        int i = 1 / 0;
    }

    private BadTransaction generateBadTransaction(String prefix) {
        BadTransaction badTransaction = new BadTransaction();
        badTransaction.setName(prefix + UUID.randomUUID().toString());
        return badTransaction;
    }
}




