package io.softwarecrafters.academy.exercises;

public class VideoSurveillance {

    private MotionSensor motionSensor;
    private VideoRecorder videoRecorder;

    public VideoSurveillance(MotionSensor motionSensor, VideoRecorder videoRecorder) {
        this.motionSensor = motionSensor;
        this.videoRecorder = videoRecorder;
    }

    public void start() {
        try {
            while (motionSensor.isDetectingMotion()) {
                videoRecorder.startRecording();
                waitOneSecond();
            }
            videoRecorder.stopRecording();
        } catch (Exception exception) {
            videoRecorder.stopRecording();
        }
    }

    private static void waitOneSecond() throws InterruptedException {
        Thread.sleep(1000);
    }
}
