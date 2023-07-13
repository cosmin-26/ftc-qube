package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.DriveMotors
import org.firstinspires.ftc.teamcode.hardware.Hardware
import java.lang.Math.atan2

@TeleOp(name = "CompleteDrive", group = "Main")
class CompleteDrive: OpMode() {

    override fun preInit() {
    }

    override fun preInitLoop() {
        telemetry.addLine("Waiting for start...")
        telemetry.update()
        idle()
    }

    enum class Drive{
        TAKE_CONE,
        TRANSITION,
        OUTTAKE_CONE;
        enum class TransitionState{
            TRANSITION_DROP,
            TRANSITION_TAKE
        }
        enum class LiftState{
            LIFT_START,
            LIFT_LOW,
            LIFT_MEDIUM,
            LIFT_UP,
            LIFT_INTER_UP,
            LIFT_INTER_MEDIUM,
            LIFT_INTER_LOW,
            LIFT_DUMP_UP,
            LIFT_DUMP_MEDIUM,
            LIFT_DUMP_LOW,
        }
    }

    override fun Hardware.run() {
        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)
        var liftTimer = ElapsedTime()
        var i =1
        var ok=0
        liftTimer.reset()

        //TODO modify these values
        //var INTER_TIME_LOW = 0.1
        //var INTER_TIME_MEDIUM = 0.2
        //var INTER_TIME_UP = 1.0
        var DUMP_TIME_LOW = 0.5
        var DUMP_TIME_UP = 0.5
        var DUMP_TIME_MEDIUM = 0.5

        var isReleased = false
        var isTaken = false
        var scale= 0.6

        var state = Drive.TAKE_CONE
        var liftState = Drive.LiftState.LIFT_START
        var transitionState = Drive.TransitionState.TRANSITION_DROP

