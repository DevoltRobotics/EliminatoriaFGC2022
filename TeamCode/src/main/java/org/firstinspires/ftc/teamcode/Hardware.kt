package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive

class Hardware(val hardwareMap: HardwareMap) {

    companion object {
        val clawClose = 0.6
        val clawOpen = 0.0
    }

    val drive by lazy { SampleMecanumDrive(hardwareMap) }

    val claw by hardware<Servo>("cw")

    val slider by hardware<DcMotorEx>("sli")
    val arm by hardware<DcMotorEx>("arm")

    fun init() {
        slider.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        claw.position = clawClose
    }

    inline fun <reified T> hardware(name: String) = lazy { hardwareMap.get(T::class.java, name) }

}