/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.entity.child;

import java.awt.Color;
import java.awt.Font;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author 305027939
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GuideCompBean implements Cloneable {

    @XmlAttribute
    private String elementId;

    @XmlAttribute
    private String text;

    @XmlAttribute
    private String position;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String icon;

    @XmlAttribute
    private String pressedIcon;

    @XmlAttribute
    private int colorRGB;

    @XmlAttribute
    private String linkType;

    @XmlAttribute
    private String linkURI;

    @XmlAttribute
    private String font;

    @XmlAttribute
    private int fontsize;

    @XmlAttribute
    private int fontstyle;

    @XmlAttribute
    private int foregroundRGB;
    
    @XmlAttribute
    private String authentication;
    
    @XmlAttribute
    private int align;

    @XmlAttribute
    private boolean remoteControl;

    @Override
    public GuideCompBean clone() throws CloneNotSupportedException {
        GuideCompBean bean = (GuideCompBean) super.clone();
        return bean;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the pressedIcon
     */
    public String getPressedIcon() {
        return pressedIcon;
    }

    /**
     * @param pressedIcon the pressedIcon to set
     */
    public void setPressedIcon(String pressedIcon) {
        this.pressedIcon = pressedIcon;
    }



    /**
     * @return the elementId
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * @param elementId the elementId to set
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * @return the linkType
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * @param linkType the linkType to set
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    /**
     * @return the linkURI
     */
    public String getLinkURI() {
        return linkURI;
    }

    /**
     * @param linkURI the linkURI to set
     */
    public void setLinkURI(String linkURI) {
        this.linkURI = linkURI;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the colorRGB
     */
    public int getColorRGB() {
        return colorRGB;
    }

    /**
     * @param colorRGB the colorRGB to set
     */
    public void setColorRGB(int colorRGB) {
        this.colorRGB = colorRGB;
    }

    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * @return the fontsize
     */
    public int getFontsize() {
        return fontsize;
    }

    /**
     * @param fontsize the fontsize to set
     */
    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    /**
     * @return the fontstyle
     */
    public int getFontstyle() {
        return fontstyle;
    }

    /**
     * @param fontstyle the fontstyle to set
     */
    public void setFontstyle(int fontstyle) {
        this.fontstyle = fontstyle;
    }

    /**
     * @return the foregroundRGB
     */
    public int getForegroundRGB() {
        return foregroundRGB;
    }

    /**
     * @param foregroundRGB the foregroundRGB to set
     */
    public void setForegroundRGB(int foregroundRGB) {
        this.foregroundRGB = foregroundRGB;
    }

    /**
     * @return the authentication
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     * @param authentication the authentication to set
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    /**
     * @return the align
     */
    public int getAlign() {
        return align;
    }

    /**
     * @param align the align to set
     */
    public void setAlign(int align) {
        this.align = align;
    }

    /**
     * @return the remoteControl
     */
    public boolean isRemoteControl() {
        return remoteControl;
    }

    /**
     * @param remoteControl the remoteControl to set
     */
    public void setRemoteControl(boolean remoteControl) {
        this.remoteControl = remoteControl;
    }


}
