package top.huajieyu001.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author huajieyu
 * @Date 2026/3/21 18:18
 * @Version 1.0
 * @Description TODO
 */
@AllArgsConstructor
@Getter
public enum MyLockEnum {

    Success(0), Owner(0), NoOwner(-1);

    int value;
}
