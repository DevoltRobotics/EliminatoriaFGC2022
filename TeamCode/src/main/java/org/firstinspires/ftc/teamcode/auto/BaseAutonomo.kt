package org.firstinspires.ftc.teamcode.auto

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Hardware
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence

abstract class BaseAutonomo : LinearOpMode() {

    val hardware by lazy { Hardware(hardwareMap, telemetry) }

    val drive get() = hardware.drive

    override fun runOpMode() {
        hardware.init()

        telemetry.addData("!", "CON TODO CHAVOS");
        telemetry.update()

        waitForStart()

        run()
    }

    abstract fun run()

}