package de.bitfolge.guilayout.prototype.export;

import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.SwingUtilities;

import de.bitfolge.guilayout.elements.functionality.*;
import de.bitfolge.guilayout.elements.functionality.Image;
import de.bitfolge.guilayout.elements.screenarea.*;

public class ExportHTML implements DiagramExport {
	
	protected Screen root = null;
	
	protected float normedWidth(int pixel) {
		return 100f*pixel/root.getWidth();
	}
	
	protected float normedHeight(int pixel) {
		return 100f*pixel/root.getHeight();
	}
	
	protected String getLoremIpsum() {
		String[] lorem = {
			"Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. ",
			"Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. ",
			"Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. ",
			"Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem. ",
			"Investigationes demonstraverunt lectores legere me lius quod ii legunt saepius. ",
			"Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. ",
			"Mirum est notare quam littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas humanitatis per seacula quarta decima et quinta decima. ",
			"Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum. "
		};
		String result = "";
		for (int i=0; i<20; i++) {
			result = result + lorem[(int)Math.floor(Math.random()*lorem.length)]; 
		}
		return result;
	}
	
	protected String getHeadingDummy() {
		return "Si meliora dies, ut vina, poemata reddit, scire velim, chartis pretium quotus arroget annus.";
	}
	
	public void exportTo(File file, Screen rootSA) throws Exception {
		PrintStream out;
		out = new PrintStream(new FileOutputStream(file));
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("	<title>XHTML Export of GUI Layout Diagram</title>");
		out.println("	<style type=\"text/css\">");
		out.println("		div.guild { position:absolute; overflow:hidden; }");
		out.println("		div.border { border:1px solid black; }");
		out.println("		div.heading {");
		out.println("			white-space:nowrap;");
		out.println("		}");
		out.println("		div.navigation, div.link, div.workspace, div.form {"); 
		out.println("			background-position:center;");
		out.println("			background-repeat:no-repeat;\"");
		out.println("		}");
		out.println("		div.link {");
		out.println("			background-image:url(resources/Link.gif);");
		out.println("		}");
		out.println("		div.navigation {");
		out.println("			background-image:url(resources/Navigation.gif);");
		out.println("		}");
		out.println("		div.workspace {");
		out.println("			background-image:url(resources/Workspace.gif);");
		out.println("		}");
		
		if (rootSA==null) {
			throw new IllegalStateException("Please select a Screen before exporting.");
		}
		this.root = rootSA;
		Component[] comps = root.getComponents();
		Vector components = new Vector(Arrays.asList(comps));
		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setMaximumFractionDigits(1);
		for (int i=0; i<components.size(); i++) {
			ScreenArea sa = (ScreenArea) components.get(i);
			out.println("		#"+sa.hashCode()+" {");
			String s = new String();
			Rectangle bounds = SwingUtilities.convertRectangle(sa.getParent(), sa.getBounds(), root);
			out.println("			left:"+nf.format(normedWidth(bounds.x))+"%;");
			out.println("			top:"+nf.format(normedHeight(bounds.y))+"%;");
			out.println("			width:"+nf.format(normedWidth(bounds.width))+"%;");
			out.println("			height:"+nf.format(normedHeight(bounds.height))+"%;");
			out.println("		}");
			
			components.addAll(Arrays.asList(sa.getComponents()));
		}
		
		out.println("	</style>");
		out.println("</head>");
		out.println("<body>");
		
		
		for (int i=0; i<components.size(); i++) {
			ScreenArea sa = (ScreenArea) components.get(i);
			String content = "&nbsp;";
			String classes = "guild";
			if (!sa.isAbstract()) {
				classes = classes + " border";
			}
			if (sa.isFunctional()) {
				FunctionalScreenArea fsa = (FunctionalScreenArea) sa;
				if (fsa.hasFunctionality(Text.class)) {
					content = getLoremIpsum();
				}
				if (fsa.hasFunctionality(Image.class)) {
					content = "<img src=\"resources/Image.gif\" width=\"100%\" height=\"100%\" alt=\"\"/>";
				}
				if (fsa.hasFunctionality(Heading.class)) {
					content = "<span style=\"font-size:"+sa.getHeight()+"pt\">"+getHeadingDummy()+"</span>";
				}
				if (fsa.hasFunctionality(Logo.class)) {
					content = "<img src=\"resources/Logo.gif\" width=\"100%\" height=\"100%\" alt=\"\"/>";
				}
				if (fsa.hasFunctionality(Link.class)) {
					classes = classes + " link";
				}
				if (fsa.hasFunctionality(Navigation.class)) {
					classes = classes + " navigation";
				}
				if (fsa.hasFunctionality(Workspace.class)) {
					classes = classes + " workspace";
				}
			}
			out.println("	<div id=\""+sa.hashCode()+"\" class=\""+classes+"\">");
			out.println("		"+content);
			out.println("	</div>");
		}
		
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public String getName() {
		return "XHTML/CSS";
	}

	public String getExtension() {
		return ".html";
	}

	public String getFileDescription() {
		return "HTML files";
	}
}
