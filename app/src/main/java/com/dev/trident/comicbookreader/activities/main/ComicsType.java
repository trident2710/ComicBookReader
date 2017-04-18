package com.dev.trident.comicbookreader.activities.main;

/**
 * trident 15/04/2017.
 * Enum for defining the comics types supported by the app
 * Contains their ids and file type tags
*/

public enum ComicsType {
    CBR(".cbr"),
    CBZ(".cbz");

    String type;

    ComicsType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }


    /**
     * Get all supported comic types
     * @return the array of all supported comic types
     */
    public static String[] getAllTypes(){
        String[] types = new String[ComicsType.values().length];
        int i = 0;
        for(ComicsType type:ComicsType.values()){
            types[i] = type.getType();
            i++;
        }
        return types;
    }
}
