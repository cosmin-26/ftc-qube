package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.hardware.Gamepad

class Gamepad(private val gp: Gamepad) {
    enum class Button {
        A,
        B,
        X,
        Y,
        START,
        LEFT_BUMPER,
        RIGHT_BUMPER,
        DPAD_UP,
        DPAD_DOWN,
        DPAD_RIGHT,
        DPAD_LEFT,
        RIGHT_STICK_BUTTON,
        LEFT_STICK_BUTTON
    }

    private var lastStates = Button.values().map { it to false }.toMap().toMutableMap()

    val left_trigger
        get() = gp.left_trigger

    val right_trigger
        get() = gp.right_trigger

    val left_bumper
        get() = gp.left_bumper

    val right_bumper
        get() = gp.right_bumper

    val left_stick_x
        get() = gp.left_stick_x

    val left_stick_y
        get() = gp.left_stick_y

    val right_stick_x
        get() = gp.right_stick_x

    val right_stick_y
        get() = gp.right_stick_y

    val dpad_up
        get() = gp.dpad_up

    val dpad_down
        get() = gp.dpad_down

    val dpad_right
        get() = gp.dpad_right

    val dpad_left
        get() = gp.dpad_left

    fun checkHold(button: Button): Boolean =
            when (button) {
                Button.A -> gp.a
                Button.B -> gp.b
                Button.X -> gp.x
                Button.Y -> gp.y
                Button.START -> gp.start
                Button.LEFT_BUMPER -> gp.left_bumper
                Button.RIGHT_BUMPER -> gp.right_bumper
                Button.DPAD_UP -> gp.dpad_up
                Button.DPAD_DOWN -> gp.dpad_down
                Button.DPAD_RIGHT -> gp.dpad_right
                Button.DPAD_LEFT -> gp.dpad_left
                Button.RIGHT_STICK_BUTTON -> gp.right_stick_button
                Button.LEFT_STICK_BUTTON -> gp.left_stick_button
            }

    fun checkToggle(button : Button): Boolean {
        val pressed = checkHold(button)
        val ok = pressed && (lastStates[button] != pressed)
        lastStates[button] = pressed
        return ok
    }
}
