package org.firstinspires.ftc.teamcode.firstYearsTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Swapnil Tele-Op", group="Linear Opmode")
public class Swapnil_OpMode extends LinearOpMode {

    private DcMotor leftmotor;

    public DcMotor getLeftmotor() {
        return leftmotor;
    }

    DcMotor right_motor = hardwareMap.dcMotor.get("right_motor");
    DcMotor left_motor = hardwareMap.dcMotor.get("left_motor");

    public void runOpMode(){
        //telemetry.data();
    }

}

