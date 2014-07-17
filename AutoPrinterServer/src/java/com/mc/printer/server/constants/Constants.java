package com.mc.printer.server.constants;

/**
 *
 * @author 305027939
 */
public class Constants {

    public static String CONFIG_PATH = "com/mc/printer/server/resource/applicationConfig";

    public static String CONFIG_XMLPATH = "applicationContext.xml";

    public static String CONFIG_JNDI = "jndiName";

    public static String VERSION = "14.0701";

    public final static int FLAG_STATUS_DELETE = 1;

    public final static int FLAG_STATUS_ACTIVE = 2;

    public final static int FLAG_STATUS_SLEEP = 3;

    /*下面是和权限绑定在一起,请查看applicationContext-security.xml*/
    public final static String[] ROLE_PAGES=new String[]{"ROLE_ROLE","ROLE_DEP","ROLE_USER","ROLE_TOOL","ROLE_DEPLOY","ROLE_BRANCH","ROLE_VIDEO","ROLE_SEARCH","ROLE_CONTROL","SYSTEM_SEARCH"};
    public final static String[] ROLE_PAGES_CN=new String[]{"角色管理","部门管理","用户管理","制单工具","推送部署","网点监控","视频推送","综合查询","业务控制","系统查询"};
    
    
    /*toppage 返回状态*/
    public final static int TOPPAGE_TIMEOUT = 1;
    public final static int TOPPAGE_NEEDINIT = 2;
    public final static int TOPPAGE_INITDONE = 3;
    public final static int TOPPAGE_ISSUE = 4;

    /*upload path*/
    public final static String UPLOAD_PATH = "/warehouse/res/home";

    /*upload path*/
    public final static String UPLOAD_VIDEO_PATH = "/warehouse/res/home/video";

    public final static String MODEL_SUFFIX = "mde";
    public final static String GUIDE_SUFFIX = "gde";

    public final static String VIDEO_SUFFIX = "flv";
    
    public final static String KEY_SPLIT = "\\$";

    /*这个时间一般设置的为客户端的2倍*/
    public final static long CHECKIN_LOOP_SEC = 360;
    
    /*每张单据对于每一个客户最大保存数量，超过以后，删除最旧。*/
    public final static int MAX_NUMBER_EACH_USER = 20;

}
