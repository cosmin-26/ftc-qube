package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.hardware.Hardware


@Autonomous
@Config
class AutoGoLeftLess: AutoBase() {
    // asta e dreapta jos si stanga sus terenul nostru dinspre masa cu unelte inspre chestia cu awards
    private val n : Int = 5
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
    private val interConeTakePose = Pose2d(0.5,25.0,Math.toRadians(0.0))
    private val interConeTakePose2 = Pose2d(0.0,25.0,Math.toRadians(0.0))
    val i = 1
    override fun preInit() {
        super.preInit()
        telemetry.addLine("Initializing...")
        telemetry.update()
        drive.poseEstimate = startPose
    }

    override fun Hardware.run() {
        telemetry.addLine(String.format("\nAm detectat un tag ID=%d", tagOfInterest?.id))
        telemetry.update()
        intake.autoArmInter()

            drive.trajectorySequenceBuilder(Pose2d(35.70, -62.83, Math.toRadians(90.00)))
                .splineTo(Vector2d(30.32, -33.51), Math.toRadians(100.41))
                .splineTo(Vector2d(41.68, -8.18), Math.toRadians(65.83))
                .build()
    }
}
