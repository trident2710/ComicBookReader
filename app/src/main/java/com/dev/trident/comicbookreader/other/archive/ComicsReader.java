package com.dev.trident.comicbookreader.other.archive;

import java.io.IOException;
import java.io.InputStream;

/**
 * trident 15/04/2017.
 * Abstract comic archive reader
 */

public abstract class ComicsReader {

    /**
     * name of the file to unarchive
     */
    protected String fileName;

    /**
     * the path to the file (archive) on the local disc
     */
    protected String filePath;

    /**
     *
     * @return the name of the file to unarchive
     */
    public String getFileName(){
        return fileName;
    }

    /**
     *
     * @return the path of the archive
     */
    public String getFilePath(){
        return filePath;
    }

    public abstract FileType getFileType();

    /**
     * Get the number of pages in the archive
     * @return pages count
     */
    public abstract int getPageCount();

    /**
     * Get the input stream to read the page from the archive
     * @return
     */
    public abstract InputStream getPageInputStream(int page) throws IOException;

    /**
     * Read the data in the archive
     * @throws Exception
     */
    public abstract void readHeader() throws Exception;


    /**
     * The enum to declare supported archive types
     */
    public enum FileType{
        CBR(".cbr"),
        CBZ(".cbz");

        String extension;

        public String getExtension(){
            return extension;
        }

        FileType(String extension){
            this.extension = extension;
        }
    };


    public ComicsReader(String filePath) throws Exception{
        this.filePath = filePath;
        this.fileName = filePath;

    }

    public abstract void destroy() throws IOException;
}
