/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.utils.DateHelper;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(method = RequestMethod.POST, value = "/admin/push")
    public ModelAndView uploadUrl(MultipartHttpServletRequest request) {
        String filePath = upload(request);
        log.info("----------upload finished.:" + filePath);
        return checkfil(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/deploy/delete/")
    public ModelAndView delete(@RequestParam String name, HttpServletRequest request) {
        String path = System.getProperty("user.dir");
        if (path.trim().equals("")) {
            path = request.getRealPath("/");
        }
        String uploadPath = Constants.UPLOAD_PATH;
        File uploadDir = new File(path + uploadPath);
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
