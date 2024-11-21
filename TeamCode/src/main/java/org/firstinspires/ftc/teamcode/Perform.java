package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Showtime!")
public class Perform extends OpMode {
	private Performance perf;

	private boolean fieldRel;
	private boolean aHeld;
	private boolean yHeld;

	private byte resetTimes;

	@Override
	public void init () {
		perf = new Performance (hardwareMap);
	}

	@Override
	public void loop() {
		if (gamepad1.a && !aHeld) {
			fieldRel = !fieldRel;
		}

		if (gamepad1.y && !yHeld) {
			perf.resetAngle();
			++resetTimes;
		}

		if (fieldRel) {
			perf.driveFieldRelative(
					-gamepad1.left_stick_y,
					gamepad1.left_stick_x,
					gamepad1.right_stick_x
			);
		} else {
			perf.drive(
					-gamepad1.left_stick_y,
					gamepad1.left_stick_x,
					gamepad1.right_stick_x
			);
		}

		telemetry.addData("True North is: ", fieldRel);
		if (0 != resetTimes) {
			telemetry.addData("Reset IMU times", resetTimes);
		}

		aHeld = gamepad1.a;
		yHeld = gamepad1.y;
	}
}
