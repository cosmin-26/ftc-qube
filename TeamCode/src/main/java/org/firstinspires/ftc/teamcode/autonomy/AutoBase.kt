package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.OpenCV.AprilTagDetectionPipeline
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.openftc.apriltag.AprilTagDetection
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation

@Config
abstract class AutoBase : org.firstinspires.ftc.teamcode.OpMode() {
    val drive : SampleMecanumDrive by lazy {
        SampleMecanumDrive(hardwareMap)
    }

    val intakePower = -1.0

    var aprilTagDetectionPipeline: AprilTagDetectionPipeline ?= null

    val FEET_PER_METER = 3.28084

    val fx = 578.272
    val fy = 578.272
    val cx = 402.145
    val cy = 221.506

    val tagsize = 0.166

    val left = 11
    val mid = 19
    val right = 14

    override fun preInit() {
        var camera: OpenCvCamera? = null
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier(
            "cameraMonitorViewId",
            "id",
            hardwareMap.appContext.packageName
        )
        aprilTagDetectionPipeline = AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy)
        camera = OpenCvCameraFactory.getInstance().createWebcam(
            hardwareMap.get(
                WebcamName::class.java, "Webcam 1"
            ), cameraMonitorViewId
        )
        camera.setPipeline(aprilTagDetectionPipeline)
        camera.openCameraDeviceAsync(object : AsyncCameraOpenListener {
            override fun onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT)
            }

            override fun onError(errorCode: Int) {}
        })
        telemetry.msTransmissionInterval = 50
    }
    var tagOfInterest: AprilTagDetection ?= null
    override fun preInitLoop() {

        val currentDetections: ArrayList<AprilTagDetection> = aprilTagDetectionPipeline!!.getLatestDetections()

        if (currentDetections.size != 0) {
            var tagFound = false
            for (tag in currentDetections) {
                if (tag.id == left || tag.id == mid || tag.id == right) {
                    tagOfInterest = tag
                    tagFound = true
                    break
                }
            }
            if (tagFound) {
                telemetry.addLine("Tag of interest is in sight!\n\nLocation data:")
                telemetry.addLine(String.format("\nDetected tag ID=%d", tagOfInterest?.id))

            } else {
                telemetry.addLine("Don't see tag of interest :(")
                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)")
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:")
                    telemetry.addLine(String.format("\nDetected tag ID=%d", tagOfInterest?.id))
                }
            }
        } else {
            telemetry.addLine("Don't see tag of interest :(")
            if (tagOfInterest == null) {
                telemetry.addLine("(The tag has never been seen)")
            } else {
                telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:")
                telemetry.addLine(String.format("\nDetected tag ID=%d", tagOfInterest?.id))
            }
        }

        telemetry.update()
        sleep(20)
    }

    /**
     * Camera related code
     * -------------------------------------------------------------
     * */

    val webcam: OpenCvCamera by lazy {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "Webcam 1"), cameraMonitorViewId)
    }

}