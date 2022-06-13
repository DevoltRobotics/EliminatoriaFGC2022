package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive

class Hardware(val hardwareMap: HardwareMap) {

    companion object {
        val clawLeftClose = 0.3
        val clawRightClose = 0.9

        val clawLeftOpen = 0.4
        val clawRightOpen = 0.8

        val clawLeftWideOpen = 0.6
        val clawRightWideOpen = 0.6
    }

    val drive by lazy { SampleMecanumDrive(hardwareMap) }

    val clawRight by hardware<Servo>("cr")
    val clawLeft by hardware<Servo>("cl")

    val slider by hardware<DcMotorEx>("sli")
    val arm by hardware<DcMotorEx>("arm")

    fun init() {
        slider.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        clawLeft.position = clawLeftClose
        clawRight.position = clawRightClose
    }

    inline fun <reified T> hardware(name: String) = lazy { hardwareMap.get(T::class.java, name) }

}