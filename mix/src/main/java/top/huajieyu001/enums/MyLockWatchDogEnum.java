package top.huajieyu001.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author huajieyu
 * @Date 2026/3/21 18:22
 * @Version 1.0
 * @Description TODO
 */
@AllArgsConstructor
@Getter
public enum MyLockWatchDogEnum {
    Success(1), Absent(-1), NoOnwer(-2);

    int value;
}
