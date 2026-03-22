package top.huajieyu001.postgres.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @TableName bad_trans
 */
@TableName(value ="bad_trans")
@Data
public class BadTrans {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private LocalDateTime createTime;
}