package com.dev.trident.comicbookreader.other.archive;

import com.dev.trident.comicbookreader.other.Utils;

/**
 * trident 16/04/2017.
 * Implementing Factory pattern to construct the ComicsReader based on the extension of the file
 * @see ComicsReader
 */

public class ArchiveReaderFactory {
    /**
     * Get the necessary reader based on the file extension
     * @param filePath - path to the file
     * @return @see ComicsReader
     * @throws Exception if the file extension does not match any supported
     */
    public static ComicsReader getReader(String filePath) throws Exception{
        switch (Utils.getFileExtension(filePath)){
            case "cbr":
                return new CBRReader(filePath);
            case "cbz":
                return new CBZReader(filePath);
            default:
                throw new Exception("file extension does not match any supported");
        }
    }
}
