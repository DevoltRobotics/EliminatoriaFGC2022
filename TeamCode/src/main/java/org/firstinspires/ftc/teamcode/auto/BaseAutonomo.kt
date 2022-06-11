package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Hardware
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence

abstract class BaseAutonomo : LinearOpMode() {

    val hardware by lazy { Hardware(hardwareMap) }

    val drive get() = hardware.drive

    override fun runOpMode() {
        hardware.init()

        waitForStart()

        drive.followTrajectorySequenceAsync(sequence())

        while(opModeIsActive()) {
            drive.update()
        }
    }

    abstract fun sequence(): TrajectorySequence

}