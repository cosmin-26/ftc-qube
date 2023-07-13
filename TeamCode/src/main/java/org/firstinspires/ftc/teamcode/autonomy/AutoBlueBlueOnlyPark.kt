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
class AutoBlueBlueOnlyPark  : AutoBase() {
    //TODO de pus pus o auto care e autobluebluenopreload2 care are y mai mult pe - cu cam 2 3 poz + auto doar parcat
    // asta e dreapta jos si stanga sus terenul nostru dinspre masa cu unelte inspre chestia cu awards
    private val n : Int = 5
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
    private val interConeTakePose = Pose2d(35.0,0.0,Math.toRadians(-92.0)) //private val interConeTakePose = Pose2d(35.0,0.0,Math.toRadians(-92.0))
    private val vecConeTakePose = Vector2d(48.0,10.0) //private val vecConeTakePose = Vector2d(48.0,-10.0)
    private val tag11 = Vector2d(48.0, 22.0) // cel cu un punct stanga
    private val tag14 = Vector2d(48.0, -20.0) // cel cu 3 puncte dreapta
    private val tag19 = Vector2d(48.0, 0.0) // cel cu 2 puncte fata
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
        drive.followTrajectory(
            drive.trajectoryBuilder(startPose)
                .splineToSplineHeading(interConeTakePose,Math.toRadians(0.0))
                .splineToConstantHeading(vecConeTakePose,Math.toRadians(0.0))
                .build()
        )
        waitMillis(1000)
        if (tagOfInterest?.id == 11) { // in stanga (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineTo(tag11)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        if (tagOfInterest?.id == 14) { // in dreapta (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineTo(tag14)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        if (tagOfInterest?.id == 19) { // in fata (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineToConstantHeading(tag19)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        intake.autoArmInter()
        waitMillis(3000)
        intake.holdCone()
        waitMillis(3000)
        outtake.holdCone()
        waitMillis(3000)
        outtake.armInter()
        waitMillis(3000)
        outtake.armDrop()
        waitMillis(3000)
        outtake.armInter()
        waitMillis(3000)
        outtake.armDrop()
        waitMillis(3000)
        outtake.armInter()
        waitMillis(3000)
        outtake.armDrop()
        waitMillis(3000)
    }
}