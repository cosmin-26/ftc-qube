package org.firstinspires.ftc.teamcode.autonomy

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.Hardware
import org.firstinspires.ftc.teamcode.waitMillis
import java.util.concurrent.TimeUnit

@Autonomous
@Config
class AutoGoLeftMore:  AutoBase() {
    // asta e dreapta jos si stanga sus terenul nostru dinspre masa cu unelte inspre chestia cu awards
    private val n : Int = 5
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
    private val interConeTakePose = Pose2d(0.5,30.0,Math.toRadians(0.0))
    private val interConeTakePose2 = Pose2d(0.0,30.0,Math.toRadians(0.0))
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
        drive.followTrajectory(
            drive.trajectoryBuilder(startPose)
                .lineToLinearHeading(interConeTakePose)
                .build()
        )
    }
}
