package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class Performance {
	private final DcMotor[] drivebase = {null, null, null, null};

	private IMU imu = null;

	public void init(HardwareMap hwMap) {
		drivebase[0] = hwMap.get(DcMotor.class, "fl");
		drivebase[1] = hwMap.get(DcMotor.class, "fr");
		drivebase[2] = hwMap.get(DcMotor.class, "br");
		drivebase[3] = hwMap.get(DcMotor.class, "bl");

		drivebase[0].setDirection(DcMotorSimple.Direction.REVERSE);
		drivebase[1].setDirection(DcMotorSimple.Direction.FORWARD);
		drivebase[2].setDirection(DcMotorSimple.Direction.FORWARD);
		drivebase[3].setDirection(DcMotorSimple.Direction.REVERSE);

		imu = hwMap.get(IMU.class, "imu");

		imu.initialize(
				new IMU.Parameters(
					new RevHubOrientationOnRobot(
							RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
							RevHubOrientationOnRobot.UsbFacingDirection.UP
					)
				)
			);
	}

	public double getAngle() {
		return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
	}

	private void setPowers(double flp, double frp, double blp, double brp) {
		double maxSpeed = 1;

		maxSpeed = Math.max(maxSpeed, Math.abs(flp));
		maxSpeed = Math.max(maxSpeed, Math.abs(frp));
		maxSpeed = Math.max(maxSpeed, Math.abs(brp));
		maxSpeed = Math.max(maxSpeed, Math.abs(blp));

		flp /= maxSpeed;
		frp /= maxSpeed;
		brp /= maxSpeed;
		blp /= maxSpeed;

		drivebase[0].setPower(flp);
		drivebase[1].setPower(frp);
		drivebase[2].setPower(brp);
		drivebase[3].setPower(blp);
	}

	public void drive(double forward, double right, double rotate) {
		double flp = forward + right + rotate;
		double frp = forward - right + rotate;
		double blp = forward - right + rotate;
		double brp = forward + right - rotate;

		setPowers(flp, frp, blp, brp);
	}
	
	public void driveFieldRelative (double forward, double right, double rotate) {
		double theta = Math.atan2(forward, right);
		double r = Math.hypot(forward, right);

		theta = AngleUnit.normalizeRadians(theta - getAngle());

		double newForward = r * Math.sin(theta);
		double newRight = r * Math.cos(theta);

		drive(newForward, newRight, rotate);
	}
}
