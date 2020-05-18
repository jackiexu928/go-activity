package com.jackie.goactivity.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-17
 */
@Data
@Document(collection = "activity_record")
@CompoundIndexes({
        @CompoundIndex(name = "openId_activity_idx", def = "{'openId': 1, 'activityId': 1}", unique = true)
})
public class ActivityRecord extends BaseEntity{
    @Id
    private String id;
    /**
     * 用户openId
     */
    private String openId;
    /**
     * 活动ID
     */
    private String activityId;
    /**
     * 参与类型，1-组织者，2-参与者
     */
    private Integer type;
}
