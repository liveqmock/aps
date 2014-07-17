/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.child.GuideBean;
import com.mc.printer.server.entity.child.GuideCompBean;
import com.mc.printer.server.service.control.ControlServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import com.mc.printer.server.utils.DateHelper;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 305027939
 */
@Controller
public class AttathController {

    private static final Log log = LogFactory.getLog(AttathController.class);

    public static final String TEMP_DIR = "temp";

    @Resource
    ControlServiceIF controlService;

    @Resource
    private LogServiceIF logService;

    public GuideBean read(InputStream inputStream, Class cls) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(new Class[]{cls});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        GuideBean newxmllBean = (GuideBean) unmarshaller.unmarshal(inputStream);
        return newxmllBean;
    }

    private String upload(MultipartHttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        String filePath = "";
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_PATH;

        List<MultipartFile> files = request.getFiles("file");
        log.info("----------upload to:" + uploadPath + ",files size:"
                + files.size());
        for (int i = 0; i < files.size(); i++) {
            MultipartFile mfiles = files.get(i);
            String oringianlName = mfiles.getOriginalFilename();
            if (oringianlName.trim().equals("")) {
                continue;
            }

            if (oringianlName.endsWith(Constants.GUIDE_SUFFIX) || oringianlName.endsWith(Constants.MODEL_SUFFIX)) {

                filePath = uploadPath + File.separator + mfiles.getOriginalFilename();
                log.info("----------try to upload file:"
                        + mfiles.getOriginalFilename() + " to " + filePath);
                try {
                    File ufiles = new File(path + filePath);
                    if (!ufiles.exists()) {
                        ufiles.mkdirs();
                    }
                    mfiles.transferTo(ufiles);
                    log.info("----------upload successful.:"
                            + ufiles.getAbsolutePath());

                    /*button status更新到数据库中*/
                    if (oringianlName.endsWith(Constants.GUIDE_SUFFIX)) {
                        if (ufiles.exists()) {
                            ZipFile zipFile = new ZipFile(ufiles);

                            ZipEntry xmlEntry = null;
                            Enumeration<? extends ZipEntry> entrys = zipFile.entries();
                            while (entrys.hasMoreElements()) {
                                ZipEntry entry = entrys.nextElement();
                                String name = entry.getName();
                                if (!name.startsWith(TEMP_DIR)) {
                                    continue;
                                }

                                if (name.endsWith(".xml")) {
                                    xmlEntry = entry;
                                    break;
                                }

                            }

                            log.debug("read guide xml:" + xmlEntry);

                            if (xmlEntry != null) {
                                try (InputStream zipInputStream = zipFile.getInputStream(xmlEntry)) {
                                    GuideBean guide = read(zipInputStream, GuideBean.class);
                                    List<GuideCompBean> guideArray = guide.getElements();
                                    String guideName = guide.getGuideName();
                                    if (guideArray != null) {
                                        int k = 0;
                                        for (GuideCompBean com : guideArray) {
                                            String type = com.getType();
                                            String buttonName = com.getText();
                                            boolean control = com.isRemoteControl();
                                            if (type.equals("Button") && control) {
                                                TbControl tbauth = new TbControl();
//                                                tbauth.setBranchname("");
                                                tbauth.setButtonname(buttonName);
                                                tbauth.setGuidename(guideName);
                                                controlService.saveControl(tbauth);
                                                k++;
                                            }
                                        }
                                        log.debug("finish insert to control table. total:" + k);
                                    } else {
                                        log.debug("guideArray is null");
                                    }
                                    zipInputStream.close();
                                }
                            }

                        }
                    }

                } catch (IllegalStateException e) {
                    log.error("IllegalStateException:" + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    log.error("exception:" + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                log.warn("no save for " + oringianlName);
            }

        }
        return filePath;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/push")
    public ModelAndView uploadUrl(MultipartHttpServletRequest request) {
        String filePath = upload(request);
        log.info("----------upload finished.:" + filePath);
        logService.saveLog("推送部署模块.");
        return checkfil(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/deploy/delete/")
    public ModelAndView delete(@RequestParam String name, HttpServletRequest request) {
        try {
            String path = System.getProperty("user.dir");
            if (path.trim().equals("")) {
                path = request.getRealPath("/");
            }
            String uploadPath = Constants.UPLOAD_PATH;
            File uploadDir = new File(path + uploadPath);
            //name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
            log.debug("try to delete:" + name);
            if (!name.trim().equals("") && uploadDir.exists() && uploadDir.isDirectory()) {
                File[] ls = uploadDir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(Constants.GUIDE_SUFFIX) || name.endsWith(Constants.MODEL_SUFFIX)) {
                            return true;
                        }
                        return false;
                    }

                });
                if (ls != null && ls.length > 0) {
                    for (File f : ls) {
                        if (f.getName().equals(name)) {
                            try {
                                f.delete();
                                log.debug("delete file sucessfully.");
                            } catch (Exception e) {
                                log.error(e);
                            }
                            break;
                        }
                    }
                }

            }

        } catch (Exception ex) {
            log.error(ex);
        }
        logService.saveLog("删除模块："+name);
        return checkfil(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/deploy")
    public ModelAndView checkfil(HttpServletRequest request) {
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_PATH;

        File uploadDir = new File(path + uploadPath);
        List<HashMap> files = new ArrayList();

        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] ls = uploadDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(Constants.GUIDE_SUFFIX) || name.endsWith(Constants.MODEL_SUFFIX)) {
                        return true;
                    }
                    return false;
                }

            });

            if (ls != null && ls.length > 0) {
                log.info("found model files:" + ls.length);
                for (File f : ls) {
                    HashMap map = new HashMap();
                    map.put("key", f.getName());
                    map.put("value", "<font size=2 color=green><b>" + f.getName() + "</b></font>  ---- 部署时间：" + DateHelper.formatTime(new Date(f.lastModified())));
                    files.add(map);
                }
            }
        }
        return new ModelAndView("tool/deploy", "files", files);
    }

    private String uploadV(MultipartHttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        String filePath = "";
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_VIDEO_PATH;

        List<MultipartFile> files = request.getFiles("file");
        log.info("----------upload to:" + uploadPath + ",files size:"
                + files.size());
        for (int i = 0; i < files.size(); i++) {
            MultipartFile mfiles = files.get(i);
            String oringianlName = mfiles.getOriginalFilename();
            if (oringianlName.trim().equals("")) {
                continue;
            }

            if (oringianlName.endsWith(Constants.VIDEO_SUFFIX)) {

                //filePath = uploadPath + File.separator + mfiles.getOriginalFilename(); //视频路径暂时写死，以后有需求再改？？
                filePath = uploadPath + File.separator + "adv.flv";
                log.info("----------try to upload file:"
                        + mfiles.getOriginalFilename() + " to " + filePath);
                try {
                    File ufiles = new File(path + filePath);
                    if (!ufiles.exists()) {
                        ufiles.mkdirs();
                    }
                    mfiles.transferTo(ufiles);
                    log.info("----------upload successful.:"
                            + ufiles.getAbsolutePath());
                } catch (IllegalStateException e) {
                    log.error("IllegalStateException:" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    log.error("IO exception:" + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                log.warn("no save for " + oringianlName);
            }

        }
        return filePath;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/video/push")
    public ModelAndView uploadUrlV(MultipartHttpServletRequest request) {
        String filePath = uploadV(request);
        log.info("----------upload finished.:" + filePath);
        logService.saveLog("推送部署视频");
        return checkfilv(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/video/deploy/delete/")
    public ModelAndView deleteV(@RequestParam String name, HttpServletRequest request) {
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_VIDEO_PATH;
        File uploadDir = new File(path + uploadPath);
        log.debug("try to delete:" + name);
        if (!name.trim().equals("") && uploadDir.exists() && uploadDir.isDirectory()) {
            File[] ls = uploadDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(Constants.VIDEO_SUFFIX)) {
                        return true;
                    }
                    return false;
                }

            });
            if (ls != null && ls.length > 0) {
                for (File f : ls) {
                    if (f.getName().equals(name)) {
                        try {
                            f.delete();
                            log.debug("delete file sucessfully.");
                        } catch (Exception e) {
                            log.error(e);
                        }
                        break;
                    }
                }
            }

        }

        logService.saveLog("删除视频:"+name);
        return checkfilv(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/video/deploy")
    public ModelAndView checkfilv(HttpServletRequest request) {
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_VIDEO_PATH;

        File uploadDir = new File(path + uploadPath);
        List<HashMap> files = new ArrayList();

        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] ls = uploadDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(Constants.VIDEO_SUFFIX)) {
                        return true;
                    }
                    return false;
                }

            });

            if (ls != null && ls.length > 0) {
                log.info("found model files:" + ls.length);
                for (File f : ls) {
                    HashMap map = new HashMap();
                    map.put("key", f.getName());
                    map.put("value", "<font size=2 color=green><b>" + f.getName() + "</b></font>  ---- 视频发布时间：" + DateHelper.formatTime(new Date(f.lastModified())));
                    files.add(map);
                }
            }
        }
        return new ModelAndView("tool/video", "files", files);
    }
}
