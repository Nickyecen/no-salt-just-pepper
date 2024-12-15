package ui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

class TextFilter extends FileFilter {

	@Override
	public boolean accept(File f) {

		if (f.isDirectory()) {
	        return true;
	    }

		String extension = getFileExtension(f);
        if (extension != null) {
            if (extension.equals("txt")) {
				return true;
			} else {
				return false;
			}
        }

        return false;
	}

	@Override
	public String getDescription() {
		return ".txt";
	}
	
	static String getFileExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
    }

}
