package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.rev.RevTouchSensor
import com.qualcomm.robotcore.eventloop.opmode.Disabled
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

class Outtake(hwMap: HardwareMap) {
    companion object {
        const val OUTTAKE_SLIDER_NO_EXTEND = 0
        const val OUTTAKE_SLIDER_EXTEND_LOW = 0 // de modificat
        const val OUTTAKE_SLIDER_EXTEND_MID = 600 // de modificat
        const val OUTTAKE_SLIDER_EXTEND_HIGH = 1050 //755
        const val auto_slider = 850

        const val OUTTAKE_POWER = 1.0
        var i = 0
        const val servoRotateArmDrop = 0.72 // 82 //cand da drop si face puncte
        const val armDropAuto = 0.90
        const val servoRotateArmTake = 0.08
        const val servoRotationArmTakeMore = 0.05 // 0.07
        //0.09cand ia de la intake //0.18
        const val servoRotateArmBeacon = 0.17
        const val armTakeCone1 = 0.06
        const val armTakeCone2 = 0.06 // 0.15
        const val servoRotateArmInter = 0.44
        const val clawOpen = 0.46
        const val clawClose = 0.95 // 75
    }


    private val outtakeMotor = hwMap.dcMotor["outtakeMotor"] ?: throw Exception("Failed to find motor outtakeMotor")

    var outtakePosition: Int = 0
    var isReleased: Boolean = false
    private val outtakeRotateArm = hwMap.servo["outtakeRotateArm"] ?: throw Exception("Failed to find servo outtakeRotateArm")
    private val outtakeClaw = hwMap.servo["outtakeClaw"] ?: throw Exception("Failed to find servo outtakeClaw")
    init {
        outtakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeMotor.direction = DcMotorSimple.Direction.REVERSE
        outtakeMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        outtakeMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        outtakeMotor.power = 0.0
        outtakePosition = 0

        armTake()
        releaseCone()
        isReleased = true
    }

    fun openSlider() {
        outtakePosition = OUTTAKE_SLIDER_EXTEND_HIGH
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER
    }


    fun closeSlider() {
        outtakePosition = OUTTAKE_SLIDER_NO_EXTEND
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER
    }

    fun openLowSlider(){
        outtakePosition = OUTTAKE_SLIDER_EXTEND_LOW
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER
    }
    fun takeCone1(){
        outtakeRotateArm.position= armTakeCone1
    }
    fun takeCone2(){
        outtakeRotateArm.position= armTakeCone2
    }
    fun openMidSlider(){
        outtakePosition = OUTTAKE_SLIDER_EXTEND_MID
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = OUTTAKE_POWER
    }

    fun holdCone(){
        isReleased = false
        outtakeClaw.position = clawClose + i
    }

    fun releaseCone(){
        isReleased = true
        outtakeClaw.position = clawOpen + i
    }

    fun toggleCone() {
        if (isReleased) {
            holdCone()
        } else {
            releaseCone()
        }
    }

    fun armDropAuto(){
        outtakeRotateArm.position = armDropAuto
    }

    fun armBeacon(){
        outtakeRotateArm.position = servoRotateArmBeacon
    }

    fun armTake(){
        outtakeRotateArm.position = servoRotateArmTake
    }

    fun armDrop(){
        outtakeRotateArm.position = servoRotateArmDrop
    }

    fun armInter(){
        outtakeRotateArm.position = servoRotateArmInter
    }
    fun resetPos(){
         i += 20
    }
    fun goDown(){
        outtakeRotateArm.position -= 0.03
    }

    fun goUp(){
        outtakeRotateArm.position += 0.03// 0.03
    }

    fun stop(){
        outtakeMotor.power = 0.0
    }

    fun printPosition(telemetry: Telemetry) {
        telemetry.addLine("Position outtake")
            .addData("Slider 1","%d", outtakeMotor.currentPosition)
    }
    fun moreOuttake()
    {
        outtakeRotateArm.position = servoRotationArmTakeMore
    }

    fun setPower(v: Double) {
        outtakeMotor.power = v
    }

    fun getSliderPositionRelativeToPosLow(): Int {
        return OUTTAKE_SLIDER_EXTEND_LOW-outtakeMotor.currentPosition
    }

    fun getSliderPositionRelativeToPosMid(): Int {
        return OUTTAKE_SLIDER_EXTEND_MID-outtakeMotor.currentPosition
    }

    fun getSliderPositionRelativeToPosHigh(): Int {
        return OUTTAKE_SLIDER_EXTEND_HIGH-outtakeMotor.currentPosition
    }
}