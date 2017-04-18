package com.dev.trident.comicbookreader.other.archive;

import com.dev.trident.comicbookreader.other.Utils;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * trident 16/04/2017.
 */

public class CBRReader extends ComicsReader {

    private ArrayList<FileHeader> mHeaders;
    private Archive mArchive;
    private File mCacheDir;
    private boolean mSolidFileExtracted = false;

    public CBRReader(String path) throws Exception{
        super(path);
        mHeaders = new ArrayList<FileHeader>();
        readHeader();

    }

    @Override
    public FileType getFileType() {
        return FileType.CBR;
    }

    @Override
    public int getPageCount() {
        return mHeaders.size();
    }

    @Override
    public InputStream getPageInputStream(int page) throws IOException {
        if (mArchive.getMainHeader().isSolid()) {
            // solid archives require special treatment
            synchronized (this) {
                if (!mSolidFileExtracted) {
                    for (FileHeader h : mArchive.getFileHeaders()) {
                        if (!h.isDirectory() && Utils.isImage(getName(h))) {
                            getPageStream(h);
                        }
                    }
                    mSolidFileExtracted = true;
                }
            }
        }
        return getPageStream(mHeaders.get(page));
    }

    @Override
    public void readHeader() throws Exception {
        try {
            mArchive = new Archive(new File(filePath));
        }
        catch (RarException e) {
            throw new IOException("unable to open archive");
        }

        FileHeader header = mArchive.nextFileHeader();
        while (header != null) {
            if (!header.isDirectory()) {
                String name = getName(header);
                if (Utils.isImage(name)) {
                    mHeaders.add(header);
                }
            }

            header = mArchive.nextFileHeader();
        }
    }

    private String getName(FileHeader header) {
        return header.isUnicode() ? header.getFileNameW() : header.getFileNameString();
    }

    private InputStream getPageStream(FileHeader header) throws IOException {
        try {
            if (mCacheDir != null) {
                String name = getName(header);
                File cacheFile = new File(mCacheDir, Utils.MD5(name));

                if (cacheFile.exists()) {
                    return new FileInputStream(cacheFile);
                }

                synchronized (this) {
                    if (!cacheFile.exists()) {
                        FileOutputStream os = new FileOutputStream(cacheFile);
                        try {
                            mArchive.extractFile(header, os);
                        }
                        catch (Exception e) {
                            os.close();
                            cacheFile.delete();
                            throw e;
                        }
                        os.close();
                    }
                }
                return new FileInputStream(cacheFile);
            }
            return mArchive.getInputStream(header);
        }
        catch (RarException e) {
            throw new IOException("unable to parse rar");
        }
    }

    public void setCacheDirectory(File cacheDirectory) {
        mCacheDir = cacheDirectory;
        if (!mCacheDir.exists()) {
            mCacheDir.mkdir();
        }
        if (mCacheDir.listFiles() != null) {
            for (File f : mCacheDir.listFiles()) {
                f.delete();
            }
        }
    }

    public void destroy() throws IOException {
        if (mCacheDir != null) {
            for (File f : mCacheDir.listFiles()) {
                f.delete();
            }
            mCacheDir.delete();
        }
        mArchive.close();
    }
}
