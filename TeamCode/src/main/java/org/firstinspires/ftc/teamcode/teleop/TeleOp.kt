package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.Hardware

@TeleOp(name = "TeleOp")
class TeleOp : LinearOpMode() {

    val hardware by lazy { Hardware(hardwareMap) }

    val drive get() = hardware.drive

    override fun runOpMode() {
        hardware.init()

        telemetry.addData("[!]", "Con todo y pa delante hasta Suiza")
        telemetry.update()

        waitForStart()

        while(opModeIsActive()) {
            /* MECANUMS */

            val pose = drive.poseEstimate

            val turbo = 1.0 - ((if(gamepad1.left_trigger > 0.2) {
                gamepad1.left_trigger
            } else gamepad1.right_trigger) * 0.8)

            val input = Vector2d(
                (-gamepad1.left_stick_y).toDouble(),
                (-gamepad1.left_stick_x).toDouble()
            ).rotated(-pose.heading)

            drive.setWeightedDrivePower(
                Pose2d(
                    input.x * turbo, input.y * turbo, (-gamepad1.right_stick_x).toDouble() * turbo
                )
            )

            if(gamepad1.dpad_up) {
                drive.poseEstimate = Pose2d(0.0, 0.0, 0.0)
            }

            /* SLIDERS Y BRAZO */

            hardware.slider.power = (-gamepad2.left_stick_y).toDouble()
            hardware.arm.power = (-gamepad2.right_stick_x).toDouble() * 0.6 + 0.07

            /* CLAW */

            if(gamepad2.a) {
                hardware.clawLeft.position = Hardware.clawLeftClose
                hardware.clawRight.position = Hardware.clawRightClose
            } else if(gamepad2.b) {
                hardware.clawLeft.position = Hardware.clawLeftOpen
                hardware.clawRight.position = Hardware.clawRightOpen
            }

            drive.update()

            telemetry.addData("heading", pose.heading)
            telemetry.update()
        }
    }

}