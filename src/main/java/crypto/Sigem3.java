package sofia.toolbox.crypto;

import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import sofia.toolbox.util.Text;

/**
 * This class contains methods to generate and validate passwords according to the author's cryptography (conversion table with time stamp).
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Sigem3 {

	public String decrypt(String password) {

		String result = "";
		int sec, length;

		sec = Integer.parseInt(password.substring(password.length() - 4, password.length() - 2));
		length = Integer.parseInt(password.substring(password.length() - 2, password.length()));
		if ((sec >= 0) && (sec <= 19)) 
			result = Text.translate(password , "ihz9aF6bu4@sONKdce7gp8wyr.UBP3ATWDEIRQSf125oH_kljVqZMtJmvXxYCn0LG", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@");
		
		if ((sec >= 20) && (sec <= 39))
			result = Text.translate(password , "wF90WBTNadm84Hcje@pG.hE1ZDXout567LCIbOJQfgViPklKn3SRx_M2vsryAYUqz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@");

		if ((sec >= 40) && (sec <= 59))
			result = Text.translate(password , "ljIG5Qo_waVDHOtgdKLY0@ziCJWU1E4SvxhsbfFABNPRZ23pqkn78XT96M.rmceuy", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@");

		result = result.substring(0, length);

		return (result);

	}

	public String encrypt(String password) {

		StringBuffer result = new StringBuffer();

		int sec;

		Format formatter = new SimpleDateFormat("ss");
		Date date = new Date();
		String secStr = formatter.format(date);

		sec = Integer.parseInt(secStr);
		
		if ((sec >= 0) && (sec <= 19))
			result.append(Text.translate(password, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@", "ihz9aF6bu4@sONKdce7gp8wyr.UBP3ATWDEIRQSf125oH_kljVqZMtJmvXxYCn0LG"));

		if ((sec >= 20) && (sec <= 39)) 
			result.append(Text.translate(password, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@", "wF90WBTNadm84Hcje@pG.hE1ZDXout567LCIbOJQfgViPklKn3SRx_M2vsryAYUqz"));

		if ((sec >= 40) && (sec <= 59))
			result.append(Text.translate(password, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz_.@", "ljIG5Qo_waVDHOtgdKLY0@ziCJWU1E4SvxhsbfFABNPRZ23pqkn78XT96M.rmceuy"));

		BigInteger bi = new BigInteger("433456787987789894584456213778655458965074589458");

		BigInteger multiply = new BigInteger(String.valueOf(sec + 1));

		String text3 = bi.multiply(multiply).toString().substring(0, 36 - password.length());
		result.append(Text.translate(text3, "01234567890", "45M32W7j65"));
		result.append(secStr);
		result.append(String.format("%02d", password.length()));

		return (result.toString());

	}


}
