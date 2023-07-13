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
class AutoBlueBlue : AutoBase() {
    // asta e dreapta jos si stanga sus terenul nostru dinspre masa cu unelte inspre chestia cu awards
    private val n : Int = 5
    private val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
    private val interConeTakePose = Pose2d(35.0,0.0,Math.toRadians(-92.0)) //private val interConeTakePose = Pose2d(35.0,0.0,Math.toRadians(-92.0))
    private val vecConeTakePose = Vector2d(48.0,-8.0) //private val vecConeTakePose = Vector2d(48.0,-10.0)
    private val tag11 = Vector2d(48.0, 22.0) // cel cu un punct stanga
    private val tag14 = Vector2d(48.0, -23.0) // cel cu 3 puncte dreapta //-20
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
                .addTemporalMarker(2.0){
                    intake.autoReleaseCone()
                    outtake.openSlider()
                    outtake.armDropAuto()
                }
                    //TODO add temporal to open intake
                .build()
        )
        //waitMillis in total 4.7+3.9*3+4.1=20.5
        //start cone 1
        waitMillis(1000)
        outtake.releaseCone()
        waitMillis(1000)
        outtake.takeCone1()
        intake.autoArmTake(1)
        intake.autoOpenSlider()
        waitMillis(1000)
        //take cone 1
        intake.holdCone()
        outtake.releaseCone()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(600)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 2
        intake.autoArmTake(2)
        intake.autoOpenSlider()
        waitMillis(1100) // prob cam mare
        //release cone 1
        outtake.releaseCone() //wait de 1+0.5+0.3+0.8+0.6+0.5+1=4.7
        waitMillis(200)
        //take cone 2
        intake.holdCone()
        outtake.armTake() //TODO make armTake go lower if needed
        outtake.closeSlider()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(600)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 3
        intake.autoArmTake(3)
        intake.autoOpenSlider()
        waitMillis(1100) // prob cam mare
        //release cone 2
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(200)
        //take cone 3
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(600)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 4
        intake.autoArmTake(4)
        intake.autoCone4()
        waitMillis(1100) // prob cam mare
        //release cone 3
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(200)
        //take cone 4
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(600)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        //start cone 5
        intake.autoArmTake(5)
        intake.autoOpenSlider()
        waitMillis(1100) // prob cam mare
        //release cone 4
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1=3.9
        waitMillis(200)
        //take cone 5
        intake.holdCone()
        outtake.armTake()
        outtake.closeSlider()
        waitMillis(500)
        intake.armDrop()
        waitMillis(300)
        intake.closeSlider()
        waitMillis(800)
        outtake.holdCone()
        waitMillis(600)
        intake.releaseCone()
        waitMillis(500)
        outtake.openSlider()
        outtake.armDropAuto()
        waitMillis(1100) // prob cam mare
        //release cone 5
        outtake.releaseCone() //wait de 0.2+0.5+0.3+0.8+0.6+0.5+1+0.2=4.1
        waitMillis(200)

        //return to init
        outtake.closeSlider()
        intake.autoArmInter()
        outtake.armTake()

        //test pt tag 14 comenteaza dupa
        drive.followTrajectory(
            drive.trajectoryBuilder(drive.poseEstimate)
                .lineToConstantHeading(tag11)
                .build()
        )
        //wait de 0.2
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
    }
}
/*
TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                    .forward(DISTANCE)
                    .turn(Math.toRadians(90))
                    .forward(DISTANCE)
                    .turn(Math.toRadians(90))
                    .forward(DISTANCE)
                    .turn(Math.toRadians(90))
                    .forward(DISTANCE)
                    .turn(Math.toRadians(90))
                    .build();
            drive.followTrajectorySequence(trajSeq);
 */
/*val intakeCones = Thread {
            intake.autoOpenSlider()
            intake.releaseCone()
            intake.autoArmTake(1)
            TimeUnit.MILLISECONDS.sleep(200)
            intake.holdCone()
            TimeUnit.MILLISECONDS.sleep(200)
            intake.armDrop()
            for (i in 2..n) {
                TimeUnit.MILLISECONDS.sleep(2000)
                intake.autoOpenSlider()
                intake.releaseCone()
                intake.autoArmTake(i)
                TimeUnit.MILLISECONDS.sleep(2000) // aici trebuie sa astepte pana celalt brat lasa conul
                intake.holdCone()
                TimeUnit.MILLISECONDS.sleep(200)
                intake.autoArmInter()
                TimeUnit.MILLISECONDS.sleep(400)
                intake.autoArmDrop()
            }
        }
        val outtakeCones = Thread {
            for (i in 1..n) {
                outtake.armTake()
                outtake.releaseCone()
                TimeUnit.MILLISECONDS.sleep(2000) // trebuie sa astepte sa se termine threadul de la intake
                outtake.holdCone()
                TimeUnit.MILLISECONDS.sleep(200)
                intake.releaseCone() // dupa ce lasa intakeul trebuie sa inceapa iar threadul de la intake
                TimeUnit.MILLISECONDS.sleep(200)
                outtake.openSlider()
                TimeUnit.MILLISECONDS.sleep(200)
                outtake.armInter()
                TimeUnit.MILLISECONDS.sleep(200)
                outtake.armDrop()
                TimeUnit.MILLISECONDS.sleep(200)
                outtake.releaseCone()
                outtake.closeSlider()
            }
        }
        intakeCones.interrupt()
        outtakeCones.interrupt()

        outtakeCones.start()
        intakeCones.start()

        intakeCones.join(20000) // time in milisecs to wait until closing thread to start parking in case something breaks commented for early tests
        outtakeCones.join(20000)

        intakeCones.interrupt()
        outtakeCones.interrupt()*/