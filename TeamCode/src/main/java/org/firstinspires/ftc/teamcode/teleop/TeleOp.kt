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

            val input = Vector2d(
                (-gamepad1.left_stick_y).toDouble(),
                (-gamepad1.left_stick_x).toDouble()
            ).rotated(-pose.heading)

            drive.setWeightedDrivePower(
                Pose2d(
                    input.x, input.y, (-gamepad1.right_stick_x).toDouble()
                )
            )

            if(gamepad1.dpad_up) {
                drive.poseEstimate = Pose2d(0.0, 0.0, 0.0)
            }

            /* SLIDERS Y BRAZO */

            hardware.slider.power = (-gamepad2.left_stick_y).toDouble()
            hardware.arm.power = (-gamepad2.right_stick_y).toDouble()

            /* INTAKE */

            hardware.intake.power =
                if(gamepad2.a) {
                    1.0
                } else if(gamepad2.b) {
                    -1.0
                } else 0.0

            drive.update()

            telemetry.addData("heading", pose.heading)
            telemetry.update()
        }
    }

}