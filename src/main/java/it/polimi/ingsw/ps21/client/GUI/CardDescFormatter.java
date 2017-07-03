package it.polimi.ingsw.ps21.client.GUI;

public abstract class CardDescFormatter {
	
	public static String format(String string) {
		String[] lines = string.split("\n");
		StringBuilder description = new StringBuilder();
		for (String l : lines) {
			String[] splittedLine = l.split("\t");
			for (int k = 0; k < splittedLine.length; k++) {
				if (k != 0)
					description.append("  ");
				String[] splittedElement = splittedLine[k].split(":");
				for (String e : splittedElement) {
					if ((e.contains("-") || e.equals("-Description")) && !e.equals("-Requirements"))
						description.append("<b>" + e + ":</b> ");
					else
						description.append(e);
				}

			}
			description.append("<br>");
	}
		return description.toString();
	}
}
