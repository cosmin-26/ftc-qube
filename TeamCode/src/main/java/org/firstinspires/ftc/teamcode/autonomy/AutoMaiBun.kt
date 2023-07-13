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
class AutoMaiBun: AutoBase() {
    // asta e` dreapta jos si stanga sus terenul nostru dinspre masa cu unelte inspre chestia cu awards
    private val n : Int = 5
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
    private val interConeTakePose = Pose2d(35.0,2.0,Math.toRadians(-9.0)) // Mathul era -95//trb mai la stanga daca ma uit dinspre conuri    //era -93.0 grade //private val interConeTakePose = Pose2d(35.0,0.0,Math.toRadians(-92.0))
    private val vecR = Vector2d(48.0,-10.0) //-12
    private val vecConeTakePose = Vector2d(44.82,-13.0) //private val vecConeTakePose = Vector2d(48.0,-10.0)
    private val tag11 = Vector2d(13.0, -13.0) // cel cu un punct stanga
    private val tag14 = Vector2d(59.0, -12.1) // cel cu 3 puncte dreapta
    private val tag19 = Vector2d(36.23, -12.1) // cel cu 2 puncte fata
    val i = 1
    override fun preInit() {
        super.preInit()
        telemetry.addLine("Initializing...")
        telemetry.update()
        drive.poseEstimate = Pose2d(36.23,-62.60,Math.toRadians(90.00))
    }

    override fun Hardware.run() {
        telemetry.addLine(String.format("\nAm detectat un tag ID=%d", tagOfInterest?.id))
        telemetry.update()
        outtake.holdCone()
        ///TODO read comments for trajectories
        drive.followTrajectorySequence(
            drive.trajectorySequenceBuilder(Pose2d(36.23,-62.60,Math.toRadians(90.00)))
                .splineTo(Vector2d(35.68,-27.01),Math.toRadians(81.87))//schimb daca intra in bat
                .splineTo(Vector2d(46.82,-13.08),Math.toRadians(-2.00))//schimb daca intra in ground
                .splineToConstantHeading(Vector2d(46.0,-12.3),Math.toRadians(-2.0)) // x41 de testat// schimb dc nu ajunge bine in poz de luat
                .build()
        )
        /*drive.followTrajectory(
            drive.trajectoryBuilder(drive.poseEstimate)
                .splineToConstantHeading(Vector2d(41.0,-10.0),Math.toRadians(-4.0))
                .build()
        )*/
        intake.autoReleaseCone()
        outtake.armInter()
        //waitMillis in total 4.2*4+5.7 = 22.5
        //start cone 1
        intake.autoArmTake(1)
        intake.autoOpenSlider()
        outtake.openSlider()
        waitMillis(200)
        outtake.armDropAuto()
        waitMillis(1100)
        outtake.releaseCone()
        waitMillis(200)
        //take cone 1
        outtake.closeSlider()
        intake.holdCone()
        outtake.takeCone1()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(400)
        intake.autoReleaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 2
        intake.autoArmTake(2)
        intake.autoCone2()
        waitMillis(1100) // prob cam mare
        //release cone 1
        outtake.releaseCone() //wait de 1+0.5+0.3+0.8+0.6+0.5+1=4.7
        waitMillis(300)
        //take cone 2
        intake.holdCone()
        outtake.takeCone2() //TODO make armTake go lower if needed
        outtake.closeSlider()
        waitMillis(600)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(400)
        intake.autoReleaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 3
        intake.autoArmTake(3)
        intake.autoCone3()
        waitMillis(1100) // prob cam mare
        //release cone 2
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(300)
        //take cone 3
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(600)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(400)
        intake.autoReleaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 4
        intake.autoArmTake(4)
        intake.autoCone4()
        waitMillis(1100) // prob cam mare
        //release cone 3
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(300)
        //take cone 4
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(600)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(400)
        intake.autoReleaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 5
        intake.autoArmTake(5)
        intake.autoCone4()
        waitMillis(1100) // prob cam mare
        //release cone 4
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(300)
        //take cone 5
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(600)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(400)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        waitMillis(300)
        outtake.armDropAuto()
        waitMillis(1100) // prob cam mare
        //release cone 5
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1+0.2=4.1
        waitMillis(300)

        //return to init
        outtake.closeSlider()
        intake.autoArmInter()
        outtake.armTake()


        if (tagOfInterest?.id == 11) { // in stanga (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineToConstantHeading(vecConeTakePose)

                    .build()
            )
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineToConstantHeading(tag11)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        else if (tagOfInterest?.id == 14) { // in dreapta (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineTo(tag14)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        else if (tagOfInterest?.id == 19) { // in fata (fata de poz de start)
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineToConstantHeading(tag19)
                    .build()
            )
            telemetry.addLine(String.format("\nAm ajuns la tag ID=%d", tagOfInterest?.id))
            telemetry.update()
        }
        else {
            drive.followTrajectory(
                drive.trajectoryBuilder(drive.poseEstimate)
                    .lineToConstantHeading(tag19)
                    .build()
            )
            telemetry.addLine("\nNu am vazut niciun tag => merg la tag de fata ")
            telemetry.update()

        }
    }
}