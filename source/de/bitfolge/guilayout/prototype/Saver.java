package de.bitfolge.guilayout.prototype;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

import javax.swing.*;

import de.bitfolge.guilayout.elements.DiagramManager;
import de.bitfolge.guilayout.elements.functionality.*;
import de.bitfolge.guilayout.elements.functionality.Image;
import de.bitfolge.guilayout.elements.screenarea.*;
import de.bitfolge.guilayout.prototype.export.DiagramExport;

public class Saver {
	
	protected static File ensureExtension(File file, String extension) {
		File f = file;
		if (!f.getName().endsWith(extension)) {
			f = new File(f.getAbsolutePath()+extension);
		}
		return f;
	}
	
	public static void saveTo(File saveFileName) {
		PrintStream out;
		try {
			out = new PrintStream(new GZIPOutputStream(new FileOutputStream(saveFileName)));
			saveFileName = ensureExtension(saveFileName, ".guild");
			
			DrawingPanel dp = GUILayout._program.drawingPanel;
			String dpString = new String();
			dpString = dpString + dp.hashCode();
			out.println(dpString);
			
			Component[] comps = GUILayout._program.drawingPanel.getComponents();
			Vector components = new Vector(Arrays.asList(comps));
			for (int i=0; i<components.size(); i++) {
				if (!ScreenArea.class.isInstance(components.get(i))) {
					continue;
				}
				ScreenArea sa = (ScreenArea) components.get(i);
				
				String saString = new String();
				
				saString = saString + sa.hashCode()+";";
				saString = saString + sa.getClass().getName()+";";
				saString = saString + sa.getParent().hashCode()+";";
				saString = saString + sa.getName().replaceAll(";",";;")+";";
				saString = saString + sa.getBounds().x+";";
				saString = saString + sa.getBounds().y+";";
				saString = saString + sa.getBounds().width+";";
				saString = saString + sa.getBounds().height+";";
				saString = saString + sa.isAbstract()+";";
				if (FunctionalScreenArea.class.isInstance(sa)) {
					FunctionalScreenArea fsa = (FunctionalScreenArea) sa;
					saString = saString + fsa.hasFunctionality(Image.class)+";";
					saString = saString + fsa.hasFunctionality(Text.class)+";";
					saString = saString + fsa.hasFunctionality(Heading.class)+";";
					saString = saString + fsa.hasFunctionality(Logo.class)+";";
					saString = saString + fsa.hasFunctionality(Link.class)+";";
					saString = saString + fsa.hasFunctionality(Form.class)+";";
					saString = saString + fsa.hasFunctionality(Navigation.class)+";";
					saString = saString + fsa.hasFunctionality(Workspace.class)+";";
				}
				
				components.addAll(Arrays.asList(sa.getComponents()));
				out.println(saString);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					GUILayout._program,
					e.getMessage(),
					"Error during Saving",
					JOptionPane.INFORMATION_MESSAGE
				);
		}
	}

	public static void loadFrom(File file) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
		HashMap compMap = new HashMap();

		String line = in.readLine().trim();
		compMap.put(line, GUILayout._program.drawingPanel);
		
		while (in.ready()) {
			line = in.readLine().trim();
			String[] tokens = line.split(";");
			int i=0;
			String hash = tokens[i++];
			String className = tokens[i++];
			String parentHash = tokens[i++];
			String name = tokens[i++];
			int x = Integer.parseInt(tokens[i++]);
			int y = Integer.parseInt(tokens[i++]);
			int width = Integer.parseInt(tokens[i++]);
			int height = Integer.parseInt(tokens[i++]);
			boolean isAbstract = Boolean.valueOf(tokens[i++]).booleanValue();
			
			boolean isImage = false;
			boolean isText = false;
			boolean isHeading = false;
			boolean isLogo = false;
			boolean isLink = false;
			boolean isForm = false;
			boolean isNavigation = false;
			boolean isWorkspace = false;

			if (className.endsWith(".FunctionalScreenArea")) {
				isImage = Boolean.valueOf(tokens[i++]).booleanValue();
				isText = Boolean.valueOf(tokens[i++]).booleanValue();
				isHeading = Boolean.valueOf(tokens[i++]).booleanValue();
				isLogo = Boolean.valueOf(tokens[i++]).booleanValue();
				isLink = Boolean.valueOf(tokens[i++]).booleanValue();
				isForm = Boolean.valueOf(tokens[i++]).booleanValue();
				isNavigation = Boolean.valueOf(tokens[i++]).booleanValue();
				isWorkspace = Boolean.valueOf(tokens[i++]).booleanValue();
			}
			ScreenArea sa = (ScreenArea) Class.forName(className).newInstance();
			sa.setBounds(x,y,width,height);
			sa.setName(name);
			sa.setAbstract(isAbstract);
			if (FunctionalScreenArea.class.isInstance(sa)) {
				FunctionalScreenArea fsa = (FunctionalScreenArea) sa; 
				fsa.setFunctionality(Image.class, isImage);
				fsa.setFunctionality(Text.class, isText);
				fsa.setFunctionality(Heading.class, isHeading);
				fsa.setFunctionality(Logo.class, isLogo);
				fsa.setFunctionality(Link.class, isLink);
				fsa.setFunctionality(Form.class, isForm);
				fsa.setFunctionality(Navigation.class, isNavigation);
				fsa.setFunctionality(Workspace.class, isWorkspace);
			}
			Container parent = (Container) compMap.get(parentHash);
			parent.add(sa);
			compMap.put(hash, sa);
		}
	}
	
	protected static void showExportError(String message) {
		JOptionPane.showMessageDialog(
				GUILayout._program,
				message,
				"Error during export",
				JOptionPane.INFORMATION_MESSAGE
			);
	}
	
	
	public static boolean prepareExport() {
		DrawingPanel drawingPanel = GUILayout._program.drawingPanel;
		ScreenArea rootSA = DiagramManager.getInstance().getSelectedScreenArea();
		if (rootSA==null) {
			int screens = 0;
			for(int i=0; i<drawingPanel.getComponentCount(); i++) {
				if (Screen.class.isInstance(drawingPanel.getComponent(i))) {
					screens++;
					rootSA = (Screen) drawingPanel.getComponent(i);
				}
			}
			if (screens!=1) {
				showExportError("Please select a Screen before exporting.");
				return false;
			}
		}
		if (!Screen.class.isInstance(rootSA)) {
			showExportError("Only Screens can be exported.\nPlease select a Screen before exporting.");
			return false;
		}
		DiagramManager.getInstance().setSelectedScreenArea(rootSA);
		return true;
	}
	
	public static void export(DiagramExport exporter) {
		if (prepareExport()) {
			JFileChooser fc = new JFileChooser(new java.io.File("."));
			ExtensionFileFilter eff = new ExtensionFileFilter();
			eff.addExtension(exporter.getExtension());
			eff.setDescription(exporter.getFileDescription());
			fc.setDialogTitle(exporter.getName()+" export");
			fc.addChoosableFileFilter(eff);
			fc.setApproveButtonText("Export");
			if (fc.showSaveDialog(GUILayout._program)==JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				f = ensureExtension(f, exporter.getExtension());
				try {
					Screen screen = (Screen) DiagramManager.getInstance().getSelectedScreenArea().clone();
					screen.updateZoom(1);
					exporter.exportTo(f, screen);
				} catch (Exception e) {
					e.printStackTrace();
					showExportError(e.getMessage());
				}
			}
		}
	}
}
