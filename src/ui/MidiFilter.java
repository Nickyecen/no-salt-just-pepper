package ui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MidiFilter extends FileFilter {

	@Override
	public boolean accept(File f) {

		if (f.isDirectory()) {
	        return true;
	    }

		String extension = TextFilter.getFileExtension(f);
        if (extension != null) {
            if (extension.equals("mid")) {
				return true;
			} else {
				return false;
			}
        }

        return false;
	}

	@Override
	public String getDescription() {
		return ".mid";
	}

}
