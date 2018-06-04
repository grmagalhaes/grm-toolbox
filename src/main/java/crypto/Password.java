package sofia.toolbox.crypto;

import sofia.toolbox.math.Random;

/**
 * This class contains methods to generate passwords according to the current security rules.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Password {

	private short minValue;
	private short maxValue;
	private Random random;

	public Password() {
		
		random = new Random();

	}

	public char getNumericChar() {
		this.minValue = 0x30;
		this.maxValue = 0x39;
		return (char)(this.minValue + random.nextInt(this.maxValue - this.minValue));
	}

	public char getLowercaseChar() {
		this.minValue = 0x61;
		this.maxValue = 0x7A;
		return (char)(this.minValue + random.nextInt(this.maxValue - this.minValue));
	}


	public char getUppercaseChar() {
		this.minValue = 0x41;
		this.maxValue = 0x5A;
		return (char)(this.minValue + random.nextInt(this.maxValue - this.minValue));
	}

	public char getChar() {
		int t = random.nextInt(100);
		if ((t >= 0) && (t <= 33)) 
			return getNumericChar();
		if ((t > 33) && (t <= 66)) 
			return getLowercaseChar();
		if ((t > 66) && (t <= 100)) 
			return getUppercaseChar();

		return getLowercaseChar();
	}

	public final String generate() {

		StringBuffer rs = new StringBuffer();
		rs.append(getLowercaseChar());
		rs.append(getChar());
		rs.append(getChar());		
		rs.append(getChar());
		rs.append(getChar());
		rs.append(getChar());
		rs.append(getUppercaseChar());		
		rs.append(getNumericChar());
		return rs.toString();
	}
	
	public final boolean validate(String user, String oldPassword, String newPassword) {
		
		boolean test;
		int i;
		Character character;
		
		if (oldPassword == null) return false;
		if (newPassword == null) return false;
		if (user == null) return false;
		
		// Old and new passwords must be different
		if (oldPassword.toLowerCase().equals(newPassword.toLowerCase())) return false;

        // Password size must have 8 or more characters
		if (newPassword.length() < 8) return false;
		
		// Password must have at least one lowercase character
		test = false;
		for (i=0; i < newPassword.length(); i++) {
			character = newPassword.charAt(i);
            if (Character.isLowerCase(character)) {
              test = true;
              break;
            }
        }
		if (!test) return false;
		
		// Password must have at least one number
		test = false;
		for (i=0; i < newPassword.length(); i++) {
			character = newPassword.charAt(i);
            if (Character.isDigit(character)) {  
              test = true;
              break;
            }
        }
		if (!test) return false;		
		
		// Password cannot contain the user name
		if (newPassword.toUpperCase().contains(user.toUpperCase())) return false;
		
		// Password must have at least one uppercase character 
		test = false;
		for (i=0; i < newPassword.length(); i++) {
			character = newPassword.charAt(i);
            if (Character.isUpperCase(character)) {  
              test = true;
              break;
            }
        }
		if (!test) return false;
		
		return true;

    }    
	
	public final String toString() {
		return generate();
	}

}

