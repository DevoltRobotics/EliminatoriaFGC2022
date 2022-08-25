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

            val triggerValue = if(gamepad1.left_trigger > 0.2) {
                gamepad1.left_trigger
            } else gamepad1.right_trigger

            val turbo = 1.0 - (triggerValue * 0.6)

            telemetry.addData("turbo", turbo)

            val input = Vector2d(
                (-gamepad1.left_stick_y).toDouble() * turbo,
                (-gamepad1.left_stick_x).toDouble() * turbo
            ).rotated(-pose.heading)

            drive.setWeightedDrivePower(
                Pose2d(
                    input.x, input.y, (-gamepad1.right_stick_x).toDouble() * turbo
                )
            )

            if(gamepad1.dpad_up) {
                drive.poseEstimate = Pose2d(0.0, 0.0, 0.0)
            }

            /* SLIDERS Y BRAZO */

            hardware.slider.power = (-gamepad2.left_stick_y).toDouble()
            hardware.arm.power = (-gamepad2.right_stick_y).toDouble() * 0.6 + 0.07

            /* CLAW */

            if(gamepad2.a) {
                hardware.claw.position = Hardware.clawCloseStone
            } else if(gamepad2.y) {
                hardware.claw.position = Hardware.clawCloseCube
            } else if(gamepad2.b) {
                hardware.claw.position = Hardware.clawOpen
            }

            drive.update()

            telemetry.addData("heading", pose.heading)
            telemetry.addData("claw", hardware.claw.position)

            telemetry.update()
        }
    }

}