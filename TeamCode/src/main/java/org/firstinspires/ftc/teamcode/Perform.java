package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Showtime!")
public class Perform extends OpMode {
	Performance perf = new Performance();

	@Override
	public void init() {
		perf.init(hardwareMap);
	}

	@Override
	public void loop() {
		perf.driveFieldRelative(
				-gamepad1.left_stick_y,
				gamepad1.left_stick_x,
				gamepad1.right_stick_x
		);
	}
}
