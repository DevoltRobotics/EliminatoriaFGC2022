package org.firstinspires.ftc.teamcode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.Hardware

@Autonomous(name = "Autonomo Rojo", group = "####", preselectTeleOp = "TeleOp")
class AutonomoRojo : BaseAutonomo() {

    override fun run() {
        hardware.claw.position = Hardware.clawOpen

        hardware.forward(0.4, 60.0)
        hardware.turnLeft(0.4, 25.0)
        hardware.forward(0.4, 10.0)

        hardware.slider.power = 0.7
        sleep(1800)
        hardware.slider.power = 0.0
        hardware.arm.power = 0.7
        sleep(1800)
        hardware.arm.power = 0.0

        hardware.forward(0.4, 20.0)

        hardware.claw.position = Hardware.clawCloseCube

        sleep(2000)

        hardware.backwards(0.4, 10.0)

        hardware.slider.power = -0.7
        sleep(1300)
        hardware.slider.power = 0.0
        hardware.arm.power = -0.7
        sleep(1800)
        hardware.arm.power = 0.0

        hardware.backwards(0.5, 10.0)
        hardware.turnRight(0.4, 21.0)
        hardware.backwards(0.6, 30.0)
    }

}