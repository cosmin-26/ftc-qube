package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import java.lang.Exception

@TeleOp
class TestServo: LinearOpMode() {
    override fun runOpMode() {
        val runtime = ElapsedTime()

        telemetry.addData("Status", "Initialized")
        telemetry.update()
        val hwMap = hardwareMap

        val servoTest = hwMap.servo["outtakeTest1"] ?: throw Exception("Failed to find servo outtakeTest1")
        val servoTest2 = hwMap.servo["outtakeTest2"] ?: throw Exception("Failed to find servo outtakeTest2")
        servoTest.position = 0.0
        servoTest2.position= 0.0

        waitForStart()
        runtime.reset()

        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)
        while (opModeIsActive()) {
            if(gp1.checkToggle(Gamepad.Button.DPAD_UP))
            {
                servoTest.position += 0.01
            }
            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN))
            {
                servoTest.position -= 0.01
            }
            if(gp1.checkToggle(Gamepad.Button.X))
                servoTest.position = 0.78
            if(gp2.checkToggle(Gamepad.Button.DPAD_UP))
            {
                servoTest2.position += 0.01
            }

            if(gp2.checkToggle(Gamepad.Button.DPAD_DOWN))
            {
                servoTest2.position -= 0.01
            }
            if(gp2.checkToggle(Gamepad.Button.X))
                servoTest2.position = 0.78
            telemetry.addData("Status", servoTest.position)
            telemetry.addData("Status", servoTest2.position)
            telemetry.update()
        }
    }
}