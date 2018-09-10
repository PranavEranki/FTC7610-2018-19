/*
Copyright (c) 2016 Robert Atkinson
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Final Tele-Op", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class TeleOp_Final extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor     leftMotor;
    private DcMotor     rightMotor;



    private double leftPower = 0;
    private double rightPower = 0;
    private int leftCoefficient = 1;
    private int rightCoefficient = 1;

    private final double accelerationSpeed = 0.01;
    private final double decelerationSpeed = 0.05;

    private final double driveSpeed = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotor  = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");


        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        openServo();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            // Driving
            if (gamepad1.right_stick_y < 0) {
                if (rightPower < 0) rightPower = limitIncrement(rightPower, decelerationSpeed, 0);
                else                rightPower = limitIncrement(rightPower, accelerationSpeed, driveSpeed);
            } else if (gamepad1.right_stick_y > 0) {
                if (rightPower < 0) rightPower = limitDecrement(rightPower, accelerationSpeed, driveSpeed);
                else                rightPower = limitDecrement(rightPower, decelerationSpeed, 0);
            } else {
                if (rightPower < 0) rightPower = limitIncrement(rightPower, decelerationSpeed, 0);
                else                rightPower = limitDecrement(rightPower, decelerationSpeed, 0);
            }

            if (gamepad1.left_stick_y < 0) {
                if (leftPower < 0) leftPower = limitIncrement(leftPower, decelerationSpeed, 0);
                else               leftPower = limitIncrement(leftPower, accelerationSpeed, driveSpeed);
            } else if (gamepad1.left_stick_y > 0) {
                if (leftPower < 0) leftPower = limitDecrement(leftPower, accelerationSpeed, driveSpeed);
                else               leftPower = limitDecrement(leftPower, decelerationSpeed, 0);
            } else {
                if (leftPower < 0) leftPower = limitIncrement(leftPower, decelerationSpeed, 0);
                else               leftPower = limitDecrement(leftPower, decelerationSpeed, 0);
            }

            leftMotor.setPower(-0.5 * gamepad1.left_stick_y);
            rightMotor.setPower(-0.5 * gamepad1.right_stick_y);
        }
    }

    private double limitIncrement(double initial, double inc, double limit) {
        initial += inc;
        if (initial > limit){
            return limit;
        }
        else {
            return initial;
        }
    }

    private double limitDecrement(double initial, double dec, double limit) {
        initial -= dec;
        if (initial < limit) {
            return limit;
        }
        else {
            return initial;
        }
    }

}

// swapnil test