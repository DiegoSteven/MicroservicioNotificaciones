package com.example.repositories;

import com.example.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    List<NotificationModel> findByType(String type);

    List<NotificationModel> findByTypeAndAlwaysTrue(String type);

    @Query(value = """
        SELECT n.* FROM tc_notifications n
        INNER JOIN tc_user_notification un ON n.id = un.notificationid
        WHERE un.userid = :userId
    """, nativeQuery = true)
    List<NotificationModel> findByUserId(@Param("userId") Long userId);

    @Query(value = """
        SELECT n.* FROM tc_notifications n
        INNER JOIN tc_device_notification dn ON n.id = dn.notificationid
        WHERE dn.deviceid = :deviceId
    """, nativeQuery = true)
    List<NotificationModel> findByDeviceId(@Param("deviceId") Long deviceId);

    @Query(value = """
        SELECT n.* FROM tc_notifications n
        INNER JOIN tc_group_notification gn ON n.id = gn.notificationid
        WHERE gn.groupid = :groupId
    """, nativeQuery = true)
    List<NotificationModel> findByGroupId(@Param("groupId") Long groupId);

    @Query(value = """
                SELECT EXISTS (
                    SELECT 1 FROM tc_user_notification
                    WHERE userid = :userId AND notificationid = :notificationId
                )
            """, nativeQuery = true)
    int isLinkedToUser(@Param("userId") Long userId, @Param("notificationId") Long notificationId);
}
