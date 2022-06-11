package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive

class Hardware(val hardwareMap: HardwareMap) {

    val drive by lazy { SampleMecanumDrive(hardwareMap) }

    val intake by hardware<DcMotorEx>("in")

    val slider by hardware<DcMotorEx>("sli")
    val arm by hardware<DcMotorEx>("arm")

    fun init() {

    }

    inline fun <reified T> hardware(name: String) = lazy { hardwareMap.get(T::class.java, name) }

}