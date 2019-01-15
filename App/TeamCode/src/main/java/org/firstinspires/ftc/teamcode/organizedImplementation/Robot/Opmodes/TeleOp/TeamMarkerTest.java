package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp (name = "TestingTeamMarker", group = "TeleOp")
//@Disabled
public class TeamMarkerTest extends LinearOpMode {

    private Servo push_servo;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        push_servo = hardwareMap.servo.get("push_servo");

        telemetry.addData("Mapped", "mapping complete");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()){
            if(gamepad1.right_trigger != 0){
                push_servo.setPosition(0);
            }else if (gamepad1.left_trigger != 0){
                push_servo.setPosition(1);
            }else{
                push_servo.setPosition(0.5);
            }
            telemetry.addData("Servo Position: ", push_servo.getPosition());
            telemetry.update();
            //off - season testing
        }
    }
}
