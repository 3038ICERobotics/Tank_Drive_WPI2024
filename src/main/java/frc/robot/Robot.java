// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

 import com.revrobotics.CANSparkBase.IdleMode;
 import com.revrobotics.CANSparkLowLevel.MotorType;
 import com.revrobotics.CANSparkMax;
 import com.revrobotics.RelativeEncoder;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;





/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must 3 update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // private static final String kRedAmpAuto = "R-Amp";
  // private static final String kCenterAuto = "Center";
  // private static final String kBlueAmpAuto = "B-Amp";
  // private static final String kRedHumanAuto = "R-Human";
  // private static final String kBlueHumanAuto = "B-Human";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //Declare Joysticks
  private Joystick lJoystick = new Joystick(0);
  private Joystick rJoystick = new Joystick(1);
  //Declaring motors
  CANSparkMax fLeft = new CANSparkMax(25, MotorType.kBrushed);
  CANSparkMax bLeft = new CANSparkMax(10, MotorType.kBrushed);

  
  CANSparkMax fRight = new CANSparkMax(13 , MotorType.kBrushed);
  CANSparkMax bRight = new CANSparkMax(2, MotorType.kBrushed);
  CANSparkMax flyWheel = new CANSparkMax(15, MotorType.kBrushed);
   
  //Encoders
  // RelativeEncoder leftDriveEncoder;
  // RelativeEncoder rightDriveEncoder;

  //Break Beam
 // DigitalInput bBeam = new DigitalInput(9);


  //Diff Drive
  private DifferentialDrive diffDrive;

  //Declaring Encoders
 // double revInch = 42 / (4*3.1415);  

  //multiplier of current value
  //range from -1 to 1
  //How to make it 0 to 1 (-1 = 0, 0 = .5, 1 = 1)
  //multiply value by x axis
  //Make sure not screwed with
  //Ignore everything on z axis > 0
  
  // double driveSpeed = rJoystick.getZ();
  // double throwerSpeed = lJoystick.getZ();

  //Speed Variables
  double driveSpeed;
  boolean driveinverted = false;

  //Auto choice
  double autoChoice;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Choosing Auto
    // m_chooser.setDefaultOption("Center", kCenterAuto);
    // m_chooser.addOption("R-Amp", kRedAmpAuto);
    // m_chooser.addOption("B-Amp", kBlueAmpAuto);
    // m_chooser.addOption("R-Human", kRedHumanAuto);
    // m_chooser.addOption("B-Human", kBlueHumanAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    fRight.restoreFactoryDefaults();
    bLeft.restoreFactoryDefaults();
    fLeft.restoreFactoryDefaults();
    bRight.restoreFactoryDefaults();
  
    //Set back motors to follow front
    bLeft.follow(fLeft);
    bRight.follow(fRight);
    bLeft.setInverted(true);
    fLeft.setInverted(true);
    //Camera
    //CameraServer.startAutomaticCapture();

    //Encoders
    // leftDriveEncoder = fLeft.getEncoder();
    // rightDriveEncoder = bRight.getEncoder();
    
    // leftDriveEncoder.setPositionConversionFactor(2);
    // rightDriveEncoder.setPositionConversionFactor(2);



    //Initiate Diff Drive
    diffDrive = new DifferentialDrive(fRight::set, fLeft::set);
   
    //Add motors to Registry
    // SendableRegistry.addChild(diffDrive, fLeft);
    // SendableRegistry.addChild(diffDrive, fRight);
    // SendableRegistry.addChild(diffDrive, bLeft);
    // SendableRegistry.addChild(diffDrive, bRight);
  
    

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //Put Values into Smart Dashboard
    // SmartDashboard.putNumber("Left Encoder", leftDriveEncoder.getPosition());
    // SmartDashboard.putNumber("Right Encoder", rightDriveEncoder.getPosition());
    SmartDashboard.putNumber("Auto Choice", autoChoice);

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kCenterAuto );
    System.out.println("Auto selected: " + m_autoSelected);

     //Start Encoders at Position Zero
    // leftDriveEncoder.setPosition(0);
    // rightDriveEncoder.setPosition(0);



    //Set Drive to Brake
    fLeft.setIdleMode(IdleMode.kBrake);
    fRight.setIdleMode(IdleMode.kBrake);
    bLeft.setIdleMode(IdleMode.kBrake);
    bRight.setIdleMode(IdleMode.kBrake);

    //Set autoChoice
    autoChoice = 2;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  //   if(autoChoice == 0){
  //     diffDrive.tankDrive(0,0);
  //   }
  //   else if(autoChoice == 1){
  //     if (leftDriveEncoder.getPosition() > -50) {
  //       diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -50 &&leftDriveEncoder.getPosition() > -155) {
  //       diffDrive.tankDrive(-0.1,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -155 &&leftDriveEncoder.getPosition() > -205) {
  //       diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -205 &&leftDriveEncoder.getPosition() > -310) {
  //       diffDrive.tankDrive(-0.1,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -310) {
  //       leftDriveEncoder.setPosition(0);

  //       rightDriveEncoder.setPosition(0);
  //     }
  //  if(autoChoice == 1){
  // //Center Position  
  //   if (leftDriveEncoder.getPosition() > -75) {
  //        diffDrive.tankDrive(-0.5,-0.5);
  //   }
  //   else if (leftDriveEncoder.getPosition() < -75){
  //    diffDrive.tankDrive(0,0);
  //   }
  // }
  // //Left Position(non working)
  //   else if(autoChoice == 2){
  //     if (leftDriveEncoder.getPosition() > -50){
  //         diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -50 && leftDriveEncoder.getPosition() > -65){
  //         diffDrive.tankDrive(0.5,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -65 && leftDriveEncoder.getPosition() > -135){
  //         diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (leftDriveEncoder.getPosition() < -135){
  //         diffDrive.tankDrive(0,0);
  //     }
  //   }
  //   //Right Position
  //   else if(autoChoice == 3){
  //     if (rightDriveEncoder.getPosition() > -80){
  //         diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (rightDriveEncoder.getPosition() < -80 && rightDriveEncoder.getPosition() > -95){
  //         diffDrive.tankDrive(-0.5,0.5);
  //     }
  //     else if (rightDriveEncoder.getPosition() < -95 && rightDriveEncoder.getPosition() > -145){
  //         diffDrive.tankDrive(-0.5,-0.5);
  //     }
  //     else if (rightDriveEncoder.getPosition() < -145){
  //         diffDrive.tankDrive(0,0);
  //     }
  //   }
  // }
  //   else if(autoChoice == 2){

  //   }
   }

    //********** FRONT IS INTAKE FOR ALL AUTONOMOUS **********//

     
    
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //Start motors at power zero
    //IntakeL.set(0);
    //IntakeR.set(0);
    //TopThrowerR.set(0);
    //TopThrowerL.set(0);

    //Set initial speed
    driveSpeed = .5;

  }

  /** This function is called periodically during operator control. */
   @Override
  public void teleopPeriodic() {
    //Change drive speed
    if (rJoystick.getRawButtonPressed(1)){
      if (driveSpeed > 0){
        driveSpeed = (driveSpeed - .2);     
      }
    }
    else if (lJoystick.getRawButtonPressed(1)){
      if (driveSpeed < 1)
      driveSpeed = (driveSpeed + .2);
    }
    // else if (rJoystick.getRawButtonPressed(3)){
    //   driveSpeed = (1);
    // }

    //Changing drive inversion
    if (rJoystick.getRawButtonPressed(2)){
       driveinverted = true;
    }
    if (lJoystick.getRawButtonPressed(2)){
       driveinverted = false;
    }

    //Limiting drive speed
    if (driveSpeed > 1){
      driveSpeed = (1);
    }
    else if (driveSpeed < 0){
      driveSpeed = (0);
    }
    //Flywheel
    if (rJoystick.getRawButton(3)){
      flyWheel.set(-1);
    } 
    else{
      flyWheel.set(0);
    }  
    //Tank Drive
    //diffDrive.tankDrive(rJoystick.getY() * -0.5, lJoystick.getY() * -0.5);
     if(driveinverted == false){
        diffDrive.tankDrive(rJoystick.getY() * driveSpeed * -1, lJoystick.getY() * driveSpeed * -1);
     }
     else if(driveinverted == true){
    diffDrive.tankDrive(rJoystick.getY() * driveSpeed,lJoystick.getY() * driveSpeed);
    }
    //Regulate Speed
    //driveSpeed = (-rJoystick.getZ() + 1) / 2;
    
  
    
      /*else if (launch == true &&  intake == true) {
        IntakeL.set(-intakeSpeed);
        IntakeR.set(-intakeSpeed);
      }
        else {
          IntakeL.set(0);
          IntakeR.set(0);
          intake = false;
        }*/
      

      // if (lJoystick.getRawButtonPressed(4) && bBeam.get() == false && leftThrowerEncoder.getPosition() < 100){
      //   TopThrowerR.set(-.38);
      //   TopThrowerL.set(.38);
      // } else if (leftThrowerEncoder.getPosition() >= 100 && leftThrowerEncoder.getPosition() < 150){
      //  // TopThrowerR.set(-.38);
      //  // TopThrowerL.set(38);
      //   IntakeL.set(1);
      //   IntakeR.set(1);
      // } else if (leftThrowerEncoder.getPosition() >= 150) {
      //   TopThrowerR.set(0);
      //   TopThrowerL.set(0);
      //   IntakeL.set(0);
      //   IntakeR.set(0);
      //   leftThrowerEncoder.setPosition(0);
      // }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    fLeft.setIdleMode(IdleMode.kCoast);
    fRight.setIdleMode(IdleMode.kCoast);
    bLeft.setIdleMode(IdleMode.kCoast);
    bRight.setIdleMode(IdleMode.kCoast);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

//button to rev engines and shoot, delay .1-.5 seconds, then push ring into thrower to launch
//Best option: right trigger
//Left trigger for intake
//button to retract intake
//LEDS: Possession of ring (yellow) 
//no ring (no color) 
//have ring + ready to shoot (orange) 
//[maybe] slow speed (red) 
//normal speed (green)
//make sure the battery is at least at 12 Volts or else it gets jittery