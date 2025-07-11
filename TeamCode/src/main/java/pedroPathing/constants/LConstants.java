package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0019;
        ThreeWheelConstants.strafeTicksToInches = .0019;
        ThreeWheelConstants.turnTicksToInches = .0017;
        ThreeWheelConstants.leftY = 5;
        ThreeWheelConstants.rightY = -5;
        ThreeWheelConstants.strafeX = -5.5;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "fl-drive";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "br-drive";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "fr-drive";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;
    }
}




