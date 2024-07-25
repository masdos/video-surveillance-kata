package io.softwarecrafters.academy.exercises;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VideoSurveillanceTest {

    @Mock
    VideoRecorder videoRecorder;

    @Mock
    MotionSensor motionSensor;

    @InjectMocks
    VideoSurveillance videoSurveillance;

    @Test
    public void stops_recording_if_no_motion_is_detected() {
        // given
        when(motionSensor.isDetectingMotion()).thenReturn(false);

        // when
        videoSurveillance.start();

        // then
        verify(videoRecorder).stopRecording();
    }

    @Test
    public void starts_recording_if_motion_is_detected() {
        // given
        when(motionSensor.isDetectingMotion()).thenReturn(true).thenReturn(false);

        // when
        videoSurveillance.start();

        // then
        verify(videoRecorder).startRecording();
    }

    @Test
    public void stops_recording_if_motion_has_unexpected_error() {
        // given
        when(motionSensor.isDetectingMotion()).thenThrow(new RuntimeException());

        // when
        videoSurveillance.start();

        // then
        verify(videoRecorder).stopRecording();
    }

    @Test
    public void check_the_status_of_the_motion_sensor_once_per_second_within_three_seconds() {
        // given
        when(motionSensor.isDetectingMotion())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        // when
        videoSurveillance.start();

        // then
        verify(motionSensor, times(4)).isDetectingMotion();
        verify(videoRecorder, times(3)).startRecording();
        verify(videoRecorder, times(1)).stopRecording();
    }
}
