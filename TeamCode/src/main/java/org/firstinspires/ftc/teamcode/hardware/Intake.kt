package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.rev.RevTouchSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.lang.Exception
import java.util.*
import kotlin.math.absoluteValue
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.Hardware
import java.lang.Math.atan2
import java.lang.Thread.sleep

class Intake(hwMap: HardwareMap) {
    companion object {
        const val INTAKE_SLIDER_NO_EXTEND = 0
        const val INTAKE_SLIDER_EXTEND_MAX = 1900
        const val INTAKE_SLIDER_EXTEND_MID = 1050
        const val AUTO_INTAKE_SLIDER = 1370//1220
        const val AUTO_CONE_4 = 1250// 1190
        const val AUTO_CONE_3 = 1250 //1190
        const val AUTO_CONE_5 = 1200 // 1200
        const val AUTO_CONE_2 = 1370 // 1220
        const val INTAKE_POWER = 1.0
        const val servoRotateArmDrop = 0.19 //0.20cand da la outtake-0.13
        const val servoRotateArmInter = 0.40
        const val servoRotateArmTake = 0.76 //cand ia-0.78
        var i = 0
        const val clawOpen = 0.40 // 0.28
        const val clawClose = 0.6 //0.87
        const val autoClawOpen = 0.44
        const val autoServoRotateArm1 = 0.61 // 0.65
        const val autoServoRotateArm2 = 0.65 // 0.68
        const val autoServoRotateArm3 = 0.68
        const val autoServoRotateArm4 = 0.72
        const val autoServoRotateArm5 = 0.76





    }

    private val intakeMotor = hwMap.dcMotor["intakeMotor"] ?: throw Exception("Failed to find motor intakeMotor")

    var intakePosition: Int = 0
    var multiplier: Int = 150

    private val intakeRotateArm = hwMap.servo["intakeRotateArm"] ?: throw Exception("Failed to find servo intakeRotateArm")
    private val intakeClaw = hwMap.servo["intakeClaw"] ?: throw Exception("Failed to find servo intakeClaw")

    init {
        intakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intakeMotor.direction = DcMotorSimple.Direction.FORWARD
        intakeMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        intakeMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        intakeMotor.power = 0.0
        intakePosition = 0

        autoArmInter()
        releaseCone()
    }
    fun autoOpenSlider(){
        intakePosition = AUTO_INTAKE_SLIDER
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }
    fun autoCone2(){
        intakePosition = AUTO_CONE_2
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }

    fun autoCone4(){
        intakePosition = AUTO_CONE_4
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }
    fun autoCone3(){
        intakePosition = AUTO_CONE_3
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }
    fun autoCone5(){
        intakePosition = AUTO_CONE_5
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }
    fun tranzitieOK()
    {
        intakeRotateArm.position =0.10
    }
    fun openSlider() {
        intakePosition = INTAKE_SLIDER_EXTEND_MID
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }

    fun closeSlider() {
        intakePosition = INTAKE_SLIDER_NO_EXTEND
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }

    fun moveSlider(power: Double) {
        intakePosition = (intakeMotor.currentPosition + power*multiplier).toInt()
        if(intakePosition > INTAKE_SLIDER_EXTEND_MAX)
            intakePosition = INTAKE_SLIDER_EXTEND_MAX
        if(intakePosition < INTAKE_SLIDER_NO_EXTEND)
            intakePosition = INTAKE_SLIDER_NO_EXTEND
        intakeMotor.targetPosition = intakePosition
        intakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        intakeMotor.power = INTAKE_POWER
    }
    fun autoArmTake(i: Int){
        when (i) {
            1 -> intakeRotateArm.position = autoServoRotateArm1
            2 -> intakeRotateArm.position = autoServoRotateArm2
            3 -> intakeRotateArm.position = autoServoRotateArm3
            4 -> intakeRotateArm.position = autoServoRotateArm4
            5 -> intakeRotateArm.position = autoServoRotateArm5
        }
    }

    fun autoReleaseCone(){
        intakeClaw.position = autoClawOpen
    }

    fun holdCone(){
        intakeClaw.position = clawClose
    }

    fun releaseCone(){
        intakeClaw.position = clawOpen
    }

    fun armTake(){
        intakeRotateArm.position = servoRotateArmTake
    }

    fun armDrop(){
        intakeRotateArm.position = servoRotateArmInter
        sleep(400)
        intakeRotateArm.position = servoRotateArmDrop
    }

    fun autoArmInter(){
        intakeRotateArm.position = servoRotateArmInter
    }

    fun autoArmDrop(){
        intakeRotateArm.position = servoRotateArmDrop
    }
    fun resetPos(){
        i += 20
    }
    fun stop(){
        intakeMotor.power = 0.0
    }

    fun printPosition(telemetry: Telemetry) {
        telemetry.addLine("Position intake")
            .addData("Slider 1","%d", intakeMotor.currentPosition)
    }

    fun setPower(v: Double) {
        intakeMotor.power = v
    }

    fun getSliderPositionRelativeToPosMax(): Int {
        return INTAKE_SLIDER_EXTEND_MAX-intakeMotor.currentPosition
    }
}