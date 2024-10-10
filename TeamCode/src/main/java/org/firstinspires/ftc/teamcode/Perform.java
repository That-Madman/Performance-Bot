package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Showtime!")
public class Perform extends OpMode {
	Performance perf = new Performance();

	boolean fieldRel = false;
	boolean aHeld = false;

	@Override
	public void init() {
		perf.init(hardwareMap);
	}

	@Override
	public void loop() {
		if (gamepad1.a && aHeld) {
			fieldRel = !fieldRel;
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

		aHeld = gamepad1.a;
	}
}