        waitForStart()
        while(opModeIsActive())
        {
            val power = speed
            val rotPower = rotation

            if(gp1.right_trigger > 0.2)
                hw.motors.move(direction,power*scale,rotPower*scale)
            else {
                hw.motors.move(direction, power, rotPower)
            }
            if(gp1.checkToggle(Gamepad.Button.Y)) {
                outtake.armTake()
            }
            if(gp2.checkToggle(Gamepad.Button.DPAD_UP)) {
                outtake.releaseCone()
            }
            if(gp2.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                outtake.moreOuttake()
                sleep(100)
                outtake.holdCone()
                sleep(200)
                intake.releaseCone()
                intake.tranzitieOK()

            }
            if(gp2.checkToggle(Gamepad.Button.DPAD_RIGHT)) {
                intake.openSlider() //se duce in poz de luat
            }
            if(gp2.checkToggle(Gamepad.Button.DPAD_LEFT)) {
                intake.closeSlider()
            }
            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN)){
                outtake.goDown()
            }
            if(gp1.checkToggle(Gamepad.Button.DPAD_UP)){
                outtake.goUp()
            }
            if(gp1.checkToggle(Gamepad.Button.X)){
                intake.autoArmTake(i)
                i += 1
            }
            if(gp1.checkToggle(Gamepad.Button.A)){
                intake.openSlider()
            }
            if(gp1.checkToggle(Gamepad.Button.B)) {
                outtake.armBeacon()
            }
            when(state)
            {
                Drive.TAKE_CONE -> {
                    if(gp2.checkToggle(Gamepad.Button.B))
                    {
                        isTaken = if(!isTaken) {
                            intake.holdCone()
                            true
                        } else {
                            intake.releaseCone()
                            false
                        }
                    }
                    /** Move Intake Slider for specific position*/
                    if(gp2.checkToggle(Gamepad.Button.A))
                    {
                        intake.armDrop()
                        outtake.armTake()
                        outtake.releaseCone()
                        state = Drive.TRANSITION
                    }
                    if(gp2.right_trigger-gp2.left_trigger > 0.2)
                        intake.moveSlider((gp2.right_trigger-gp2.left_trigger).toDouble())
                }

                Drive.TRANSITION -> {
                    if(gp2.checkToggle(Gamepad.Button.A))
                    {

                        intake.armTake()
                        state = Drive.TAKE_CONE
                    }
                    if(gp2.checkToggle(Gamepad.Button.B))
                    {
                        intake.releaseCone()
                    }
                    if(gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER))
                    {
                        outtake.holdCone()
                        outtake.openSlider()
                        outtake.armDrop()
                        //outtake.armInter()
                        //liftTimer.reset()
                        state = Drive.OUTTAKE_CONE
                        //liftState = Drive.LiftState.LIFT_INTER_UP
                        liftState = Drive.LiftState.LIFT_UP
                    }
                    if(gp2.checkToggle(Gamepad.Button.X))
                    {
                        outtake.holdCone()
                        outtake.openMidSlider()
                        outtake.armDrop()
                        //outtake.armInter()
                        //liftTimer.reset()
                        state = Drive.OUTTAKE_CONE
                        //liftState = Drive.LiftState.LIFT_INTER_MEDIUM
                        liftState = Drive.LiftState.LIFT_MEDIUM
                    }
                    if(gp2.checkToggle(Gamepad.Button.Y))
                    {
                        outtake.holdCone()
                        outtake.openLowSlider()
                        outtake.armDrop()
                        //outtake.armInter()
                        //liftTimer.reset()
                        state = Drive.OUTTAKE_CONE
                        //liftState = Drive.LiftState.LIFT_INTER_LOW
                        liftState = Drive.LiftState.LIFT_LOW
                    }
                }

                Drive.OUTTAKE_CONE -> {
                    /** This is for changing from one level to another*/
                    if (gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        outtake.openSlider()
                        liftState = Drive.LiftState.LIFT_UP
                    }
                    if (gp2.checkToggle(Gamepad.Button.X)) {
                        outtake.openMidSlider()
                        liftState = Drive.LiftState.LIFT_MEDIUM
                    }
                    if (gp2.checkToggle(Gamepad.Button.Y)) {
                        outtake.openLowSlider()
                        liftState = Drive.LiftState.LIFT_LOW
                    }
                    if (gp2.checkToggle(Gamepad.Button.A)) {
                        outtake.toggleCone()
                    }
                    /** This is for changing from each level to intake position*/
                    when (liftState) {
                        /*Drive.LiftState.LIFT_INTER_LOW -> {
                            if (liftTimer.seconds() >= INTER_TIME_LOW) {
                                outtake.armDrop()
                                liftState = Drive.LiftState.LIFT_LOW
                            }
                        }
                        Drive.LiftState.LIFT_INTER_MEDIUM -> {
                            if (liftTimer.seconds() >= INTER_TIME_MEDIUM) {
                                outtake.armDrop()
                                liftState = Drive.LiftState.LIFT_MEDIUM
                            }
                        }
                        Drive.LiftState.LIFT_INTER_UP -> {
                            if (liftTimer.seconds() >= INTER_TIME_UP) {
                                outtake.armDrop()
                                liftState = Drive.LiftState.LIFT_UP
                            }
                        }*/
                        Drive.LiftState.LIFT_LOW -> {
                            if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                                outtake.releaseCone()
                                ok=0
                                liftState = Drive.LiftState.LIFT_DUMP_LOW
                            }
                        }
                        Drive.LiftState.LIFT_MEDIUM -> {
                            if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                                outtake.releaseCone()
                                ok=0
                                liftState = Drive.LiftState.LIFT_DUMP_MEDIUM

                            }
                        }
                        Drive.LiftState.LIFT_UP -> {
                            if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                                outtake.releaseCone()
                                ok=0
                                liftState = Drive.LiftState.LIFT_DUMP_UP

                            }
                        }
                        Drive.LiftState.LIFT_DUMP_LOW -> {
                            outtake.closeSlider()
                            if(ok==0) {
                                liftTimer.reset()
                                ok=1
                            }
                            if (liftTimer.seconds() >= DUMP_TIME_LOW) {
                                outtake.armTake()
                                intake.armTake()
                                state = Drive.TAKE_CONE
                            }
                        }
                        Drive.LiftState.LIFT_DUMP_MEDIUM -> {
                            outtake.closeSlider()
                            if(ok==0) {
                                liftTimer.reset()
                                ok=1
                            }
                            if (liftTimer.seconds() >= DUMP_TIME_MEDIUM) {
                                outtake.armTake()
                                intake.armTake()
                                state = Drive.TAKE_CONE
                            }
                        }
                        Drive.LiftState.LIFT_DUMP_UP -> {
                            outtake.closeSlider()
                            if(ok==0) {
                                liftTimer.reset()
                                ok=1
                            }
                            if (liftTimer.seconds() >= DUMP_TIME_UP) {
                                outtake.armTake()
                                intake.armTake()
                                state = Drive.TAKE_CONE
                            }
                        }
                    }
                }
            }
            intake.printPosition(telemetry)
            outtake.printPosition(telemetry)
            //telemetry.addData("State",state)
            telemetry.addData("Lift State",liftState)
            //telemetry.addData("Lol", liftTimer)
            telemetry.addData("Lift time", liftTimer.seconds())
            motors.printPosition(telemetry)
            telemetry.update()
        }
    }

    ///The direction in which the robot is translating
    private val direction: Double
        get() {
            val x = gamepad1.left_stick_x.toDouble()
            val y = -gamepad1.left_stick_y.toDouble()

            return atan2(y, x) / Math.PI * 180.0 - 90.0
        }

    /// Rotation around the robot's Z axis.
    private val rotation: Double
        get() = -gamepad1.right_stick_x.toDouble()

    /// Translation speed.
    private val speed: Double
        get() {
            val x = gamepad1.left_stick_x.toDouble()
            val y = gamepad1.left_stick_y.toDouble()

            return Math.sqrt((x * x) + (y * y))
        }
}