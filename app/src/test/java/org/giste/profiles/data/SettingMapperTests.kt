package org.giste.profiles.data

import org.giste.profiles.domain.SettingType
import org.giste.profiles.domain.VolumeSetting
import org.giste.profiles.domain.VolumeType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SettingMapperTests {
    private val volumeMediaSetting = VolumeSetting(1, 1, true, VolumeType.MEDIA, 10)
    private val volumeRingSetting = VolumeSetting(2, 1, true, VolumeType.RING, 10)
    private val volumeNotificationSetting = VolumeSetting(3, 1, true, VolumeType.NOTIFICATION, 10)
    private val volumeAlarmSetting = VolumeSetting(4, 1, true, VolumeType.ALARM, 10)

    private val volumeMediaSettingEntity = SettingEntity(1, 1, SettingType.VOLUME_MEDIA, true, 10)
    private val volumeRingSettingEntity = SettingEntity(2, 1, SettingType.VOLUME_RING, true, 10)
    private val volumeNotificationSettingEntity =
        SettingEntity(3, 1, SettingType.VOLUME_NOTIFICATION, true, 10)
    private val volumeAlarmSettingEntity = SettingEntity(4, 1, SettingType.VOLUME_ALARM, true, 10)

    @Test
    fun toEntity_returnsSettingEntity() {
        val mapper = SettingMapper()

        assertThat(mapper.toEntity(volumeMediaSetting), equalTo(volumeMediaSettingEntity))
        assertThat(mapper.toEntity(volumeRingSetting), equalTo(volumeRingSettingEntity))
        assertThat(
            mapper.toEntity(volumeNotificationSetting),
            equalTo(volumeNotificationSettingEntity)
        )
        assertThat(mapper.toEntity(volumeAlarmSetting), equalTo(volumeAlarmSettingEntity))
    }

    @Test
    fun toModel_returnSetting() {
        val mapper = SettingMapper()

        assertThat(mapper.toModel(volumeMediaSettingEntity), equalTo(volumeMediaSetting))
        assertThat(mapper.toModel(volumeRingSettingEntity), equalTo(volumeRingSetting))
        assertThat(
            mapper.toModel(volumeNotificationSettingEntity),
            equalTo(volumeNotificationSetting)
        )
        assertThat(mapper.toModel(volumeAlarmSettingEntity), equalTo(volumeAlarmSetting))
    }
}