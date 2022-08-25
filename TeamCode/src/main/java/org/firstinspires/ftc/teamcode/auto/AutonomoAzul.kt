package org.firstinspires.ftc.teamcode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.Hardware

@Autonomous(name = "Autonomo Azul", group = "####", preselectTeleOp = "TeleOp")
class AutonomoAzul : BaseAutonomo() {

    override fun run() {
        hardware.claw.position = Hardware.clawCloseCube

        hardware.forward(0.6, 50.0)
        hardware.turnLeft(0.6, 25.0)
        hardware.forward(0.6, 20.0)

        hardware.slider.power = 0.7
        sleep(1100)
        hardware.slider.power = 0.0
        hardware.arm.power = 0.7
        sleep(800)
        hardware.arm.power = 0.0

        hardware.claw.position = Hardware.clawOpen

        sleep(1200)

        hardware.slider.power = -0.7
        sleep(1100)
        hardware.slider.power = 0.0
        hardware.arm.power = -0.7
        sleep(800)
        hardware.arm.power = 0.0

        hardware.backwards(0.6, 20.0)
        hardware.turnRight(0.6, 20.0)
        hardware.backwards(0.6, 50.0)
    }

}