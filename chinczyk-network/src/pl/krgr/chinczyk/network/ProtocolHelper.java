package pl.krgr.chinczyk.network;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolHelper {

	public static String[] matches(String responseTemplate, String response) {
		String normalizedPattern = responseTemplate.replaceAll("%s", "(.*)").replaceAll("%d", "(\\\\d+)").replaceAll("%b", "(true|false)");
		Pattern p = Pattern.compile(normalizedPattern);
		Matcher m = p.matcher(response);
		List<String> matches = new LinkedList<String>();
		if (m.matches()) {
			for (int i = 1; i <= m.groupCount(); i++) {
				matches.add(m.group(i));
			}
			if (matches.size() == 0) {
				matches.add(m.group());
			}
		}
		return matches.toArray(new String[matches.size()]);
	}	
}
