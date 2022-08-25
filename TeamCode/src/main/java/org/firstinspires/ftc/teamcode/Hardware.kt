package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive

class Hardware(val hardwareMap: HardwareMap, val telemetry: Telemetry? = null) {

    companion object {
        val clawCloseStone = 0.7
        val clawCloseCube = 0.95
        val clawOpen = 0.1

        val TICKS_PER_REV = 537.7
        val WHEEL_DIAMETER = 4.0

        // static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
        val TICKS_PER_IN = TICKS_PER_REV / (WHEEL_DIAMETER * Math.PI)
    }

    val drive by lazy { SampleMecanumDrive(hardwareMap) }

    val claw by hardware<Servo>("cw")

    val slider by hardware<DcMotorEx>("sli")
    val arm by hardware<DcMotorEx>("arm")

    fun init() {
        slider.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun forward(power: Double, distance: Double, timeout: Double = 10.0) = driveWithEncoders(
        power, timeout,
        distance, distance, distance, distance,
    )

    fun backwards(power: Double, distance: Double, timeout: Double = 10.0) = driveWithEncoders(
        power, timeout,
        -distance, -distance, -distance, -distance,
    )

    fun turnLeft(power: Double, distance: Double, timeout: Double = 10.0) = driveWithEncoders(
        power, timeout,
        -distance, distance, -distance, distance,
    )

    fun turnRight(power: Double, distance: Double, timeout: Double = 10.0) = driveWithEncoders(
        power, timeout,
        distance, -distance, distance, -distance,
    )

    fun driveWithEncoders(power: Double, timeout: Double, fl: Double, fr: Double, bl: Double, br: Double) {
        val timer = ElapsedTime()

        drive.leftFront.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        drive.leftRear.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        drive.rightFront.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        drive.rightRear.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        drive.leftFront.targetPosition = (fl * TICKS_PER_IN).toInt()
        drive.leftRear.targetPosition = (bl * TICKS_PER_IN).toInt()
        drive.rightFront.targetPosition = (fr * TICKS_PER_IN).toInt()
        drive.rightRear.targetPosition = (br * TICKS_PER_IN).toInt()

        drive.leftFront.mode = DcMotor.RunMode.RUN_TO_POSITION
        drive.leftRear.mode = DcMotor.RunMode.RUN_TO_POSITION
        drive.rightFront.mode = DcMotor.RunMode.RUN_TO_POSITION
        drive.rightRear.mode = DcMotor.RunMode.RUN_TO_POSITION

        drive.leftFront.power = power
        drive.leftRear.power = power
        drive.rightFront.power = power
        drive.rightRear.power = power

        while(
            drive.leftFront.isBusy
            && drive.leftRear.isBusy
            && drive.rightFront.isBusy
            && drive.rightRear.isBusy
            && timer.seconds() < timeout
            && !Thread.currentThread().isInterrupted
        ) {
            telemetry?.addData("fl", drive.leftFront)
            telemetry?.addData("fr", drive.rightFront)
            telemetry?.addData("bl", drive.leftRear)
            telemetry?.addData("br", drive.rightRear)

            telemetry?.update()
        }

        drive.leftFront.power = 0.0
        drive.leftRear.power = 0.0
        drive.rightFront.power = 0.0
        drive.rightRear.power = 0.0

        drive.leftFront.mode = DcMotor.RunMode.RUN_USING_ENCODER
        drive.leftRear.mode = DcMotor.RunMode.RUN_USING_ENCODER
        drive.rightFront.mode = DcMotor.RunMode.RUN_USING_ENCODER
        drive.rightRear.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    inline fun <reified T> hardware(name: String) = lazy { hardwareMap.get(T::class.java, name) }

}