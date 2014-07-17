/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.entity.child;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 305027939
 */
@XmlRootElement(name = "guide")
@XmlAccessorType(XmlAccessType.FIELD)
public class GuideBean implements Cloneable {

    @XmlElement(name = "comp")
    private List<GuideCompBean> elements;

    @XmlAttribute
    private String backgoundImg;

    @XmlAttribute
    private String guideName;
    
    @XmlAttribute
    private int width;
    
    @XmlAttribute
    private int height;
    
    @XmlAttribute
    private int backgroundColorRGB;


    @Override
    public GuideBean clone() throws CloneNotSupportedException {
        GuideBean beans = (GuideBean) super.clone();
        List<GuideCompBean> beanArray = new ArrayList();
        for (GuideCompBean bean : beans.getElements()) {
            GuideCompBean newBean = bean.clone();
            beanArray.add(newBean);
        }
        beans.setElements(beanArray);
        return beans;
    }

    /**
     * @return the elements
     */
    public List<GuideCompBean> getElements() {
        return elements;
    }

    /**
     * @param elements the elements to set
     */
    public void setElements(List<GuideCompBean> elements) {
        this.elements = elements;
    }

    /**
     * @return the backgoundImg
     */
    public String getBackgoundImg() {
        return backgoundImg;
    }

    /**
     * @param backgoundImg the backgoundImg to set
     */
    public void setBackgoundImg(String backgoundImg) {
        this.backgoundImg = backgoundImg;
    }

    /**
     * @return the guideName
     */
    public String getGuideName() {
        return guideName;
    }

    /**
     * @param guideName the guideName to set
     */
    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the backgroundColorRGB
     */
    public int getBackgroundColorRGB() {
        return backgroundColorRGB;
    }

    /**
     * @param backgroundColorRGB the backgroundColorRGB to set
     */
    public void setBackgroundColorRGB(int backgroundColorRGB) {
        this.backgroundColorRGB = backgroundColorRGB;
    }


}
