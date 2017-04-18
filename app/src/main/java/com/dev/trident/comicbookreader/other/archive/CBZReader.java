package com.dev.trident.comicbookreader.other.archive;

import com.dev.trident.comicbookreader.other.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * trident 16/04/2017.
 */

public class CBZReader extends ComicsReader {

    private ZipFile mZipFile;
    private ArrayList<ZipEntry> mEntries;

    public CBZReader(String path) throws Exception{
        super(path);
        readHeader();
    }

    @Override
    public void destroy() throws IOException {
        mZipFile.close();
    }

    @Override
    public FileType getFileType() {
        return FileType.CBZ;
    }

    @Override
    public int getPageCount() {
        return mEntries.size();
    }

    @Override
    public InputStream getPageInputStream(int page) throws IOException{
        if(page>=mEntries.size()) throw new IOException("page does not exist");
        return mZipFile.getInputStream(mEntries.get(page));
    }

    @Override
    public void readHeader() throws Exception {
        mZipFile = new ZipFile(getFilePath());
        mEntries = new ArrayList<ZipEntry>();

        Enumeration<? extends ZipEntry> e = mZipFile.entries();
        while (e.hasMoreElements()) {
            ZipEntry ze = e.nextElement();
            if (!ze.isDirectory() && Utils.isImage(ze.getName())) {
                mEntries.add(ze);
            }
        }
    }


}
