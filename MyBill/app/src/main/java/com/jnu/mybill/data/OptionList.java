package com.jnu.mybill.data;

public class OptionList {
    private int optionPhoto;
    private String optionText;

    public OptionList(int optionPhoto, String optionText){
        this.optionPhoto = optionPhoto;
        this.optionText=optionText;
    }
    public int getOptionPhoto() {
        return optionPhoto;
    }

    public void setOptionPhoto(int optionPhoto) {
        this.optionPhoto = optionPhoto;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
